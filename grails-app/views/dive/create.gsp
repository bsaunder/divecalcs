<%@ page import="com.test.Dive" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Dive</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Dive List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Dive</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${diveInstance}">
            <div class="errors">
                <g:renderErrors bean="${diveInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="depth">Depth:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:diveInstance,field:'depth','errors')}">
                                    <input type="text" id="depth" name="depth" value="${fieldValue(bean:diveInstance,field:'depth')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:diveInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:diveInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
