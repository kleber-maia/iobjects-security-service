
<%@include file="../include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.entity.*"%>

<%
  try {
    // nossa inst�ncia de Papel
    Papel papel = (Papel)facade.getEntity(Papel.CLASS_NAME);

    // PapelInfo para editar
    PapelInfo papelInfo = null;

    // info do registro para editar
    papelInfo = papel.selectByPapelId(
                  NumberTools.parseInt(request.getParameter(Papel.FIELD_PAPEL_ID.getFieldAlias()))
                );

    // se estamos salvando...
    if (Controller.isSaving(request)) {
      // obt�m os hor�rios permitidos por dia
      boolean[][] arrayHorario = new boolean[7][24];
      for (int i=0; i<7; i++) {
        // hor�rios marcados na p�gina para o dia
        String[] stringDay = request.getParameterValues("d" + i);
        // se temos hor�rios para o dia...
        if (stringDay != null) {
          // marca os hor�rios no nosso array
          for (int w=0; w<stringDay.length; w++)
            arrayHorario[i][NumberTools.parseInt(stringDay[w])] = true;
        } // if
      } // for

      // define a nova Tabela Hor�rio
      papelInfo.setTabelaHorario(papel.encodeTabelaHorario(arrayHorario));
      // atualiza
      papel.update(papelInfo);
    } // if

    // obt�m os hor�rios permitidos por dia
    boolean[][] arrayHorario = papel.decodeTabelaHorario(papelInfo.getTabelaHorario());

    // nosso Form de dados
    Form formData = new Form(request, "formDataPapelTabelaHorario", Papel.ACTION_TABELA_HORARIO, Papel.COMMAND_SAVE_TABELA_HORARIO, "", false);
    // nosso Grid de hor�rio
    Grid gridHorario = new Grid(facade, "gridHorario", 0, 0);
    gridHorario.columns().add(new Grid.Column("!", 50, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d0);' title='Marca/desmarca todos.'>Domingo</a>", 100, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d1);' title='Marca/desmarca todos.'>Segunda</a>", 100, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d2);' title='Marca/desmarca todos.'>Ter�a</a>", 100, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d3);' title='Marca/desmarca todos.'>Quarta</a>", 100, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d4);' title='Marca/desmarca todos.'>Quinta</a>", 100, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d5);' title='Marca/desmarca todos.'>Sexta</a>", 100, Grid.ALIGN_CENTER));
    gridHorario.columns().add(new Grid.Column("<a href='javascript:checkUncheckAll(formDataPapelTabelaHorario.d6);' title='Marca/desmarca todos.'>S�bado</a>", 100, Grid.ALIGN_CENTER));
%>

<html>
  <head>
    <title><%=Papel.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body style="margin:0px;" onselectstart="return true;" oncontextmenu="return false;">

    <script type="text/javascript">

      function checkUncheckAll(day) {
        // iremos marcar ou desmarcar todos?
        var checked = !day[0].checked;
        // loop nos hor�rios do dia
        for (i=0; i<day.length; i++) {
          day[i].checked = checked;
        } // for
      }

    </script>

    <!-- Form de dados -->
    <%=formData.begin()%>
      <!-- dados ocultos -->
      <%=EntityFormEdit.script(facade, Papel.FIELD_PAPEL_ID, papelInfo, request, -1, "", "")%>

      <!-- tabela de hor�rio -->
      <table style="width:100%; height:100%;">
        <tr style="height:auto;">
          <td>
            <%=gridHorario.begin()%>
              <%for (int i=0; i<24; i++) {%>
              <%=gridHorario.addRow(new String[]{i + "h",
                                    "<input name='d0' type='checkbox' value='" + i + "' " + (arrayHorario[0][i] ? "checked=checked" : "") + " style='height:15px;' />",
                                    "<input name='d1' type='checkbox' value='" + i + "' " + (arrayHorario[1][i] ? "checked=checked" : "") + " style='height:15px;' />",
                                    "<input name='d2' type='checkbox' value='" + i + "' " + (arrayHorario[2][i] ? "checked=checked" : "") + " style='height:15px;' />",
                                    "<input name='d3' type='checkbox' value='" + i + "' " + (arrayHorario[3][i] ? "checked=checked" : "") + " style='height:15px;' />",
                                    "<input name='d4' type='checkbox' value='" + i + "' " + (arrayHorario[4][i] ? "checked=checked" : "") + " style='height:15px;' />",
                                    "<input name='d5' type='checkbox' value='" + i + "' " + (arrayHorario[5][i] ? "checked=checked" : "") + " style='height:15px;' />",
                                    "<input name='d6' type='checkbox' value='" + i + "' " + (arrayHorario[6][i] ? "checked=checked" : "") + " style='height:15px;' />"
                                    })%>
              <%} // for%>
            <%=gridHorario.end()%>
          </td>
        </tr>
        <!-- bot�es de comando -->
        <tr style="height:20px;">
          <td>
            <%=CommandControl.formButton(facade, formData, ImageList.COMMAND_SAVE, "Deseja mesmo salvar os Hor�rios selecionados para o Papel?", true, false)%>&nbsp;
            <%=Button.script(facade, "buttonCancelar", "Cancelar", "Cancela a sele��o de Hor�rios para o Papel.", ImageList.COMMAND_DELETE, "", Button.KIND_DEFAULT, "width:auto;", "window.location.href='" + Papel.ACTION_TABELA_HORARIO.url(Papel.FIELD_PAPEL_ID.getFieldAlias() + "=" + papelInfo.getPapelId()) + "';", false)%>
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
