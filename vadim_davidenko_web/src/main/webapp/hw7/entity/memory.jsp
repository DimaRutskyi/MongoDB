<%--
  Created by IntelliJ IDEA.
  User: Вадим
  Date: 14.02.2016
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<table align="center">
  <tr><td>
    <form name="memoryForm" action="/memoryServlet" method="post">
        <input type="hidden" name="entityId">
      <table border="0" cellpadding="6" style="background-color: #d4ecff">
        <tr><td colspan="2" align="center"><h3>Memory registration</h3></td></tr>
        <tr>
          <td align="right">Vendor name:</td>
          <td><input type="text" name="vendor" size="20" maxlength="20"/></td>
        </tr>
        <tr>
          <td align="right">Memory size:</td>
          <td><input type="text" name="size" size="10" maxlength="10"/></td>
        </tr>
        <tr><td colspan="2"><hr/></td></tr>
        <tr>
          <td colspan="2" align="center">
            <input type="button" value="Save" onclick="submitForm()" style="width: 80px"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="hw7/menu.jsp"><input type="button" value="Back" style="width: 80px"/></a>
          </td>
        </tr>
      </table>
    </form>
  </td></tr>
  <tr><td colspan="2" align="center">
    <b>${server_msg}</b>
  </td></tr>
</table>

<script>
  document.memoryForm.entityId.value = '${entityId}';
  document.memoryForm.vendor.value = '${vendor}';
  document.memoryForm.size.value = '${size}';

  function submitForm() {
    var form = document.memoryForm;
    if(!form.vendor.value.trim() || !form.size.value.trim() || isNaN(+form.size.value)) {
      alert("Please, fill in all fields with valid values!");
    } else {
      form.submit();
    }
  }
</script>


</body>
</html>
