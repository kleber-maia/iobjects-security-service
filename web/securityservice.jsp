
<%@include file="include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.*"%>
<%@page import="securityservice.entity.*"%>

<%
  // nossa inst�ncia de SecurityService
  SecurityService securityService = (SecurityService)facade.getBusinessObject(SecurityService.CLASS_NAME);
%>

<html>
  <head>
    <title><%=SecurityService.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body class="FrameBar" style="margin:4px;" onselectstart="return true;" oncontextmenu="return false;">

    <!-- barra de ferramentas e �rea cliente -->
    <table cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
      <tr>
        <td style="height:25px;">

          <!-- barra de ferramentas -->
          <table class="ToolBar" cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
            <tr>
              <td>
                <%=Button.script(facade,
                                 "toolbuttonPapel",
                                 "Pap�is",
                                 "Gerenciar Pap�is",
                                 securityService.extension().imageList().getImageUrl("papel16x16.gif"),
                                 "",
                                 Button.KIND_TOOLBUTTON,
                                 "width:100px;",
                                 "javascript:frameSecurityService.location.href='" + Papel.ACTION.url() + "';",
                                 false)%>
                <%=Button.script(facade,
                                 "toolbuttonUsuario",
                                 "Usu�rios",
                                 "Gerenciar Usu�rios",
                                 securityService.extension().imageList().getImageUrl("usuario16x16.gif"),
                                 "",
                                 Button.KIND_TOOLBUTTON,
                                 "width:100px;",
                                 "javascript:frameSecurityService.location.href='" + Usuario.ACTION.url() + "';",
                                 false)%>
              </td>
            </tr>
          </table>

        </td>
      </tr>
      <tr style="height:auto;">
        <td>

          <!-- Pap�is e Usu�rios -->
          <iframe name="frameSecurityService" src="<%=Papel.ACTION.url()%>" frameborder="0" scrolling="no" style="width:100%; height:100%;">
          </iframe>

        </td>
      </tr>
    </table>

  </body>
</html>
