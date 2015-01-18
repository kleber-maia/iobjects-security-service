
<%--
The MIT License (MIT)

Copyright (c) 2008 Kleber Maia de Andrade

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
--%>

<%@include file="../include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.entity.*"%>
<%@page import="securityservice.ui.entity.*"%>

<%
  try {
    // nossa instância de Papel Relação Mestre
    PapelRelacaoMestre papelRelacaoMestre = (PapelRelacaoMestre)facade.getEntity(PapelRelacaoMestre.CLASS_NAME);

    // se estamos excluindo...
    if (Controller.isDeleting(request)) {
      // registros selecionados
      PapelRelacaoMestreInfo[] selectedInfoList = (PapelRelacaoMestreInfo[])EntityGrid.selectedInfoList(papelRelacaoMestre, request);
      // compõe a resposta
      Form.composeAjaxResponse(response, Form.COMPOSE_TYPE_JAVA_SCRIPT);
      // nosso Grid
      EntityGrid entityGrid = EntityGrid.getInstance(facade, papelRelacaoMestre);
      // exclui e apaga do Grid
      for (int i=0; i<selectedInfoList.length; i++) {
        papelRelacaoMestre.delete(selectedInfoList[i]);%>
        <%=entityGrid.deleteRow(selectedInfoList[i])%>
      <%} // for
      // dispara
      return;
    } // if

    // define valores dos parâmetros de Relação Mestre
    papelRelacaoMestre.userParamList().setParamsValues(request);

    // lista que iremos exibir
    PapelRelacaoMestreInfo[] papelRelacaoMestreInfoList = new PapelRelacaoMestreInfo[0];

    // mensagem de erro na pesquisa
    String searchErrorMessage = "";
    try {
      // lista que iremos exibir
      papelRelacaoMestreInfoList = papelRelacaoMestre.selectByPapelId(
              NumberTools.parseInt(papelRelacaoMestre.USER_PARAM_PAPEL_ID.getValue())
            );
    }
    catch (Exception e) {
      searchErrorMessage = ExtendedException.extractMessage(e);
    } // try-catch

    // nosso Form de exclusão
    Form formDelete = new Form(request, "formDeletePapelRelacaoMestre", PapelRelacaoMestre.ACTION, PapelRelacaoMestre.COMMAND_DELETE, "", false, true);
    // nosso EventListener para o EntityGrid
    PapelRelacaoMestreEntityGridEventListener eventListener = new PapelRelacaoMestreEntityGridEventListener(facade);
    // nosso Grid
    EntityGrid entityGrid = EntityGrid.getInstance(facade, papelRelacaoMestre, 0, 0);
    entityGrid.addColumn(PapelRelacaoMestre.FIELD_RELACAO_MESTRE, 250);
    entityGrid.addColumn(PapelRelacaoMestre.FIELD_PRIVILEGIADO, 70);
    // manipulador de evento do Grid
    entityGrid.addEventListener(eventListener);
%>

<html>
  <head>
    <title><%=PapelRelacaoMestre.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body style="margin:0px;" onselectstart="return true;" oncontextmenu="return false;">

    <!-- Form de exclusão -->
    <%=formDelete.begin()%>
      <table style="width:100%; height:100%;">
        <tr style="height:auto;">
          <td>

            <!-- Grid -->
            <%=entityGrid.script(papelRelacaoMestreInfoList, "", searchErrorMessage)%>

          </td>
        </tr>
        <tr style="height:20px;">
          <td>
            <%=CommandControl.entityFormButton(facade, papelRelacaoMestre, PapelRelacaoMestre.ACTION_CADASTRO, PapelRelacaoMestre.COMMAND_INSERT, ImageList.COMMAND_INSERT, false)%>&nbsp;
            <%=CommandControl.formButton(facade, formDelete, ImageList.COMMAND_DELETE, "Deseja mesmo excluir o(s) registro(s) selecionado(s)?", true, false)%>
          </td>
        </tr>
      </table>
    <%=formDelete.end()%>

  </body>
</html>

<%}
  catch (Exception e) {
    Controller.forwardException(e, pageContext);
  } // try-catch
%>
