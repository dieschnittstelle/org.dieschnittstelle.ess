<%@page
	import="org.dieschnittstelle.ess.entities.crm.AbstractTouchpoint,org.dieschnittstelle.ess.entities.crm.StationaryTouchpoint,java.util.List"%>
<%@ page import="org.apache.logging.log4j.Logger" %>
<%@ page import="org.dieschnittstelle.ess.ser.TouchpointServiceServlet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- declare a logger -->
<% Logger logger = org.apache.logging.log4j.LogManager
		.getLogger("org.dieschnittstelle.ess.ser#gui.jsp"); %>
<!-- check whether a redirect shall be initiated, in which case we will not generate markup -->
<%
	String basecontext = request.getContextPath() + "/";

	if (request.getAttribute("redirectToRoot") != null)
{
	logger.info("will initiate a redirect, skip markup generation");
	response.sendRedirect(basecontext);
}
else {
	logger.info("will generate markup");
	boolean demoGetIdempotence = request.getAttribute("demoGetIdempotence") != null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SER Touchpoints</title>
</head>
<body>
	<!--  print out the class hierarchy -->
<%--	<%=this%>--%>
	<br />
<%--	<%--%>
<%--		Class klass = this.getClass();--%>
<%--		String tabs = "";--%>
<%--		do {--%>
<%--	%><%=tabs + klass%><br />--%>
<%--	<%--%>
<%--		klass = klass.getSuperclass();--%>
<%--			tabs += "\t";--%>
<%--		} while (klass != null);--%>
<%--	%>--%>

	<!--  access session attributes and their constituents -->
	<h3>SER Touchpoints</h3>

	<!-- create a table -->
	<table border="1">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Strasse</th>
			<th>Postleitzahl</th>
			<th>Ort</th>
			<td></td>
		</tr>
		<!--  iterate over the touchpoints -->
<%--		<%: dynamischer Code--%>
		<%
			List<AbstractTouchpoint> touchpoints = (List<AbstractTouchpoint>) request
					.getAttribute("touchpoints");
			for (AbstractTouchpoint touchpoint : touchpoints) {
				if (touchpoint instanceof StationaryTouchpoint) {
		%>
		<tr>
			<td><%=touchpoint.getId()%></td>
			<td><%=touchpoint.getName()%></td>
			<td><%=((StationaryTouchpoint) touchpoint).getAddress()
							.getStreet()%>
				<%=((StationaryTouchpoint) touchpoint).getAddress()
							.getHouseNr()%></td>
			<td><%=((StationaryTouchpoint) touchpoint).getAddress()
							.getZipCode()%></td>
			<td><%=((StationaryTouchpoint) touchpoint).getAddress()
							.getCity()%></td>
			<td>
				<!--  we add a delete button -->
				<form method="POST" action="<%=basecontext%>gui/touchpoints/delete/<%=touchpoint.getId()%>">
					<input type="submit" value="delete">
				</form>
			</td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<!--  create a new touchpoint -->
	<h3>New Touchpoint</h3>
	<form method="<%=(demoGetIdempotence ? "GET" : "POST")%>" action="<%=basecontext%>gui/touchpoints/create/">
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Street and HouseNr:</td>
				<td><input type="text" name="street"><input
					type="text" size="4" name="houseNr"></td>
			</tr>
			<tr>
			<td>Zip Code and City:</td>
			<td><input type="number" name="zipCode" size="5" ><input
				type="text" name="city"></td>
			</tr>
		</table>
		<input type="submit" value="create" />
	</form>
</body>
</html>
<!-- closing else for markup generation -->
<%}%>