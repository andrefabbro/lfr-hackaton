<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ page import="java.util.Map" %>

<%@ page import="br.com.cetelem.content.targeting.model.Eligibility" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Map<String, Object> context = (Map<String, Object>) request.getAttribute("context");

String eligibility = (String) context.get("eligibility");
%>

<aui:fieldset>
	<aui:select name="eligibility" value="<%= eligibility %>" label="br.com.cetelem.label.eligibility">
		<aui:option label="br.com.cetelem.label.cashAdvance" value="<%= Eligibility.CASH_ADVANCE %>" />
		<aui:option label="br.com.cetelem.label.installmentWithdrawal" value="<%= Eligibility.INSTALLMENT_WITHDRAWAL %>" />
		<aui:option label="br.com.cetelem.label.personalLoan" value="<%= Eligibility.PERSONAL_LOAN %>" />
		<aui:option label="br.com.cetelem.label.statementInstallment" value="<%= Eligibility.STATEMENT_INSTALLMENT %>" />
	</aui:select>
</aui:fieldset>