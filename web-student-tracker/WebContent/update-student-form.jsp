<html>
<head>
<title>
Update Student!
</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/add-student-style" rel="stylesheet" type="text/css">
</head>
<body>
<div id="wrapper">
   <div id="header">
   <h2>GmrIt students!</h2>
   <h3>Update Student</h3>
   </div>
   </div>
   <div id="container">
   <form action="studenControllerServlet" method="GET">
   <input type="hidden" name="command" value="UPDATE">
      <input type="hidden" name="studentId" value="${the_student.id}">
   
first Name:
<input type="text" name="firstName" value="${the_student.firstName}"/><br>
last Name:
<input type="text" name="lastName" value="${the_student.lastName}"/><br>
email:
<input type="text" name="email" value="${the_student.email}"/><br>
<input type="submit" value="Save" class="save" />
</form></div>
<div style="clear: both;"></div>
<p>
<a href="studenControllerServlet">Back to List</a>
</p>
</body></html>