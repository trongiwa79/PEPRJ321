<%-- 
    Document   : shopping
    Created on : Nov 7, 2020, 10:58:29 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.FULLNAME}">
            <font color="red">
                Welcome, ${sessionScope.FULLNAME}
            </font><br/>
            <a href="signOut">Sign Out</a><br/>
            <c:if test="${not empty sessionScope.ADMIN}">
                <a href="searchPage">Go to Search User Page</a>
            </c:if>
        </c:if>
        
        <h1>Shopping Page</h1>
        <form action="searchItem">
            Search <input type="text" name="txtSearchItem" value="${param.txtSearchItem}" />
            <input type="submit" value="Search" />
        </form><br/>
        
        <form action="viewCart">
            <input type="submit" value="View Your Cart" />
            <input type="hidden" name="lastSearchItem" value="${param.txtSearchItem}" />
        </form><br/>
        
        <c:set var="searchItem" value="${param.txtSearchItem}" />
        <c:if test="${not empty searchItem}">
            <c:set var="listItems" value="${requestScope.LISTITEMS}" />
            <c:if test="${not empty listItems}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Title</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Add Item</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${listItems}" varStatus="counter">
                                <form action="addItem">
                                    <tr>
                                        <td>
                                            ${counter.count}
                                            <input type="hidden" name="txtItemId" value="${item.id}" />
                                         .</td>
                                        <td>
                                            ${item.title}
                                            <input type="hidden" name="txtTitle" value="${item.title}" />
                                        </td>
                                        <td>
                                            ${item.quantity}
                                        </td>
                                        <td>
                                            ${item.price}
                                            <input type="hidden" name="txtPrice" value="${item.price}" />
                                        </td>
                                        <td>
                                            <input type="submit" value="Add To Cart" />
                                            <input type="hidden" name="lastSearchItem" value="${searchItem}" />
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                        </tbody>
                        </table><br/>
            </c:if>
            <c:if test="${empty listItems}">
                <h2>
                    No Item is matched!!
                </h2><br/>
            </c:if>
        </c:if>
        <c:if test="${empty sessionScope.FULLNAME}">
            <a href="loginPage">Go back to Login Page</a>
        </c:if>
    </body>
</html>
