<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="../WEB-INF/common/shared-header.jsp"/>
</head>
    <body>
        <div class="container">
            <form action="../RegisterController" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input required name="username" type="text" class="form-control" id="username"
                           placeholder="Username"/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input required name="password" type="password" class="form-control" id="password"
                           placeholder="Password"/>
                </div>
                <button id="submit" type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </body>
</html>
