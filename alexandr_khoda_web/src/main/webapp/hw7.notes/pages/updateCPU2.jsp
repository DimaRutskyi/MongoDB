<%--
  Created by IntelliJ IDEA.
  User: al1
  Date: 21.11.15
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="static hw7.notes.view.Servlet.*" %>
<%@ page import="hw7.notes.domain.CPU" %>
<%@ page import="java.util.List" %>
<%@ page import="hw7.notes.dao.CPUDao" %>
<%@ page import="hw7.notes.view.Menu" %>
<%@ page import="hw7.notes.service.NotebookServiceImpl" %>
<%@ page import="hw7.notes.domain.Vendor" %>
<%@ page import="hw7.notes.dao.VendorDao" %>
<%--<%@ page errorPage="/hw7.notes/pages/generalErrorPage.jsp" %>--%>
<script src="/hw7.notes/JS/select.js" type="text/javascript">    </script>
<script src="/hw7.notes/JS/notebooks.js" type="text/javascript">    </script>

<html>

<head>
    <title>Update CPU type</title>
    <style>
        <%@include file='/hw7.notes/css/addNotebook.css' %>
    </style>
    <center><h1>Update CPU type</h1></center><br>
</head>
<body>
<%! CPUDao cpuDao;
    VendorDao vendorDao;
    List<CPU> cpu;
    List<Vendor> vendor;
%>
<%
    cpuDao = ((NotebookServiceImpl) Menu.service).getCpuDao();
    vendorDao = ((NotebookServiceImpl) Menu.service).getVendorDao();
    vendor = (List<Vendor>)vendorDao.findAll();
    request.setAttribute("vendor", vendor);
%>

<form action="/AddCpu" method="get">
    <img src="/hw7.notes/img/cpu.jpg" align="left" style="margin-right: 20px;
     width: 200px; height: auto">


    <div id="divVenSel">
        <label for="venSel">SELECT VENDOR:</label>
        <select size="1" name="venSel" id="venSel">
            <option disabled>select item</option>
            <c:forEach var="v" items="${vendor}" varStatus="cnt">
                <c:choose>
                    <c:when test="${SelInx != null}">
                        <c:if test="${cnt.index == SelInx}">
                            <option value="${v.id}" selected>${v.name}</option>
                        </c:if>
                        <c:if test="${cnt.index != SelInx}">
                            <option value="${v.id}">${v.name}</option>
                        </c:if>
                    </c:when>
                    <c:when test="${SelVal != null}">
                        <c:if test="${v.id == SelVal}">
                            <option value="${v.id}" selected>${v.name}</option>
                        </c:if>
                        <c:if test="${v.id != SelVal}">
                            <option value="${v.id}">${v.name}</option>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <option value="${v.id}">${v.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select><br>
    </div>


    <label for="freq">FREQUENCY:</label>
    <input  type="text" value="${freqA}" placeholder="3.5G"
            name="freq" id="freq"><br>

    <label for="model">MODEL:</label>
    <input  type="text" value="${modelA}" placeholder="Quad"
            name="model" id="model"><br>
    <br><br>
    <br><br>
    <p style="text-align: center;">
        <input type="submit" name="back" value="&longleftarrow; back">
        <input type="submit" name="add" value="Add">

        <input type="button" value="Clear All"
               onclick="setSelectIndex('venSel', 0);
               clearElemContent('freq');
               clearElemContent('model');">
    </p>
    <br>
    <br>
    <label id="message" style="width: 100%; margin-top:10%;
            color:${messageColor == null ? 'brown' : messageColor};
            text-align: center; font-size:x-large">${messageText}
    </label>
</form>

</body>
</html>