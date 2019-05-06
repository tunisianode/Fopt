<html>
<head><title>MVC</title></head>
<body>
<h1>MVC mit Servlets und JSPs</h1>

<jsp:useBean id="requestCounter" class="pada.da.servlets.basic.Counter"
 scope="request"/>
<jsp:useBean id="sessionCounter" class="pada.da.servlets.basic.Counter"
 scope="session"/>
<jsp:useBean id="applicationCounter" class="pada.da.servlets.basic.Counter"
 scope="application"/>

<ul>
<li>Z&auml;hler f&uuml;r scope="request": <%= requestCounter.get() %>
<li>Z&auml;hler f&uuml;r scope="session": <%= sessionCounter.get() %>
<li>Z&auml;hler f&uuml;r scope="application": <%= applicationCounter.get() %>
</ul>

<form method="get" action="mvc_zuruecksetzen">
<input value="Alle Z&auml;hler zur&uuml;cksetzen!" type="submit"/>
</form>

<form method="get" action="mvc_erhoehen">
<input value="Alle Z&auml;hler erh&ouml;hen!" type="submit"/>
</form>

<form method="get" action="mvc_setzen">
<input name="newValue" type="text"/>
<input value="Alle Z&auml;hler auf diesen Wert setzen!" type="submit"/>
</form>

</body>
</html>
