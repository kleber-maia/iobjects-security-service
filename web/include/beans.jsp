
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
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

<%@page import="iobjects.*"%>
<%@page import="iobjects.entity.*"%>
<%@page import="iobjects.process.*"%>
<%@page import="iobjects.report.*"%>
<%@page import="iobjects.servlet.*"%>
<%@page import="iobjects.sql.*"%>
<%@page import="iobjects.util.*"%>
<%@page import="iobjects.util.print.*"%>
<%@page import="iobjects.ui.*"%>
<%@page import="iobjects.ui.entity.*"%>
<%@page import="iobjects.ui.param.*"%>
<%@page import="iobjects.ui.treeview.*"%>
<%@page import="iobjects.ui.report.*"%>

<%// beans de escopo de sessão%>
<jsp:useBean id="facade" type="iobjects.Facade" scope="session" />
