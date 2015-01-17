
<%@include file="../include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.entity.*"%>

<%
  try {
    // nossa inst�ncia de Papel
    Papel papel = (Papel)facade.getEntity(Papel.CLASS_NAME);
    // nossa inst�ncia de Papel Usu�rio
    PapelUsuario papelUsuario = (PapelUsuario)facade.getEntity(PapelUsuario.CLASS_NAME);
    // nossa inst�ncia de Papel A��o
    PapelAcao papelAcao = (PapelAcao)facade.getEntity(PapelAcao.CLASS_NAME);
    // nossa inst�ncia de Papel Rela��o Mestre
    PapelRelacaoMestre papelRelacaoMestre = (PapelRelacaoMestre)facade.getEntity(PapelRelacaoMestre.CLASS_NAME);

    // PapelInfo para editar
    PapelInfo papelInfo = null;
    // se estamos incluindo...
    if (Controller.isInserting(request)) {
      // info em branco
      papelInfo = new PapelInfo();
    }
    // se estamos salvando...
    else if (Controller.isSaving(request)) {
      // info preenchido na p�gina
      papelInfo = (PapelInfo)papel.entityInfoFromRequest(request);
      // se estamos salvando e � um novo...inclui
      if (Controller.isSavingNew(request)) {
        papel.insert(papelInfo);
      }
      // se estamos salvando e � um existente...atualiza
      else {
        papel.update(papelInfo);
      } // if
      // comp�e a resposta
      Form.composeAjaxResponse(response, Form.COMPOSE_TYPE_JAVA_SCRIPT);
      // atualiza a consulta
      %><%=EntityGrid.forwardBrowse(facade, papel, Papel.ACTION, Controller.isSavingNew(request) ? EntityGrid.OPERATION_INSERT : EntityGrid.OPERATION_UPDATE, papelInfo)%><%
      // dispara
      return;
    }
    // se estamos editando...
    else if (Controller.isEditing(request)) {
      // info do registro para editar
      papelInfo =
        papel.selectByPapelId(
          NumberTools.parseInt(request.getParameter(Papel.FIELD_PAPEL_ID.getFieldAlias()))
        );
    }
    // se o comando � desconhecido...
    else {
      throw new Exception("Comando desconhecido.");
    } // if

    // nosso FrameBar
    FrameBar frameBar = new FrameBar(facade, "frameBarPapel");
    // nosso PageControl
    int tabSheetCount = 3;
    if (facade.masterRelationInformation().getActive())
      tabSheetCount++;
    PageControl pageControl = new PageControl(facade, "pageControlPapel", true);
    pageControl.setTabWidth(120);
    // nosso Form de dados
    Form formData = new Form(request, "formDataPapel", Papel.ACTION_CADASTRO, Papel.COMMAND_SAVE, "", true, true);
%>

<html>
  <head>
    <title><%=Papel.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body style="margin:0px;" onselectstart="return true;" oncontextmenu="return false;">

    <!-- inicia o FrameBar -->
    <%=frameBar.begin()%>

      <!-- �rea de frames -->
      <%=frameBar.beginFrameArea()%>

        <!-- Frame de identifica��o do objeto -->
        <%=frameBar.actionFrame(Papel.ACTION)%>

        <!-- Frame de comandos -->
        <%=frameBar.beginFrame("Comandos", false, false)%>
          <table style="width:100%;">
            <tr>
              <td><%=CommandControl.formHyperlink(facade, formData, ImageList.COMMAND_SAVE, "Deseja mesmo salvar o Papel?", true, false)%></td>
            </tr>
            <tr>
              <td><%=CommandControl.entityFormHyperlink(facade, papel, Papel.ACTION_CADASTRO, Papel.COMMAND_INSERT, ImageList.COMMAND_INSERT, false)%></td>
            </tr>
            <tr>
              <td><%=CommandControl.entityBrowseHyperlink(facade, papel, Papel.ACTION, ImageList.COMMAND_BACK, "Voltar", "Voltar para consulta", false)%></td>
            </tr>
          </table>
        <%=frameBar.endFrame()%>
      <%=frameBar.endFrameArea()%>

      <!-- Form de dados -->
      <%=formData.begin()%>
        <!-- dados ocultos -->
        <%=EntityFormEdit.script(facade, Papel.FIELD_PAPEL_ID, papelInfo, request, -1, "", "")%>
        <%=EntityFormEdit.script(facade, Papel.FIELD_HASH, papelInfo, request, -1, "", "")%>

        <!-- �rea de dados -->
        <%=frameBar.beginClientArea()%>
          <table style="width:100%; height:100%;">
            <tr style="height:40px;">
              <td>

                <!-- campos Nome e Privilegiado -->
                <table style="width:100%;">
                  <tr>
                    <td><%=EntityFieldLabel.script(facade, Papel.FIELD_NOME, request)%></td>
                    <td><%=EntityFieldLabel.script(facade, Papel.FIELD_PRIVILEGIADO, request)%></td>
                  </tr>
                  <tr>
                    <td style="width:100%;"><%=EntityFormEdit.script(facade, Papel.FIELD_NOME, papelInfo, request, 0, "", "")%></td>
                    <td style="width:70px;"><%=EntityFormSelect.script(facade, Papel.FIELD_PRIVILEGIADO, papelInfo, request, 70, "", "", false)%></td>
                  </tr>
                </table>

              </td>
            </tr>
            <tr style="height:40px;">
              <td>

                <!-- campo Descri��o -->
                <table style="width:100%;">
                  <tr>
                    <td><%=EntityFieldLabel.script(facade, Papel.FIELD_DESCRICAO, request)%></td>
                  </tr>
                  <tr>
                    <td><%=EntityFormEdit.script(facade, Papel.FIELD_DESCRICAO, papelInfo, request, 0, "", "")%></td>
                  </tr>
                </table>

              </td>
            </tr>
            <tr style="height:auto;">
              <td>

                <%=pageControl.begin()%>

                  <!-- Usu�rios -->
                  <%if (Controller.isInserting(request)) {%>
                    <%=pageControl.beginTabSheet("Usu�rios")%>
                      <span>N�o dispon�vel em tempo de inclus�o.</span>
                    <%=pageControl.endTabSheet()%>
                  <%}
                    else {%>
                    <%=pageControl.iFrameTabSheet("Usu�rios", PapelUsuario.ACTION.url(papelUsuario.USER_PARAM_MASTER.getName() + "=" + PapelUsuario.MASTER_IS_PAPEL + "&" + papelUsuario.USER_PARAM_PAPEL_ID.getName() + "=" + papelInfo.getPapelId()))%>
                  <%} // if%>

                  <!-- Tabela de Hor�rios -->
                  <%if (Controller.isInserting(request)) {%>
                    <%=pageControl.beginTabSheet("Hor�rios")%>
                      <span>N�o dispon�vel em tempo de inclus�o.</span>
                    <%=pageControl.endTabSheet()%>
                  <%}
                    else {%>
                    <%if (papelInfo.getPrivilegiado() == Papel.SIM) {%>
                      <%=pageControl.beginTabSheet("Hor�rios")%>
                        <span>Usu�rios que exercem Pap�is privilegiados possuem acesso irrestrito em todos os dias e horas.</span>
                      <%=pageControl.endTabSheet()%>
                    <%}
                      else {%>
                      <%=pageControl.iFrameTabSheet("Hor�rios", Papel.ACTION_TABELA_HORARIO.url(Papel.FIELD_PAPEL_ID.getFieldAlias() + "=" + papelInfo.getPapelId()))%>
                    <%} // if%>
                  <%} // if%>

                  <!-- Objetos -->
                  <%if (Controller.isInserting(request)) {%>
                    <%=pageControl.beginTabSheet("Objetos e Comandos")%>
                      <span>N�o dispon�vel em tempo de inclus�o.</span>
                    <%=pageControl.endTabSheet()%>
                  <%}
                    else {%>
                    <%if (papelInfo.getPrivilegiado() == Papel.SIM) {%>
                      <%=pageControl.beginTabSheet("Objetos e Comandos")%>
                        <span>Usu�rios que exercem Pap�is privilegiados possuem acesso irrestrito a todos Objetos.</span>
                      <%=pageControl.endTabSheet()%>
                    <%}
                      else {%>
                      <%=pageControl.iFrameTabSheet("Objetos e Comandos", PapelAcao.ACTION_CADASTRO.url(papelAcao.USER_PARAM_PAPEL_ID.getName() + "=" + papelInfo.getPapelId()))%>
                    <%} // if%>
                  <%} // if%>

                  <!-- Rela��o Mestre -->
                  <%if (facade.masterRelationInformation().getActive()) {%>
                    <%if (Controller.isInserting(request)) {%>
                      <%=pageControl.beginTabSheet(facade.masterRelationInformation().getCaption())%>
                         <span>N�o dispon�vel em tempo de inclus�o.</span>
                      <%=pageControl.endTabSheet()%>
                    <%}
                      else {%>
                      <%if (papelInfo.getPrivilegiado() == Papel.SIM) {%>
                        <%=pageControl.beginTabSheet(facade.masterRelationInformation().getCaption())%>
                          <span>Usu�rios que exercem Pap�is privilegiados possuem acesso irrestrito a todo(a) <%=facade.masterRelationInformation().getCaption()%>.</span>
                        <%=pageControl.endTabSheet()%>
                      <%}
                        else {%>
                        <%=pageControl.iFrameTabSheet(facade.masterRelationInformation().getCaption(), PapelRelacaoMestre.ACTION.url(papelRelacaoMestre.USER_PARAM_PAPEL_ID.getName() + "=" + papelInfo.getPapelId()))%>
                      <%} // if%>
                    <%} // if%>
                  <%} // if%>

                <%=pageControl.end()%>

              </td>
            </tr>
	         </table>

        <%=frameBar.endClientArea()%>
      <%=formData.end()%>
    <%=frameBar.end()%>

  </body>
</html>

<%}
  catch (Exception e) {
    Controller.forwardException(e, pageContext);
  } // try-catch
%>
