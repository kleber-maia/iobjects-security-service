
<%@include file="../include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.entity.*"%>

<%
  try {
    // nossa inst�ncia de Papel Rela��o Mestre
    PapelRelacaoMestre papelRelacaoMestre = (PapelRelacaoMestre)facade.getEntity(PapelRelacaoMestre.CLASS_NAME);

    // PapelRelacaoMestreInfo para editar
    PapelRelacaoMestreInfo papelRelacaoMestreInfo = null;
    // se estamos incluindo...
    if (Controller.isInserting(request)) {
      // info em branco
      papelRelacaoMestreInfo = new PapelRelacaoMestreInfo();
      // Papel Id
      papelRelacaoMestreInfo.setPapelId(NumberTools.parseInt(papelRelacaoMestre.USER_PARAM_PAPEL_ID.getValue()));
    }
    // se estamos salvando...
    else if (Controller.isSaving(request)) {
      // info preenchido na p�gina
      papelRelacaoMestreInfo = (PapelRelacaoMestreInfo)papelRelacaoMestre.entityInfoFromRequest(request);
      // se estamos salvando e � um novo...inclui
      if (Controller.isSavingNew(request)) {
        papelRelacaoMestre.insert(papelRelacaoMestreInfo);
      }
      // se estamos salvando e � um existente...atualiza
      else {
        papelRelacaoMestre.update(papelRelacaoMestreInfo);
      } // if
      // comp�e a resposta
      Form.composeAjaxResponse(response, Form.COMPOSE_TYPE_JAVA_SCRIPT);
      // atualiza a consulta
      %><%=EntityGrid.forwardBrowse(facade, papelRelacaoMestre, PapelRelacaoMestre.ACTION, Controller.isSavingNew(request) ? EntityGrid.OPERATION_INSERT : EntityGrid.OPERATION_UPDATE, papelRelacaoMestreInfo)%><%
      // dispara
      return;
    }
    // se estamos editando...
    else if (Controller.isEditing(request)) {
      // info do registro para editar
      papelRelacaoMestreInfo =
        papelRelacaoMestre.selectByPapelIdRelacaoMestre(
          NumberTools.parseInt(request.getParameter(PapelRelacaoMestre.FIELD_PAPEL_ID.getFieldAlias())),
          request.getParameter(PapelRelacaoMestre.FIELD_RELACAO_MESTRE.getFieldAlias())
        );
    }
    // se o comando � desconhecido...
    else {
      throw new Exception("Comando desconhecido.");
    } // if

    // nosso Form de dados
    Form formData = new Form(request, "formDataPapelRelacaoMestre", PapelRelacaoMestre.ACTION_CADASTRO, PapelRelacaoMestre.COMMAND_SAVE, "", true, true);
%>

<html>
  <head>
    <title><%=PapelRelacaoMestre.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body style="margin:0px;" onselectstart="return true;" oncontextmenu="return false;">

    <!-- Form de dados -->
    <%=formData.begin()%>
      <!-- dados ocultos -->
      <%=EntityFormEdit.script(facade, PapelRelacaoMestre.FIELD_PAPEL_ID, papelRelacaoMestreInfo, request, -1, "", "")%>
      <%=EntityFormEdit.script(facade, PapelRelacaoMestre.FIELD_HASH, papelRelacaoMestreInfo, request, -1, "", "")%>
      <!-- dados de Papel Rela��o Mestre -->
      <table>
        <!-- lookup Rela��o Mestre -->
        <tr>
          <td><%=EntityFieldLabel.script(facade, PapelRelacaoMestre.FIELD_RELACAO_MESTRE, request)%></td>
          <td><%=ExternalLookup.script(facade,
                                       facade.masterRelationInformation().getClassName(),
                                       facade.masterRelationInformation().getTableName(),
                                       facade.masterRelationInformation().getDisplayFieldNames(),
                                       facade.masterRelationInformation().getDisplayFieldTitles(),
                                       facade.masterRelationInformation().getDisplayFieldWidths(),
                                       facade.masterRelationInformation().getReturnFieldNames(),
                                       new String[0],
                                       facade.masterRelationInformation().getReturnUserFieldNames(),
                                       new String[0],
                                       facade.masterRelationInformation().getOrderFieldNames(),
                                       facade.masterRelationInformation().getFilterExpression(),
                                       ExternalLookup.SELECT_TYPE_SINGLE,
                                       facade.masterRelationInformation().getCaption(),
                                       PapelRelacaoMestre.FIELD_RELACAO_MESTRE.getFieldAlias(),
                                       "value != ''",
                                       "Obrigat�rio selecionar " + facade.masterRelationInformation().getCaption() + ".",
                                       "width:250px;",
                                       "")%></td>
        </tr>
        <!-- campo Privilegiado -->
        <tr>
          <td><%=EntityFieldLabel.script(facade, PapelRelacaoMestre.FIELD_PRIVILEGIADO, request)%></td>
          <td><%=EntityFormSelect.script(facade, PapelRelacaoMestre.FIELD_PRIVILEGIADO, papelRelacaoMestreInfo, request, 70, "", "", false)%></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <!-- bot�es de comando -->
        <tr>
          <td></td>
          <td>
            <%=CommandControl.formButton(facade, formData, ImageList.COMMAND_SAVE, "Deseja mesmo salvar a " + PapelRelacaoMestre.ACTION.getCaption() + "?", true, false)%>&nbsp;
            <%=CommandControl.entityBrowseButton(facade, papelRelacaoMestre, PapelRelacaoMestre.ACTION, ImageList.COMMAND_BACK, "Voltar", "Volta para a consulta.", false)%>
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
