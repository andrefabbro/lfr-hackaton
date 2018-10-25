package br.com.cetelem.content.targeting.keys;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * Stores the properties values from portal-ext.properties
 * 
 * @author André Fabbro
 */
public abstract class PropertiesValues {

    public static final String ELIGIBILITY_SERVICE_PROTOCOL =
        GetterUtil.getString(
            PropsUtil.get(PropertiesKeys.ELIGIBILITY_SERVICE_PROTOCOL));

    public static final String ELIGIBILITY_SERVICE_HOST = GetterUtil.getString(
        PropsUtil.get(PropertiesKeys.ELIGIBILITY_SERVICE_HOST));

    public static final String ELIGIBILITY_SERVICE_PORT = GetterUtil.getString(
        PropsUtil.get(PropertiesKeys.ELIGIBILITY_SERVICE_PORT));

    public static final String COOKIE_CONTRACT_ID_NAME = GetterUtil.getString(
        PropsUtil.get(PropertiesKeys.COOKIE_CONTRACT_ID_NAME));

}
