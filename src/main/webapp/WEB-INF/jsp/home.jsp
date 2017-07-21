<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.onelogin.saml2.Auth"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.lang3.StringUtils" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Home Page</title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/css/jumbotron-narrow.css">
<link rel="stylesheet" type="text/css" href="/css/home.css">
<link rel="stylesheet" type="text/css" href="/css/jquery.growl.css" />
<script src="/js/jquery.js"></script>
<script src="/js/jquery.growl.js" type="text/javascript"></script>
</head>

<body>

	<div class="container">
		<div class="header clearfix">
			<nav>
				<ul class="nav nav-pills pull-right">
					<li class="active" id="home"><a href="#">Home</a></li>
					<li id="logout"><a href="#">Logout</a></li>
				</ul>
			</nav>
			<h3 class="text-muted">App.com</h3>
		</div>
		<div class="jumbotron">
			<h3>Hello ${nameId}!</h3>
		</div>
		<%
			Boolean found = false;
			@SuppressWarnings("unchecked")
			Enumeration<String> elems = (Enumeration<String>) session.getAttributeNames();

			while (elems.hasMoreElements() && !found) {
				String value = (String) elems.nextElement();
				if (value == "attributes" || value == "nameId") {
					found = true;
				}
			}

			if (found) {
				String nameId = (String) session.getAttribute("nameId");
				@SuppressWarnings("unchecked")
				Map<String, List<String>> attributes = (Map<String, List<String>>) session.getAttribute("attributes");
				if (!nameId.isEmpty()) {
					out.println("<div><b> NameId:</b> " + nameId + "</div>");
				}

				if (attributes.isEmpty()) {
		%>
		<div class="alert alert-danger" role="alert">You don't have any
			attributes</div>
		<%
			} else {
		%>
		<div>
			<b>Attributes:</b>
		</div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Name</th>
					<th>Values</th>
				</tr>
			</thead>
			<tbody>
				<%
					Collection<String> keys = attributes.keySet();
							for (String name : keys) {
								out.println("<tr><td>" + name + "</td><td>");
								List<String> values = attributes.get(name);
								for (String value : values) {
									out.println("<li>" + value + "</li>");
								}

								out.println("</td></tr>");
							}
				%>
			</tbody>
		</table>
		<%
			}
				//out.println("<a href=\"dologout.jsp\" class=\"btn btn-primary\">Logout</a>");
			} else {
				out.println("<div class=\"alert alert-danger\" role=\"alert\">Not authenticated</div>");
				//out.println("<a href=\"dologin.jsp\" class=\"btn btn-primary\">Login</a>");
			}
		%>
		<footer class="footer">
			<p>&copy; 2016 Company Inc</p>
		</footer>

	</div>

	<script type="text/javascript">
		$("#logout")
				.click(
						function(e) {
							e.preventDefault();
							$("#home").removeClass("active");
							$("#password-login").removeClass("active");
							$("#logout").addClass("active");
							// assumes we are not part of SSO so just logout of local session
							window.location = "${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}/logout";
						});
	</script>

</body>
</html>