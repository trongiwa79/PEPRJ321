<%-- 
    Document   : search
    Created on : Nov 7, 2020, 8:42:46 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.FULLNAME}">
            <c:redirect url="/loginPage" />
        </c:if>
        <c:if test="${empty sessionScope.ADMIN}">
            <c:redirect url="/shoppingPage" />
        </c:if>

        <font color="red">
        Welcome, ${sessionScope.FULLNAME}
        </font><br/>
        <a href="signOut">Sign Out</a><br/>
        <a href="shoppingPage">Go to Shopping Page</a><br/>

        <c:set var="university" value="FPT.,Van Lang,;Hutech,Hoa Sen,," scope="page"/>
        <c:forTokens var="uni" items="${university}" delims=",;">
            <c:out value="${uni}" /><br/>
        </c:forTokens>  

        <h1>Search</h1>
        <form action="searchLastname">
            Search <input type="text" name="txtSearchValue" 
                          <c:if test="${not empty param.txtSearchValue}">
                              value="${param.txtSearchValue}" 
                          </c:if>
                          <c:if test="${empty param.txtSearchValue}">
                              value="${param.lastSearchValue}"
                          </c:if>
                          />
            <input type="submit" value="Search" />
        </form><br/>

        <c:if test="${not empty param.txtSearchValue}">
            <c:set var="searchValue" value="${param.txtSearchValue}" />
        </c:if>
        <c:if test="${empty param.txtSearchValue}">
            <c:set var="searchValue" value="${param.lastSearchValue}" />
        </c:if>
        <c:if test="${not empty searchValue}">
            <c:set var="listUsers" value="${requestScope.LISTUSERS}" />
            <c:catch var="ex">
                <sql:setDataSource var="con" dataSource="DBREG" />
                <c:if test="${not empty con}">
                    <sql:query var="rs" dataSource="">
                        Select username as Username, Password as Pass, Lastname as Fullname, isAdmin as Role From Registration Where lastname Like ?
                        <sql:param value="%${searchValue}%"/>
                    </sql:query>
                        <c:if test="${rs.rowCount eq 0}">
                            <h2>No Record is matched</h2>
                        </c:if>
                    <c:if test="${not empty rs and rs.rowCount gt 0}">
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                        <c:forEach var="colName" items="${rs.columnNames}">
                                        <th>${colName}</th>
                                        </c:forEach> 
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="row" items="${rs.rowsByIndex}" varStatus="counter">
                                    <tr>
                                        <td>
                                            ${counter.count}
                                        .</td>
                                        <c:forEach var="field" items="${row}">
                                        <td>${field}</td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </c:if>
                </c:if>
            </c:catch>
            <c:if test="${not empty ex}">
                <font color="red">
                Errors occur: <br/>
                ${ex}
                </font>
            </c:if>
            <c:if test="${not empty listUsers}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Fullname</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${listUsers}" varStatus="counter">
                        <form action="updateAccount">
                            <tr>
                                <td>
                                    ${counter.count}
                                    .</td>
                                <td>
                                    ${user.username}
                                    <input type="hidden" name="txtUsername" value="${user.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword" value="${user.password}" />
                                </td>
                                <td>
                                    ${user.fullname}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkAdmin" value="ON" 
                                           <c:if test="${user.role}">
                                               checked="checked"
                                           </c:if>
                                           />

                                </td>
                                <td>
                                    <c:url var="urlRewriting" value="deleteAccount">
                                        <c:param name="pk" value="${user.username}" />
                                        <c:param name="lastSearchValue" value="${searchValue}" />
                                    </c:url>
                                    <a href="${urlRewriting}">Delete</a>
                                </td>
                                <td>
                                    <input type="submit" value="Update" />
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
            <c:set var="errors" value="${requestScope.UPDATEERRORS}" />
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty errors.updateYourselfErr}">
                <font color="red">
                ${errors.updateYourselfErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty errors.updateAdminRoleErr and empty errors.updateYourselfErr}">
                <font color="red">
                ${errors.updateAdminRoleErr}
                </font><br/>
            </c:if>
            <c:set var="delErrs" value="${requestScope.DELETEERROR}" />
            <c:if test="${not empty delErrs.deleteYourselfErr}">
                <font color="red">
                ${delErrs.deleteYourselfErr}
                </font><br/>
            </c:if>
            <c:if test="${not empty delErrs.deleteAdminErr and empty delErrs.deleteYourselfErr}">
                <font color="red">
                ${delErrs.deleteAdminErr}
                </font>
            </c:if>
        </c:if>
        <c:if test="${empty listUsers}">
            <h2>
                No User is matched!!
            </h2>
        </c:if>
    </c:if>

</body>
</html>
