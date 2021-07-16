<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>\
<head>
    <title> Login Webapp </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6Z
    MFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">SSC Login Webapp</a>
            <a class="btn btn-light pull-right" type="button" href="/logout">
                <i class="fa fa-sign-out"></i> &nbsp; Logout
            </a>
        </div>
    </nav>
    <c:if test="${not empty message}">
        <c:choose>
            <c:when test="${hasError}">
                <div class="alert alert-danger" role="alert">
                        ${message}
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-success" role="alert">
                        ${message}
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>
    <div class="row justify-content-sm-center h-100">
        <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">

            <h2 class = "justify-content-sm-center"> Create New User</h2>
            <div style="color:red"
                 class="mb-3">${error}</div>
            <form action="/user/create" method="POST">
                <div class="input-group mb-4 input-group-md">
                        <span class="input-group-text" id="username" style="width: 40px">
                            <i class="fa fa-user"></i>
                        </span>
                    <input type="text" class="form-control" name="username" placeholder="Username" aria-label="Username"
                           aria-describedby="username" autocomplete="off" value="${username}">
                </div>
                <div class="input-group mb-4 input-group-md">
                        <span class="input-group-text" id="displayName" style="width: 40px">
                            <i class="fa fa-user"></i>
                        </span>
                    <input type="text" class="form-control" name="displayName" placeholder="Display Name"
                           aria-label="displayName"
                           aria-describedby="displayName" autocomplete="off" value="${displayName}">
                </div>
                <div class="input-group mb-4 input-group-md">
                        <span class="input-group-text" id="password" style="width: 40px">
                            <i class="fa fa-key"></i>
                        </span>
                    <input type="password" class="form-control" name="password" placeholder="Password"
                           aria-label="Password" aria-describedby="password" autocomplete="off" value="${password}">
                </div>
                <div class="input-group mb-4 input-group-md">
                        <span class="input-group-text" id="cpassword" style="width: 40px">
                            <i class="fa fa-key"></i>
                        </span>
                    <input type="password" class="form-control" name="cpassword" placeholder="Comfirm Password"
                           aria-label="Password" aria-describedby="cpassword" autocomplete="off" value="${cpassword}">
                </div>

                <div class="d-grip gap-2">
                    <button class="btn btn-primary" type="submit"><i class="fa fa-plus"></i> Create New User</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

