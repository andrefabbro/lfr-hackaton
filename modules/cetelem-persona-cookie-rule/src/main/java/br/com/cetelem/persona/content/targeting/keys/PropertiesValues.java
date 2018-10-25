package br.com.cetelem.persona.content.targeting.keys;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * Stores the properties values from portal-ext.properties
 * 
 * @author André Fabbro
 */
public abstract class PropertiesValues {

    public static final String COOKIE_PERSONA_ID_NAME = GetterUtil.getString(
        PropsUtil.get(PropertiesKeys.COOKIE_PERSONA_ID_NAME));

    public static final String COOKIE_USER_GROUPS_IDS_NAME =
        GetterUtil.getString(
            PropsUtil.get(PropertiesKeys.COOKIE_USER_GROUPS_IDS_NAME));

}
