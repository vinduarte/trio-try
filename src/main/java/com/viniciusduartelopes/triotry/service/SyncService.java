package com.viniciusduartelopes.triotry.service;

import com.viniciusduartelopes.triotry.model.BatchSubscribeResponseModel;
import com.viniciusduartelopes.triotry.model.ContactModel;
import com.viniciusduartelopes.triotry.model.SyncResponseModel;
import com.viniciusduartelopes.triotry.util.ContactsMembersUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class SyncService {

    @Autowired
    private MockIOService mockIOService;

    @Autowired
    private MailChimpService mailChimpService;

    public SyncResponseModel sync() {
        SyncResponseModel syncResponse = new SyncResponseModel(0, new ArrayList<>());

        List<ContactModel> contacts;

        try {
            contacts = mockIOService.getContacts();
        } catch (Exception ex) {
            log.log(Level.INFO, "Error getting contacts from MockIO API.");
            return syncResponse;
        }

        BatchSubscribeResponseModel batchSubscribeResponse;
        try {
            batchSubscribeResponse = mailChimpService.subscribeContacts(contacts);
        } catch (Exception ex) {
            log.log(Level.INFO, "Error subscribing members in MailChimp.");
            return syncResponse;
        }

        syncResponse.setSyncedContacts(batchSubscribeResponse.getTotal_created()
                + batchSubscribeResponse.getTotal_updated());

        if (batchSubscribeResponse.getNew_members() != null && !batchSubscribeResponse.getNew_members().isEmpty()) {
            syncResponse.getContacts().addAll(ContactsMembersUtil.membersToContacts(batchSubscribeResponse.getNew_members()));
        }

        if (batchSubscribeResponse.getUpdated_members() != null && !batchSubscribeResponse.getUpdated_members().isEmpty()) {
            syncResponse.getContacts().addAll(ContactsMembersUtil.membersToContacts(batchSubscribeResponse.getUpdated_members()));
        }

        return syncResponse;
    }
}
