<%@ page import="hw7.domain.Memory" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: i.gonchar
  Date: 2/24/2016
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Edit Memory:</h3>
Select Memory: <br/>
<form id="editMemory" action="/editMemory" method="post">

    <select size="4" name="memoryId">
        <option disabled>No selected ...................</option>
        <%
            String memoryId = (String) request.getAttribute("memoryId");
            List<Memory> memoryList = (List<Memory>) request.getAttribute("memoryList");
            if (memoryList != null && !memoryList.isEmpty()) {
                for (Memory memory : memoryList) {
        %>
        <option <%=(String.valueOf(memory.getId()).equals(memoryId)) ? "selected" : ""%>
                value="<%=String.valueOf(memory.getId())%>"><%=memory.getVendor()%>, <%=memory.getSize()%>
        </option>
        <% }
        } %>
    </select>
    <br/>
    Change Vendor: <br/>
    <input id="editMemoryVendor" type="text" name="vendor"> <br/>
    Change frequency: <br/>
    <input id="editMemorySize" type="text" name="size"> <br/>
    <input name = "editButton" type="submit" value="Edit Memory"/>
    <input name="deleteButton" type="submit"<%-- onclick="confirmDelete()"--%> value="Delete Memory"/>
</form>
<br/>
<a href="/hw7/notesAdvanced.jsp">
    <button>Back</button>
</a>
<p>
    ${reg_result}
</p>
<script>
    function confirmDelete() {
        var doc = document;
        var conf = confirm('Are you sure you want to delete?');
        if (conf) {
            var form = doc.getElementById('editMemory');
            form.submit();
        }
    }
</script>
</body>
</body>
</html>