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
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <c:set var="id" value="${id}"></c:set>
                            <c:forEach items="${listUsers}" var="user">
                                <c:set var="u_id" value="${user.id}"></c:set>
                                <c:if test="${id==u_id}">
                                    <h3 class="profile-username text-center">${user.email}</h3>
                                </c:if>
                            </c:forEach>
                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <c:set var="nb_resa" value="${nb_resa}"></c:set>
                                    <b>Reservation(s)</b> <a class="pull-right">${nb_resa}</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#rents" data-toggle="tab">Reservations</a></li>
                            <li><a href="#cars" data-toggle="tab">Voitures</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Voiture</th>
                                            <th>Date de debut</th>
                                            <th>Date de fin</th>
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
                                                <td>${rent.debut_res}</td>
                                                <td>${rent.fin_res}</td>                                            
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="cars">
                                <!-- /.box-header -->  
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Constructeur</th>
                                            <th>Modele</th>
                                            <th style=>Nombre de places</th>
                                        </tr>
                                        <c:forEach items="${listRents}" var="rent">
                                            <tr>
                                                <c:forEach items="${listVehicles}" var="vehicle">
                                                    <c:set var="v_id" value="${vehicle.id}"></c:set>
                                                    <c:set var="r_vId" value="${rent.vehicleId}"></c:set>
                                                    <c:if test="${v_id==r_vId}">
                                                        <td>${vehicle.id}</td>
                                                        <td>${vehicle.constructeur}</td>
                                                        <td>${vehicle.modele}</td>
                                                        <td>${vehicle.nbPlaces}</td>
                                                    </c:if>
                                                </c:forEach>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
