<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../WEB-INF/common/shared-header.jsp"/>
    <jsp:useBean id="wrap" class="hu.alkfejl.model.Wrap" scope="request"/>
    <jsp:setProperty property="*" name="reservation"/>
    <title>Add a reservation</title>
</head>
<body>
<jsp:include page="/WEB-INF/common/menu.jsp"/>
<jsp:include page="/AddReservation"/>
<div class="container">
    <form action="/cinema_web_war/AddReservation" method="post" name="resForm">
        <input type="hidden" name="id" value="${wrap.reservation.id}"/>
        <div class="form-group">
            <label for="movie">Movie</label>
            <select name="movie" id="movie" class="form-control" onchange="document.resForm.submit()">
                <c:if test="${sessionScope.screenings == null}">
                    <option selected disabled hidden>Select a movie</option>
                </c:if>
                <c:forEach var="movie" items="${sessionScope.movies}" varStatus="loop">
                    <c:if test="${loop.index != sessionScope.movieIndex}">
                        <option value="${loop.index}"> ${movie.title}</option>
                    </c:if>
                    <c:if test="${loop.index == sessionScope.movieIndex}">
                        <option value="${loop.index}" selected> ${movie.title}</option>
                    </c:if>
                </c:forEach>
            </select>
            <label for="screening">Screening</label>
            <select name="screening" id="screening" class="form-control" onchange="document.resForm.submit()">
                <c:if test="${sessionScope.seats == null}">
                    <option selected disabled hidden>Select a screening</option>
                </c:if>
                <c:forEach var="screening" items="${sessionScope.screenings}" varStatus="loop">
                    <c:if test="${loop.index != sessionScope.screeningIndex}">
                        <option value="${loop.index}">Room: ${screening.room},
                            start: ${screening.date} ${screening.time}</option>
                    </c:if>
                    <c:if test="${loop.index == sessionScope.screeningIndex}">
                        <option value="${loop.index}" selected>Room: ${screening.room},
                            start: ${screening.date} ${screening.time}</option>
                    </c:if>
                </c:forEach>
            </select>
            <div class="form-group mb-2">
                <label for="seatsIn">Seats</label>
                <input name="seatsIn" type="text" class="form-control" id="seatsIn"
                       placeholder="Seats separated by ',' characters"/>
            </div>
            <div>
                Currently reserved seats
            </div>
            <table class="table table-striped table-dark">
                <c:forEach var="seat" items="${sessionScope.seats}" varStatus="loop">
                    <tr>
                        <td>
                            ${seat}
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <button id="sub" type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
