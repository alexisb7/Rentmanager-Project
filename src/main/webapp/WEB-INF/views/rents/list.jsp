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
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/create">Ajouter</a>
            </h1>
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
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach items="${listRents}" var="rent">
                                    <tr>
                                        <td>${rent.id}</td>
                                        <td>
                                            <c:forEach items="${listVehicles}" var="vehicle">
                                                <c:set var="v_id" value="${vehicle.id}"></c:set>
                                                <c:set var="r_vId" value="${rent.vehicleId}"></c:set>
                                                <c:if test="${v_id==r_vId}">
                                                    ${vehicle.constructeur} ${vehicle.modele}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach items="${listUsers}" var="user">
                                                <c:set var="u_id" value="${user.id}"></c:set>
                                                <c:set var="r_uId" value="${rent.clientId}"></c:set>
                                                <c:if test="${u_id==r_uId}">
                                                    ${user.prenom} ${user.nom}
                                                </c:if>
                                            </c:forEach>
                                        </td>
                                        <td>${rent.debut_res}</td>
                                        <td>${rent.fin_res}</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/rents?id=${rent.id}">
                                                <a class="btn btn-success" href="${pageContext.request.contextPath}/rents/update?id=${rent.id}">
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
