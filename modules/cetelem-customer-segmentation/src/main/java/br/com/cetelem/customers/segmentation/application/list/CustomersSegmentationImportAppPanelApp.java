package br.com.cetelem.customers.segmentation.application.list;

import br.com.cetelem.customers.segmentation.constants.CustomersSegmentationImportAppPanelCategoryKeys;
import br.com.cetelem.customers.segmentation.constants.CustomersSegmentationImportAppPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author andrefabbro
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + CustomersSegmentationImportAppPanelCategoryKeys.CONTROL_PANEL_CATEGORY
	},
	service = PanelApp.class
)
public class CustomersSegmentationImportAppPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return CustomersSegmentationImportAppPortletKeys.CustomersSegmentationImportApp;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + CustomersSegmentationImportAppPortletKeys.CustomersSegmentationImportApp + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}