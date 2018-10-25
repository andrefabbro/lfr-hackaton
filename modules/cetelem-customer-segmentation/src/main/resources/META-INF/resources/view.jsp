<%@ include file="/init.jsp" %>

<%
List<Role> roles = RoleLocalServiceUtil.getRoles(RoleConstants.TYPE_REGULAR, StringPool.BLANK);
List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(themeDisplay.getCompanyId());

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:actionURL name="processCustomerSegmentationFile" varImpl="processCustomerSegmentationFileURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
    <portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:actionURL>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="User Segmentation Import File" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>
<div class="panel-body">
	
	<aui:form action="<%= processCustomerSegmentationFileURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
        
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
	
				<aui:select name="role" label="cetelem.user.segmentation.label.select.role">
					<aui:validator name="required"></aui:validator>
					<%
					for (Role role : roles) {
					    if(!role.isSystem()) {
					%>
						<aui:option label="<%= HtmlUtil.escape(role.getDescriptiveName()) %>" value="<%= role.getRoleId() %>" />
					<%
					    }
					}
					%>
                </aui:select>
                   
                <aui:select name="userGroups" label="cetelem.user.segmentation.label.select.group" multiple="true">
					<aui:validator name="required"></aui:validator>
					<%
					for (UserGroup userGroup : userGroups) {
					%>
						<aui:option label="<%= HtmlUtil.escape(userGroup.getName()) %>" value="<%= userGroup.getUserGroupId() %>" />
					<%
					}
					%>
                </aui:select>
                   
				<aui:input name="file" type="file">
					<aui:validator name="acceptFiles">'csv'</aui:validator>
				</aui:input>
				
			</aui:fieldset>
		</aui:fieldset-group>
		
		<aui:button-row>
			<aui:button type="submit" cssClass="btn-lg" id="submit" label="save" primary="<%= true %>" />
            <aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
		
	</aui:form>
	
</div>