<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Utilisateurs
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/users/create">Ajouter</a>                
            </h1>     
            <form method="post" action="${pageContext.request.contextPath}/users?search=true">
                <td>Entrez le nom du client </td>
                <input type="text" id="nom" name="nom" placeholder="Nom du client">              
                <button type="submit" class="btn btn-primary">Rechercher</button>
            </form>              
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Nom</th>
                                    <th>Prenom</th>
                                    <th>Email</th>
                                    <th>Date de naissance</th>
                                    <th>Action</th>                     
                                </tr>
                                <c:forEach items="${ listUsers }" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td>${user.nom}</td>
                                        <td>${user.prenom}</td>
                                        <td>${user.email}</td>
                                        <td>${user.birth_date}</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/users?id=${user.id}">
                                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/users/details?id=${user.id}">
                                                    <i class="fa fa-play"></i>
                                                </a>
                                                <a class="btn btn-success" href="${pageContext.request.contextPath}/users/update?id=${user.id}">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <button type="submit" class="btn btn-danger">
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
