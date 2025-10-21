<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>
<c:url value="/login" var="loginUrl"/>  
<form action="${loginUrl}" method="post">         
    <c:if test="${param.error != null}">          
        <p>  
            Invalid username and password.  
        </p>  
    </c:if>  
    <c:if test="${param.logout != null}">         
        <p>  
            You have been logged out.  
        </p>  
    </c:if>  
    <p>  
        <label for="username">Username</label>  
        <input type="text" id="username" name="email"/>
    </p>  
    <p>  
        <label for="password">Password</label>  
        <input type="password" id="password" name="password"/>      
    </p>
    <input type="hidden"
            name="${_csrf.parameterName}"
            value="${_csrf.token}"/>
        <button type="submit" class="btn">Log in</button>
    </form>

    </body>