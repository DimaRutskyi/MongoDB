<%--
  Created by IntelliJ IDEA.
  User: al1
  Date: 21.11.15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%! public int counter = -1; %>
<html>
<head>
    <title>Igor Web Application</title>
</head>
<body>

<table border="1">
    <tbody>

    <form action="/authorizationForm" method="post">
        <tr>
            <td rowspan="5"><img
                    src="http://medias.lescontamines.com/images/prestataires/multitailles/800x600_22262-Logo_taxi.jpg"
                    height="200"/>
            </td>
            <td  align="center"><b>Login Form:</b></td>
        </tr>
        <td><input type="text" name="login"/></td>
        <tr>
            <td><input type="password" name="password"/></td>
        </tr>

        <tr>
            <td><input type="submit" value="Login" style="width:100%"/></td>

        </tr>
    </form>
    <tr>
        <td><a href="registerPage.jsp">
            <button style="width:100%">Register Now</button>
        </a></td>

    </tr>
    </tbody>
</table>
<p>
    ${auth_result}
</p>

</body>
</html>