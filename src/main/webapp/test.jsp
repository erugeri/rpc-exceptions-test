<%@ page import="com.test.GenerateCalls" %>
<html>
<head>
  <script language="javascript">
    setTimeout(function(){ window.location.href = "/"; }, 10);
  </script>
</head>
<body>
  <h1>Test RPC!</h1>
  <p><%=GenerateCalls.generate() %></p>
</body>
</html>
