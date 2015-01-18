
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

<%@include file="include/beans.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="securityservice.*"%>
<%@page import="securityservice.entity.*"%>

<%
  // nossa instância de SecurityService
  SecurityService securityService = (SecurityService)facade.getBusinessObject(SecurityService.CLASS_NAME);
%>

<html>
  <head>
    <title><%=SecurityService.ACTION.getCaption()%></title>
    <link href="<%=facade.getStyle().userInterface()%>" rel="stylesheet" type="text/css">
    <script src="include/scripts.js" type="text/javascript"></script>
  </head>
  <body class="FrameBar" style="margin:4px;" onselectstart="return true;" oncontextmenu="return false;">

    <!-- barra de ferramentas e área cliente -->
    <table cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
      <tr>
        <td style="height:25px;">

          <!-- barra de ferramentas -->
          <table class="ToolBar" cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
            <tr>
              <td>
                <%=Button.script(facade,
                                 "toolbuttonPapel",
                                 "Papéis",
                                 "Gerenciar Papéis",
                                 securityService.extension().imageList().getImageUrl("papel16x16.gif"),
                                 "",
                                 Button.KIND_TOOLBUTTON,
                                 "width:100px;",
                                 "javascript:frameSecurityService.location.href='" + Papel.ACTION.url() + "';",
                                 false)%>
                <%=Button.script(facade,
                                 "toolbuttonUsuario",
                                 "Usuários",
                                 "Gerenciar Usuários",
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

          <!-- Papéis e Usuários -->
          <iframe name="frameSecurityService" src="<%=Papel.ACTION.url()%>" frameborder="0" scrolling="no" style="width:100%; height:100%;">
          </iframe>

        </td>
      </tr>
    </table>

  </body>
</html>
