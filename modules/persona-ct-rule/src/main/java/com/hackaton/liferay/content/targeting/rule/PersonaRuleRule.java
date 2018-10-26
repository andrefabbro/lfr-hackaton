package com.hackaton.liferay.content.targeting.rule;

import com.hackaton.liferay.content.targeting.rule.persona.Persona;
import com.hackaton.liferay.content.targeting.rule.persona.PersonaResolver;
import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.api.model.BaseJSPRule;
import com.liferay.content.targeting.api.model.Rule;
import com.liferay.content.targeting.model.RuleInstance;
import com.liferay.content.targeting.rule.categories.SampleRuleCategory;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.model.User;

/**
 * @author andrefabbro
 */
@Component(immediate = true, service = Rule.class)
public class PersonaRuleRule extends BaseJSPRule {

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
	public boolean evaluate(
			HttpServletRequest httpServletRequest, RuleInstance ruleInstance,
			AnonymousUser anonymousUser)
		throws Exception {

		User user = anonymousUser.getUser();

		String userName = user.getScreenName();

		URL url = new URL("https://portal-symposium.lfr.io/o/extrato-rest-service/cliente/persona/" + userName);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("GET");
		connection.setReadTimeout(15*1000);
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder stringBuilder = new StringBuilder();

		String line = null;
		while ((line = reader.readLine()) != null)
		{
			stringBuilder.append(line);
		}

		String typeSettings = ruleInstance.getTypeSettings();

		Persona persona = PersonaResolver.getInstance().getPersona(stringBuilder.toString().toLowerCase());

		return _getPersona(typeSettings).equals(persona.getLabel());
	}

	@Override
	public String getIcon() {
		return "icon-puzzle-piece";
	}

	@Override
	public String getRuleCategoryKey() {

		// Available category classes: BehaviourRuleCategory,
		// SessionAttributesRuleCategory, SocialRuleCategory and
		// UserAttributesRoleCategory

		return SampleRuleCategory.KEY;
	}

	@Override
	public String getSummary(RuleInstance ruleInstance, Locale locale) {
		String typeSettings = ruleInstance.getTypeSettings();

		String persona = _getPersona(typeSettings);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		if (persona.equals(Persona.TRAVEL)) {
			return LanguageUtil.get(
				resourceBundle, "the-user-always-matches-this-rule");
		}
		else {
			return LanguageUtil.get(
				resourceBundle, "the-user-never-matches-this-rule");
		}
	}

	@Override
	public String processRule(
		PortletRequest portletRequest, PortletResponse portletResponse,
		String id, Map<String, String> values) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String persona = GetterUtil.getString(values.get("persona"));

		jsonObject.put("persona", persona);

		return jsonObject.toString();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.hackaton.liferay)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void populateContext(
		RuleInstance ruleInstance, Map<String, Object> context,
		Map<String, String> values) {

		String persona = "";

		if (!values.isEmpty()) {

			// Value from the request in case of an error

			persona = GetterUtil.getString(values.get("persona"));
		}
		else if (ruleInstance != null) {

			// Value from the stored configuration

			String typeSettings = ruleInstance.getTypeSettings();

			persona = _getPersona(typeSettings);
		}

		context.put("persona", persona);
	}

	private String _getPersona(String typeSettings) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				typeSettings);

			return jsonObject.getString("persona");
		}
		catch (JSONException jsone) {
		}

		return "";
	}

}