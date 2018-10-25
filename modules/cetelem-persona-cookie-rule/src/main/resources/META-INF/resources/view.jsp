<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ArrayUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>

<%@ page import="com.liferay.portal.kernel.model.Role" %>
<%@ page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.model.RoleConstants" %>

<%@ page import="com.liferay.portal.kernel.model.UserGroup" %>
<%@ page import="com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.service.UserGroupLocalServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil" %>

<%@ page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %>

<%@ page import="java.lang.String" %>
<%@ page import="java.lang.Long" %>

<%@ page import="java.text.Format" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_REGULAR, StringPool.BLANK);
List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(themeDisplay.getCompanyId());

Map<String, Object> context = (Map<String, Object>) request.getAttribute("context");

Long roleId = GetterUtil.getLong(context.get("roleId"));
List<String> userGroupsIds = (List<String>) context.get("userGroupsIds");
String condition = GetterUtil.getString(context.get("condition"));

%>

<aui:select name="roleId" label="br.com.cetelem.user.segmentation.label.select.role.persona" value="<%= roleId %>">
<aui:validator name="required"></aui:validator>
	<%
	for (Role r : roles) {
	    if(!r.isSystem()) {
	%>
		<aui:option label="<%= HtmlUtil.escape(r.getDescriptiveName()) %>" value="<%= r.getRoleId() %>" />
	<%
	    }
	}
	%>
</aui:select>

<aui:select name="userGroupsIds" label="br.com.cetelem.user.segmentation.label.select.user.group" multiple="true">
	<%
	for (UserGroup userGroup : userGroups) {
	    String userGroupIdStr = String.valueOf(userGroup.getUserGroupId());
	%>
		<aui:option label="<%= HtmlUtil.escape(userGroup.getName()) %>" value="<%= userGroup.getUserGroupId() %>" selected="<%= userGroupsIds.contains(userGroupIdStr) %>" />
	<%
	}
	%>
</aui:select>

<aui:field-wrapper name="condition" required="true" label="br.com.cetelem.user.segmentation.label.select.user.groupCondition"> 
	<aui:input inlineLabel="right" name="condition" type="radio" value="AND" label="and" checked="<%= condition.equals("AND") %>" /> 
	<aui:input inlineLabel="right" name="condition" type="radio" value="OR" label="or" checked="<%= condition.equals("OR") %>"/>
</aui:field-wrapper>