<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
    <style type="text/css">
    		.login{
    		  display: inline-block ;
    		  vertical-align : top;
    		  min-width : 20% ;
    		  margin :30px 15px 30px 50px;
    		}
    	
    	.registration{
    		   display: inline-block ;
    		   vertical-align : top;
	   		   min-width : 17% ;
	   		   margin :30px 15px 30px 50px;
    		  }
    	.labels{
    		min-width: 100px;
    	} 
    	.classError{
    		color: red ;
    	}
    </style>
</head>
<body>
    <fieldset class="registration">
    <legend><h1>Register!</h1></legend>
        
         <p><form:errors class="classError" path="user.*"/></p> 
        
        <form:form method="POST" action="/registration" modelAttribute="user">
                <p>
                    <form:label class="labels" path="name">Name:</form:label></br>
                    <form:input type="text" path="name"/>
                </p>
                
                <p>
                    <form:label class="labels" path="email">Email:</form:label></br>
                    <form:input  path="email"/>
                </p>

                <p>
                    <form:label class="labels" path="password">Password:</form:label></br>
                    <form:password path="password"/>
                </p>
                <p>
                    <form:label class="labels" path="passwordConfirmation">Password Confirmation:</form:label></br>
                    <form:password path="passwordConfirmation"/>
                </p>
                <input type="submit" value="Register!"/>
        </form:form>
    </fieldset>
    <fieldset class="login">
        <legend><h1>Login</h1></legend>
        <p class="classError" ><c:out value="${emailError}" /></p>
        <p class="classError" ><c:out value="${PasswordErr}" /></p>
        <p class="classError" ><c:out value="${error}" /></p>
        <p class="classError" ><c:out value="${format}" /></p>
        
        <form method="post" action="/login">
            <p>
                <label type="email" for="email">Email</label></br>
                <input type="text" id="email" name="loginEmail"/>
            </p>
            <p>
                <label for="password">Password</label></br>
                <input type="password" id="password" name="loginPassword"/>
            </p>
            <input type="submit" value="Login!"/>
        </form>    


    </fieldset>
    
</body>
</html>