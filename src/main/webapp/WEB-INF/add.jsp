
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
 <form:form method="POST" action="/ideas/new" modelAttribute="idea">
    	 
	    <h1>Create a new Idea </h1>
        <p>
        	<form:label path="content">Idea:</form:label>
        	 <form:input path="content" value="${idea.content}" />
        	 <form:errors  path="content"/>
        </p>
        	 
    <input type="submit" value="Create"/>
</form:form>
    