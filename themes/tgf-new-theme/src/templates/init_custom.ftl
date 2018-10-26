<#assign

	show_footer = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-footer"))
	show_header = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-header"))
	show_header_search = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-header-search"))
	show_header_navigation = getterUtil.getBoolean(themeDisplay.getThemeSetting("show-header-navigation"))
	wrap_content = getterUtil.getBoolean(themeDisplay.getThemeSetting("wrap-content"))

	navbar_is_solid = getterUtil.getBoolean(themeDisplay.getThemeSetting("navbar-is-solid"))

	home_main_action = getterUtil.getString(themeDisplay.getThemeSetting("home-main-action"))

	resources_url = getterUtil.getString(themeDisplay.getThemeSetting("resources-url"))


/>

<#if wrap_content>
	<#assign portal_content_css_class = "container" />
<#else>
	<#assign portal_content_css_class = "" />
</#if>


<#assign permission_checker = themeDisplay.getPermissionChecker() />

<#assign is_group_admin = permission_checker.isGroupAdmin(group_id) />
<#assign is_omniadmin = permission_checker.isOmniadmin() />

<#assign show_dockbar = is_group_admin || is_omniadmin />

<#if show_dockbar>
    <#assign wrapper_class_name = "" />
<#else>
    <#assign wrapper_class_name = "hide-dockbar" />
</#if>