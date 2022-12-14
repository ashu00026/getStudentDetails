<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Student database!!</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
   <div id="wrapper">
   <div id="header">
   <h2>GmrIt students!</h2>
   </div>
   </div>
   <div id="container">
   <div id="content">
   <input type="button" value="add student!" 
   onclick="window.location.href='add-student-form.jsp';return false;" 
   class="add-student-button"/>
   <table>
   <tr>
   <th>First Name</th>
   <th>Last Name</th>
   <th>Email</th>
   <th>Action</th>
   </tr>
   <c:forEach var="tempStudent" items="${students_list}">
   <c:url var="tempLink" value="studenControllerServlet">
   <c:param name="command" value="LOAD"/>
   <c:param name="studentId" value="${tempStudent.id}"/>
   </c:url>
   <c:url var="deleteLink" value="studenControllerServlet">
   <c:param name="command" value="DELETE"/>
   <c:param name="studentId" value="${tempStudent.id}"/>
   </c:url>
   
   <tr>
   <td>${tempStudent.firstName}</td>
   <td>${tempStudent.lastName}</td>
   <td>${tempStudent.email}</td>
   <td><a href="${tempLink}">update</a>|<a href="${deleteLink}" onClick="if(!(confirm('Are you sure you want to delete the Student?'))) return false">delete</a></td>
   </tr>
   </c:forEach>
   </table>
   </div>
   </div>

</body>
</html>