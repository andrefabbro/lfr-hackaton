package br.com.cetelem.persona.content.targeting.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import br.com.cetelem.persona.content.targeting.keys.PropertiesValues;

/**
 * @author André Fabbro
 */
@Component(immediate = true, service = Rule.class)
public class PersonaCookieRule extends BaseJSPRule {

    private static final String CONDITION_AND = "AND";

    private static final String CONDITION_OR = "OR";

    @Reference
    private UserGroupLocalService userGroupLocalService;

    @Reference
    private RoleLocalService roleLocalService;

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
        HttpServletRequest request, RuleInstance ruleInstance,
        AnonymousUser anonymousUser)
        throws Exception {

        JSONObject jsonObject =
            JSONFactoryUtil.createJSONObject(ruleInstance.getTypeSettings());

        Long roleId = jsonObject.getLong("roleId");
        String condition = jsonObject.getString("condition");
        List<String> userGroupsIds = _getUserGroupsIds(jsonObject);

        boolean matchAllUserGroups =
            condition.equals(CONDITION_AND) ? true : false;

        User user = anonymousUser.getUser();

        // If user is logged-in, compare direct from user attributes
        if (user != null) {
            return _evaluateLoggedIn(
                user, roleId, userGroupsIds, matchAllUserGroups);
        }
        else {
            return _evaluateNotLoggedIn(
                request, roleId, userGroupsIds, matchAllUserGroups);
        }
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

        StringBuffer sb = new StringBuffer();

        try {
            JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
                ruleInstance.getTypeSettings());

            Long roleId = jsonObject.getLong("roleId");
            String condition = jsonObject.getString("condition");
            List<String> userGroupsIds = _getUserGroupsIds(jsonObject);

            sb.append(
                "Persona: " +
                    roleLocalService.fetchRole(roleId).getDescriptiveName());
            sb.append("<br/>");
            sb.append("User Groups: ");
            userGroupsIds.forEach(s -> {
                if (!s.trim().equals(StringPool.BLANK)) {
                    sb.append(
                        userGroupLocalService.fetchUserGroup(
                            Long.valueOf(s)).getName());
                    sb.append(", ");
                }
            });
            sb.append("<br/>");
            sb.append("Condition: ").append(condition);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public String processRule(
        PortletRequest portletRequest, PortletResponse portletResponse,
        String id, Map<String, String> values) {

        JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

        Long roleId = GetterUtil.getLong(values.get("roleId"));
        String condition = GetterUtil.getString(values.get("condition"));

        List<String> userGroupsIdsLst = _getUserGroupIdsFromString(
            GetterUtil.getString(values.get("userGroupsIds")));

        jsonObject.put("roleId", roleId);
        jsonObject.put("userGroupsIds", userGroupsIdsLst);
        jsonObject.put("condition", condition);

        return jsonObject.toString();
    }

    @Override
    @Reference(target = "(osgi.web.symbolicname=br.com.cetelem.content.targeting.persona)", unbind = "-")
    public void setServletContext(ServletContext servletContext) {

        super.setServletContext(servletContext);
    }

    @Override
    protected void populateContext(
        RuleInstance ruleInstance, Map<String, Object> context,
        Map<String, String> values) {

        Long roleId = null;
        String condition = CONDITION_OR;
        List<String> userGroupsIds = new ArrayList<String>();

        if (!values.isEmpty()) {

            // Value from the request in case of an error

            roleId = GetterUtil.getLong(values.get("roleId"));
            condition = GetterUtil.getString(values.get("condition"));

            userGroupsIds = _getUserGroupIdsFromString(
                GetterUtil.getString(values.get("userGroupsIds")));

        }
        else if (ruleInstance != null) {

            // Value from the stored configuration
            try {
                JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
                    ruleInstance.getTypeSettings());

                roleId = jsonObject.getLong("roleId");
                condition = jsonObject.getString("condition");
                userGroupsIds = _getUserGroupsIds(jsonObject);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        context.put("roleId", roleId);
        context.put("userGroupsIds", userGroupsIds);
        context.put("condition", condition);
    }

    private List<String> _getUserGroupsIds(JSONObject jsonObject) {

        JSONArray userGroupsIdsArr = jsonObject.getJSONArray("userGroupsIds");

        List<String> gidvalues = new ArrayList<String>();
        for (int i = 0; i < userGroupsIdsArr.length(); i++)
            gidvalues.add(userGroupsIdsArr.getString(i));

        return gidvalues;
    }

    private boolean _evaluateLoggedIn(
        User user, Long roleId, List<String> userGroupIdsRule,
        boolean matchAllUserGroups) {

        long foundRole = Arrays.stream(user.getRoleIds()).filter(
            id -> id == roleId.longValue()).findAny().orElse(0);

        if (!(foundRole > 0))
            return false;

        return _evaluateUserGroups(
            userGroupIdsRule, user.getUserGroupIds(), matchAllUserGroups);
    }

    private boolean _evaluateNotLoggedIn(
        HttpServletRequest request, Long roleId, List<String> userGroupsIds,
        boolean matchAllUserGroups) {

        Cookie personaCookie =
            (Cookie) Arrays.stream(request.getCookies()).filter(
                c -> PropertiesValues.COOKIE_PERSONA_ID_NAME.equals(
                    c.getName())).findAny().orElse(null);

        if (personaCookie == null ||
            !(personaCookie.getValue().trim().equals(roleId.toString())))
            return false;

        Cookie userGroupsGookie =
            (Cookie) Arrays.stream(request.getCookies()).filter(
                c -> PropertiesValues.COOKIE_USER_GROUPS_IDS_NAME.equals(
                    c.getName())).findAny().orElse(null);

        if (userGroupsGookie == null)
            return true;

        String[] groupIdsStrArr = userGroupsGookie.getValue().split(",");

        long[] userGroupsIdsCookies = new long[groupIdsStrArr.length];
        for (int i = 0; i < userGroupsIdsCookies.length; i++) {
            userGroupsIdsCookies[i] = Long.valueOf(groupIdsStrArr[i]);
        }

        return _evaluateUserGroups(
            userGroupsIds, userGroupsIdsCookies, matchAllUserGroups);
    }

    private boolean _evaluateUserGroups(
        List<String> userGroupIdsRule, long[] userGroupIdsCookie,
        boolean matchAll) {

        boolean match = !matchAll;

        for (String id_rule : userGroupIdsRule) {
            for (long id_cookie : userGroupIdsCookie) {
                if (matchAll && Long.valueOf(id_rule) != id_cookie)
                    match = false;
                else if (Long.valueOf(id_rule) == id_cookie)
                    match = true;
            }
        }

        return match;
    }

    private List<String> _getUserGroupIdsFromString(String value) {

        value = value.replace("[", "");
        value = value.replace("]", "");
        value = value.replace("\"", "");

        String[] userGroupsIds = value.split(",");
        return Arrays.asList(userGroupsIds);
    }

}
