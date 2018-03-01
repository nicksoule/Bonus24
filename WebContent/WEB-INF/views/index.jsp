<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Spring MVC</title>
</head>
<body>
	<br>
	<div style="text-align:center">
	
		<h3>
			Deck ID: ${cardDeck}
			
		</h3>
		<a href="welcome?id=${cardDeck}">Draw Five Cards</a>
	</div>
</body>
</html>