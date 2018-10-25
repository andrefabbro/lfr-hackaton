package br.com.cetelem.content.targeting.rule;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseJSPRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.SessionAttributesRuleCategory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import br.com.cetelem.content.targeting.keys.PropertiesValues;
import br.com.cetelem.content.targeting.model.Eligibility;
import br.com.cetelem.content.targeting.model.EligibilityResolver;
import br.com.cetelem.content.targeting.model.MenuOptionsRepresentation;
import br.com.cetelem.content.targeting.service.CreditCardServiceFactory;

@Component(immediate = true, service = Rule.class)
public class ContractEligibilityRule extends BaseJSPRule {

	private Log log = LogFactoryUtil.getLog(ContractEligibilityRule.class);
	
	private static final String JSESSION_ID_COOKIE = "JSESSIONID";

	@Activate
	@Override
	public void activate() {
		super.activate();
	}

	@Deactivate
	@Override
	public void deActivate() {
		super.deActivate();
	}

	@Override
	public boolean evaluate(HttpServletRequest httpServletRequest, RuleInstance ruleInstance,
			AnonymousUser anonymousUser) throws Exception {
	    
	    Cookie cookie = (Cookie) Arrays.stream(httpServletRequest.getCookies())
            .filter(c -> PropertiesValues.COOKIE_CONTRACT_ID_NAME.equals(c.getName()))
            .findAny().orElse(null);
	    
	    Cookie cookieJSessionId = (Cookie) Arrays.stream(httpServletRequest.getCookies())
	        .filter(c -> JSESSION_ID_COOKIE.equals(c.getName()))
	        .findAny().orElse(null);
	    
		User user = anonymousUser.getUser();
		if (user != null && cookie != null && cookieJSessionId != null) {
		    
			try {
			    
			    String jsessionId = cookieJSessionId.getName() + "=" + cookieJSessionId.getValue();
			    
				MenuOptionsRepresentation options = creditCardServiceFactory
				                .newCreditCardService(getBaseMenuOptionsBaseURL())
				                .findMenuOptions(jsessionId, cookie.getValue())
				                .execute()
				                .body();
				
				Eligibility eligibility = EligibilityResolver.getInstance()
				                .getEligibility(ruleInstance.getTypeSettings());

				return eligibility.getStatus(options);
				
			} catch (Exception e) {
			    // We don't throw an exception just because it couldn't be evaluated
				log.error(e);
			}
		}

		return false;
	}
	
	protected String getBaseMenuOptionsBaseURL() {

        return new StringBuilder().append(
            PropertiesValues.ELIGIBILITY_SERVICE_PROTOCOL).append(
                StringPool.COLON).append(StringPool.DOUBLE_SLASH).append(
                    PropertiesValues.ELIGIBILITY_SERVICE_HOST).append(
                        StringPool.COLON).append(
                            PropertiesValues.ELIGIBILITY_SERVICE_PORT).toString();
    }

	@Override
	public String getIcon() {
		return "icon-puzzle-piece";
	}

	@Override
	public String getRuleCategoryKey() {
		return SessionAttributesRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		return ruleInstance.getTypeSettings();
	}

	@Override
	public String processRule(PortletRequest portletRequest, PortletResponse portletResponse, String id,
			Map<String, String> values) {

		return values.get("eligibility");
	}

	@Override
	@Reference(target = "(osgi.web.symbolicname=br.com.cetelem.content.targeting.rule.contract.eligibility)", unbind = "-")
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void populateContext(RuleInstance ruleInstance, Map<String, Object> context, Map<String, String> values) {
		String eligibility = "";
		if (!values.isEmpty()) {
			eligibility = GetterUtil.getString(values.get("eligibility"));
		} else if (ruleInstance != null) {
			eligibility = ruleInstance.getTypeSettings();
		}

		context.put("eligibility", eligibility);
	}

	@Reference
	public void setCreditCardServiceFactory(CreditCardServiceFactory creditCardServiceFactory) {
		this.creditCardServiceFactory = creditCardServiceFactory;
	}

	private CreditCardServiceFactory creditCardServiceFactory;

}