package br.com.cetelem.customers.segmentation.message.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author André Fabbro
 */
public class UserSegmentationListener implements MessageListener {

    private static final Log _log =
        LogFactoryUtil.getLog(UserSegmentationListener.class);

    @Override
    public void receive(Message message)
        throws MessageListenerException {

        _log.info("======================================================");
        _log.info("Inicio proc da mensagem para " + this.getClass().getName());
        _log.info("======================================================");

        try {
            _doReceive(message);
        }
        catch (Exception e) {
            _log.error(
                "Houve um problema para processar a mensagem para o destino: " +
                    message.getDestinationName());
            throw new MessageListenerException(e);
        }
    }

    private void _doReceive(Message message)
        throws PortalException, IOException {

        _log.info("--------[ INICIO ]-----------");
        long processStart = System.currentTimeMillis();

        long companyId = PortalUtil.getDefaultCompanyId();

        Role role = RoleLocalServiceUtil.getRole(message.getLong("roleId"));
        _log.info("Role: " + role.getRoleId() + " - " + role.getName());

        DLFileEntry fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
            message.getLong("fileEntryId"));
        _log.info(
            "FileEntry: " + fileEntry.getFileEntryId() + " - " +
                fileEntry.getName());

        long[] userGroupIds = (long[]) message.get("userGroupIds");
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        for (long ugid : userGroupIds)
            userGroups.add(UserGroupLocalServiceUtil.getUserGroup(ugid));
        userGroups.forEach(ug -> {
            _log.info(
                "UserGroup: " + ug.getUserGroupId() + " - " + ug.getName());
        });

        List<String> cpfs = new ArrayList<String>();

        InputStream in = fileEntry.getContentStream();
        try {
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);
            String line;
            while ((line = buf.readLine()) != null)
                cpfs.add(line.trim());
        }
        finally {
            in.close();
        }

        cpfs.forEach(cpf -> {

            User user =
                UserLocalServiceUtil.fetchUserByScreenName(companyId, cpf);
            if (user != null) {
                long start = System.currentTimeMillis();

                setUpUser(user, role, userGroups);

                _log.info(
                    user.getScreenName() + " setup finished in " +
                        ((System.currentTimeMillis() - start) / 1000) +
                        " seconds.");
            }
        });

        _log.info(
            " Process finished in " +
                ((System.currentTimeMillis() - processStart) / 1000) +
                " seconds.");
        _log.info("--------[  FIM   ]-----------");
    }

    private void setUpUser(User user, Role role, List<UserGroup> userGroups) {

        long[] userGroupIds = user.getUserGroupIds();

        // Removes the user from all groups
        Arrays.stream(userGroupIds).forEach(groupId -> {
            UserGroupLocalServiceUtil.clearUserUserGroups(user.getUserId());
        });

        // Adds user to the selected groups
        userGroups.stream().forEach(userGroup -> {
            UserGroupLocalServiceUtil.addUserUserGroup(
                user.getUserId(), userGroup.getUserGroupId());
        });

        // removes user from all non system roles
        List<Role> userRoles =
            user.getRoles().stream().filter(r -> !r.isSystem()).collect(
                Collectors.toList());

        if (!userRoles.isEmpty())
            RoleLocalServiceUtil.deleteUserRoles(user.getUserId(), userRoles);

        // Adds user to selected regular role
        RoleLocalServiceUtil.addUserRole(user.getUserId(), role);
    }

}
