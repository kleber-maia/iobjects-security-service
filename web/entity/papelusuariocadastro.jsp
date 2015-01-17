
<%@include file="../include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.entity.*"%>

<%
  try {
    // nossa inst�ncia de Papel Usu�rio
    PapelUsuario papelUsuario = (PapelUsuario)facade.getEntity(PapelUsuario.CLASS_NAME);

    // verifica tipo de objeto mestre na rela��o
    boolean masterIsPapel = papelUsuario.USER_PARAM_MASTER.getValue().equals(PapelUsuario.MASTER_IS_PAPEL);
    boolean masterIsUsuario = papelUsuario.USER_PARAM_MASTER.getValue().equals(PapelUsuario.MASTER_IS_USUARIO);
    // se n�o sabemos o mestre da rela��o...exce��o
    if (!masterIsPapel && !masterIsUsuario)
      throw new Exception("Mestre da rela��o indefinido.");

    // PapelUsuarioInfo para editar
    PapelUsuarioInfo papelUsuarioInfo = null;
    // se estamos incluindo...
    if (Controller.isInserting(request)) {
      // info em branco
      papelUsuarioInfo = new PapelUsuarioInfo();
      // preenche o PapelId
      if (masterIsPapel)
        papelUsuarioInfo.setPapelId(NumberTools.parseInt(papelUsuario.USER_PARAM_PAPEL_ID.getValue()));
      // preenche o UsuarioId
      else if (masterIsUsuario)
        papelUsuarioInfo.setUsuarioId(NumberTools.parseInt(papelUsuario.USER_PARAM_USUARIO_ID.getValue()));
    }
    // se estamos salvando...
    else if (Controller.isSaving(request)) {
      // info preenchido na p�gina
      papelUsuarioInfo = (PapelUsuarioInfo)papelUsuario.entityInfoFromRequest(request);
      // se estamos salvando e � um novo...inclui
      if (Controller.isSavingNew(request)) {
        papelUsuario.insert(papelUsuarioInfo);
      }
      // se estamos salvando e � um existente...atualiza
      else {
        papelUsuario.update(papelUsuarioInfo);
      } // if
      // comp�e a resposta
      Form.composeAjaxResponse(response, Form.COMPOSE_TYPE_JAVA_SCRIPT);
      // atualiza a consulta
      %><%=EntityGrid.forwardBrowse(facade, papelUsuario, PapelUsuario.ACTION, Controller.isSavingNew(request) ? EntityGrid.OPERATION_INSERT : EntityGrid.OPERATION_UPDATE, papelUsuarioInfo)%><%
      // dispara
      return;
    }
    // se estamos editando...
    else if (Controller.isEditing(request)) {
      // info do registro para editar
      papelUsuarioInfo =
        papelUsuario.selectByPapelIdUsuarioId(
          NumberTools.parseInt(request.getParameter(PapelUsuario.FIELD_PAPEL_ID.getFieldAlias())),
          NumberTools.parseInt(request.getParameter(PapelUsuario.FIELD_USUARIO_ID.getFieldAlias()))
        );
    }
    // se o comando � desconhecido...
    else {
      throw new Exception("Comando desconhecido.");
    } // if

    // nosso Form de dados
    Form formData = new Form(request, "formDataPapelUsuario", PapelUsuario.ACTION_CADASTRO, PapelUsuario.COMMAND_SAVE, "", true, true);
%>

<html>
  <head>
    <title><%=PapelUsuario.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body style="margin:0px;" onselectstart="return true;" oncontextmenu="return false;">

    <!-- Form de dados -->
    <%=formData.begin()%>
      <!-- dados ocultos -->
      <%=EntityFormEdit.script(facade, masterIsPapel ? PapelUsuario.FIELD_PAPEL_ID : PapelUsuario.FIELD_USUARIO_ID, papelUsuarioInfo, request, -1, "", "")%>
      <%=EntityFormEdit.script(facade, PapelUsuario.FIELD_HASH, papelUsuarioInfo, request, -1, "", "")%>
      <!-- dados de Papel Usu�rio -->
      <table>
        <!-- campo Usu�rio Id ou Papel Id -->
        <tr>
          <%
            // ajusta o filtro de Usu�rio e Papel para n�o exibir os ocultos
            EntityLookup lookupUsuario = papelUsuario.lookupList().get(PapelUsuario.LOOKUP_USUARIO.getName());
            EntityLookup lookupPapel   = papelUsuario.lookupList().get(PapelUsuario.LOOKUP_PAPEL.getName());
            // guarda os filtros atuais
            String filterUsuario = lookupUsuario.getLookupFilterExpression();
            String filterPapel   = lookupPapel.getLookupFilterExpression();
            // se o usu�rio que efetuou logon n�o � Super Usu�rio...
            if (!facade.getLoggedUser().getSuperUser()) {
              // adiciona a express�o de filtro ao lookup de Usu�rio
              if (!lookupUsuario.getLookupFilterExpression().equals(""))
                lookupUsuario.setLookupFilterExpression("(" + lookupUsuario.getLookupFilterExpression() + ") AND ");
              lookupUsuario.setLookupFilterExpression(lookupUsuario.getLookupFilterExpression() + "(" + Usuario.FIELD_NOME.getFieldName() + " NOT LIKE '@%')");
              // adiciona a express�o de filtro ao lookup de Papel
              if (!lookupPapel.getLookupFilterExpression().equals(""))
                lookupPapel.setLookupFilterExpression("(" + lookupPapel.getLookupFilterExpression() + ") AND ");
              lookupPapel.setLookupFilterExpression(lookupPapel.getLookupFilterExpression() + "(" + Papel.FIELD_NOME.getFieldName() + " NOT LIKE '@%')");
            } // if
          %>
          <td><%=EntityLookupLabel.script(facade, masterIsPapel ? PapelUsuario.LOOKUP_USUARIO : PapelUsuario.LOOKUP_PAPEL, request)%></td>
          <td><%=EntityFormLookupSelect.script(facade,
                                               PapelUsuario.CLASS_NAME,
                                               masterIsPapel ? PapelUsuario.LOOKUP_USUARIO : PapelUsuario.LOOKUP_PAPEL,
                                               papelUsuarioInfo,
                                               request,
                                               250,
                                               "",
                                               "",
                                               false)%></td>
          <%
            // retorna os filtros anteriores dos lookups
            lookupUsuario.setLookupFilterExpression(filterUsuario);
            lookupPapel.setLookupFilterExpression(filterPapel);
          %>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td></td>
          <td>
            <%=CommandControl.formButton(facade, formData, ImageList.COMMAND_SAVE, "Deseja mesmo salvar a " + PapelUsuario.ACTION.getCaption() + "?", true, false)%>&nbsp;
            <%=CommandControl.entityBrowseButton(facade, papelUsuario, PapelUsuario.ACTION, ImageList.COMMAND_BACK, "Voltar", "Volta para a consulta.", false)%>
          </td>
        </tr>
      </table>
    <%=formData.end()%>

  </body>
</html>

<%}
  catch (Exception e) {
    Controller.forwardException(e, pageContext);
  } // try-catch
%>
