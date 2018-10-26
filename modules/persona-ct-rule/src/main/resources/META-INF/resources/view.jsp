<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.hackaton.liferay.content.targeting.rule.persona.Persona" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Map<String, Object> context = (Map<String, Object>)request.getAttribute("context");

String persona = (String) context.get("persona");
%>

<aui:select label="cetelem-personas" name="persona" value="<%= persona %>">
	<aui:option label="cetelem-persona-travel" value="<%= Persona.TRAVEL %>" />
	<aui:option label="cetelem-persona-gourmet" value="<%= Persona.GOURMET %>" />
</aui:select>