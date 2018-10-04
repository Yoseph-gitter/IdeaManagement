<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<html>
	<head>
		<style> 
				*{
					margin : 0;
					padding: 0;
				}
			   .anchors{
			     text-decoration: underline;
			     display: inline;
			     font-weight: bald;
			    }
			    .logout{
			     margin: 1% 1% 1% 11%;
			    }
		        .all{ {
				    margin: 3%;
				    outline: 1px solid black;
				    padding: 2%;
				}
				#ideas {
			            font-size: 1.5rem;
				        margin: 0 40% 0 20%;
					    display: inline;
					    font-size: 24px;
					    font-weight: bold;
				      }
					  div.all{
					  	margin: 3%;
					    padding: 2%;
					    border: 1px dotted;
				      }
					 .idea{
					 	border: 2px solid gray;
					 	border-radius: 10px;
					 	text-decoration: underline ;
					 }
        </style>
	</head>
	<body>
		  <div class="all">
		  
		  <h3>Welcome, <c:out value="${user.name}" /></h3> 
		  
		  <h5 id="ideas">Ideas</h5>
		  <a  href="/sortUp"  class="anchors">Sort Ideas Up</a> | <a href="/sortDown"  class="anchors">Sort Ideas Down</a>
		  <a class="logout" href="/logout">Logout</a>
		  <table class="table">
		      <thead class="thead-dark">
		        <tr>
		           <th scope="col">Idea</th>
		          <th scope="col">Created By:</th>
		          <th scope="col">Likes</th>
		          <th scope="col">Action</th>
		        </tr>
		      </thead>
		      <tbody>
		      
			      <c:forEach var="ideaInfo" items="${ideas}">
				      <tr>
				      	  <c:choose>
						        <c:when test="${ ideaInfo.idea.creator.id== user.id }">
						       		<td><a href="/ideas/${ideaInfo.idea.id}"  class="anchors">${ideaInfo.idea.content}</a></td>
						   		</c:when> 
						   		
						         <c:otherwise>
						         	<td>${ideaInfo.idea.content}</td>
						   		 </c:otherwise> 
						 </c:choose> 
						   	   
				          
				         
				          <td><c:out value="${ideaInfo.idea.creator.name}"/></td>
				          <td><c:out value="${ideaInfo.idea.likedBy.size() }"/></td>
				          <td>
				              <c:choose>
						        <c:when test="${! ideaInfo.isLiked() }">
						       		<a href="/ideas/${ideaInfo.idea.id}/like"  class="anchors">Like</a>
						   		</c:when> 
						   		
						         <c:otherwise>
						         	<a href="/ideas/${ideaInfo.idea.id}/unlike"  class="anchors">Unlike</a>
						   		 </c:otherwise> 
						   	   </c:choose> 
				          </td>
				      </tr>
			     </c:forEach>
			    
		      </tbody>
		    </table>
		    
		    <a href="/ideas/new"  class="idea"><h3>Create an idea</h3></a>
		      
		
		   <!-- 
		        BootStrap codes will goes here
		     -->
		     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		
		     <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		 
		    
		  
		
		</div>
					
			
	
	</body>
</html>

