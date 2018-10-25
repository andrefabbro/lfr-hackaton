package br.com.cetelem.customers.segmentation.portlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.ParallelDestination;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;

import br.com.cetelem.customers.segmentation.constants.CustomersSegmentationImportAppPortletKeys;
import br.com.cetelem.customers.segmentation.message.listener.UserSegmentationListener;

/**
 * @author André Fabbro
 */
@Component(immediate = true, property = {
    "com.liferay.portlet.add-default-resource=true",
    "com.liferay.portlet.display-category=category.hidden",
    "com.liferay.portlet.layout-cacheable=true",
    "com.liferay.portlet.private-request-attributes=false",
    "com.liferay.portlet.private-session-attributes=false",
    "com.liferay.portlet.render-weight=50",
    "com.liferay.portlet.use-default-template=true",
    "javax.portlet.display-name=Customer Segmentation File Import",
    "javax.portlet.expiration-cache=0",
    "javax.portlet.init-param.template-path=/",
    "javax.portlet.init-param.view-template=/view.jsp",
    "javax.portlet.name=" +
        CustomersSegmentationImportAppPortletKeys.CustomersSegmentationImportApp,
    "javax.portlet.resource-bundle=content.Language",
    "javax.portlet.security-role-ref=power-user,user",
    "javax.portlet.supports.mime-type=text/html"
}, service = Portlet.class)
public class CustomersSegmentationImportAppPortlet extends MVCPortlet {

    private static final Log _log =
        LogFactoryUtil.getLog(CustomersSegmentationImportAppPortlet.class);

    private static final String PREFIX_FILE_ENTRY_DEFAULT_NAME =
        "SEGMENTACAO-USUARIOS-";

    private static final String DESTINATION_NAME =
        "cetelem/user_segmentation_import";

    @Reference
    private RoleLocalService roleLocalService;

    @Reference
    private UserGroupLocalService userGroupService;

    @Reference
    private GroupLocalService groupLocalService;

    @Reference
    private DLAppLocalService dllAppLocalService;

    @Reference
    private Portal _portal;

    public void processCustomerSegmentationFile(
        ActionRequest request, ActionResponse response)
        throws PortalException, SystemException {

        long roleId = ParamUtil.getLong(request, "role");
        long[] userGroupIds = ParamUtil.getLongValues(request, "userGroups");

        UploadPortletRequest uploadPortletRequest =
            _portal.getUploadPortletRequest(request);

        String title =
            PREFIX_FILE_ENTRY_DEFAULT_NAME + System.currentTimeMillis();
        String contentType = uploadPortletRequest.getContentType("file");
        long size = uploadPortletRequest.getSize("file");
        String sourceFileName = uploadPortletRequest.getFileName("file");

        StringBuilder descriptionSb = new StringBuilder().append(
            "Role: " + roleLocalService.getRole(roleId).getName()).append(
                " - UserGroups: ");
        
        Arrays.stream(userGroupIds).forEach(ugid -> {
            try {
                descriptionSb.append(
                    userGroupService.getUserGroup(ugid).getName());
                descriptionSb.append(", ");
            }
            catch (PortalException e) {
                e.printStackTrace();
            }
        });

        InputStream inputStream = null;
        try {
            inputStream = uploadPortletRequest.getFileAsStream("file");
        }
        catch (IOException e) {
            throw new SystemException(e);
        }

        ServiceContext serviceContext = ServiceContextFactory.getInstance(
            DLFileEntry.class.getName(), uploadPortletRequest);

        Group globalGroup =
            groupLocalService.getCompanyGroup(PortalUtil.getDefaultCompanyId());

        FileEntry fileEntry = null;
        try {
            fileEntry = dllAppLocalService.addFileEntry(
                PortalUtil.getUser(request).getUserId(),
                globalGroup.getGroupId(), 0, sourceFileName, contentType, title,
                descriptionSb.toString(), StringPool.BLANK, inputStream, size,
                serviceContext);

            this.sendJob(fileEntry.getFileEntryId(), roleId, userGroupIds);
        }
        catch (Exception e) {
            throw new SystemException(e);
        }
        finally {
            StreamUtil.cleanUp(inputStream);
        }
    }

    private void sendJob(long fileEntryId, long roleId, long[] userGroupIds) {

        if (!MessageBusUtil.hasMessageListener(DESTINATION_NAME)) {

            ParallelDestination destination = new ParallelDestination();
            destination.setName(DESTINATION_NAME);
            destination.setWorkersCoreSize(1);
            destination.setWorkersMaxSize(1);
            destination.setMaximumQueueSize(10);

            destination.afterPropertiesSet();

            MessageBusUtil.addDestination(destination);
            MessageBusUtil.registerMessageListener(
                DESTINATION_NAME, new UserSegmentationListener());

            _log.info("Destino incluido.");
        }

        Message message = new Message();
        message.put("fileEntryId", fileEntryId);
        message.put("roleId", roleId);
        message.put("userGroupIds", userGroupIds);
        message.setDestinationName(DESTINATION_NAME);

        MessageBusUtil.sendMessage(DESTINATION_NAME, message);
    }
}
