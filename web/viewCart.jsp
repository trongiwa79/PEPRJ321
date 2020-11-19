<%-- 
    Document   : viewCart
    Created on : Nov 8, 2020, 10:28:50 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>Your Cart</h1>
        <c:set var="cart" value="${sessionScope.CARTOBJ}" />
        <c:set var="items" value="${cart.items}" />
        <c:if test="${not empty items}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Title</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <form action="removeItem">
                    <c:forEach var="item" items="${items}" varStatus="counter">
                        <tr>
                            <td>
                                ${counter.count}
                                .</td>
                            <td>
                                ${item.value.title}
                            </td>
                            <td align="center">
                                ${item.value.quantity}
                            </td>
                            <td>
                                ${item.value.total}
                            </td>
                            <td align="center">
                                <input type="checkbox" name="chkItem" value="${item.key}" />
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3" align="right">
                            Total:
                        </td>
                        <td>
                            ${cart.totalPrice}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <c:url var="urlRewriting" value="searchItem">
                                <c:param name="txtSearchItem" value="${param.lastSearchItem}" />
                            </c:url>
                            <a href="${urlRewriting}">Add more Items to Your Cart</a>
                        </td>
                        <td>
                            <input type="submit" value="Remove Selected Item" />
                            <input type="hidden" name="lastSearchItem" value="${param.lastSearchItem}" />
                        </td>
                    </tr>
                </form>
                <form action="checkOut">
                    <tr>
                        <td colspan="5" align="center">
                            <input type="submit" value="Check Out" />
                            <input type="hidden" name="lastSearchValue" value="${param.lastSearchItem}" />
                        </td>
                    </tr>
                </form>
                </tbody>
            </table>
           
        </c:if>
        <c:if test="${empty items}">
            <h2>
                No Cart is existed!!
            </h2>
            <c:url var="urlRewriting" value="searchItem">
                <c:param name="txtSearchItem" value="${param.lastSearchItem}" />
            </c:url>
            <a href="${urlRewriting}">Add Items to Your Cart</a>
        </c:if>
    </body>
</html>
