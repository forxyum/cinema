<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservations</title>
    <jsp:include page="../WEB-INF/common/shared-header.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/common/menu.jsp"/>
<jsp:include page="/ReservationController"/>
    <div class="container">
        <table class="table">
            <thead class = "thead-dark">
                <tr>
                    <th scope="col">Title</th>
                    <th scope="col">Start of screening</th>
                    <th scope="col">Room</th>
                    <th scope="col">Reserved seats</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="reservation" items="${requestScope.reservationList}" varStatus="loop">
                <tr>
                    <td>${requestScope.titleList.get(loop.index)}</td>
                    <td>${requestScope.startList.get(loop.index)}</td>
                    <td>${requestScope.roomList.get(loop.index)}</td>
                    <td>${requestScope.seatList.get(loop.index)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
