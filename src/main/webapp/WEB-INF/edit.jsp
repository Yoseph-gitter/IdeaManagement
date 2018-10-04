
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
 
 <form:form method="POST" action="/ideas/${idea.id}/edit" modelAttribute="idea">
    	 <form:input type="hidden"  path="id"  />
	    <h1>Edit an Idea </h1>
        <p>
        	<form:label path="content">Idea:</form:label>
        	 <form:input path="content" value="${idea.content}" />
        	 <form:errors  path="content"/>
        </p>
        	 
    <input type="submit" value="Update"/>
    <a href="/ideas/${idea.id}/delete" style="decoration:underlined"   />Delete</a>
</form:form>
    