package com.viniciusduartelopes.triotry.service;

import com.viniciusduartelopes.triotry.dto.BatchSubscribeResponseDTO;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
import com.viniciusduartelopes.triotry.dto.SyncResponseDTO;
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

    public SyncResponseDTO sync() {
        SyncResponseDTO syncResponse = new SyncResponseDTO(0, new ArrayList<>());

        List<ContactDTO> contacts;

        try {
            contacts = mockIOService.getContacts();
        } catch (Exception ex) {
            log.log(Level.INFO, "Error getting contacts from MockIO API.");
            return syncResponse;
        }

        BatchSubscribeResponseDTO batchSubscribeResponse;
        try {
            batchSubscribeResponse = mailChimpService.subscribeContacts(contacts);
        } catch (Exception ex) {
            log.log(Level.INFO, "Error subscribing members in MailChimp.");
            return syncResponse;
        }

        syncResponse.setSyncedContacts(batchSubscribeResponse.getTotalCreated()
                + batchSubscribeResponse.getTotalUpdated());

        if (batchSubscribeResponse.getNewMembers() != null && !batchSubscribeResponse.getNewMembers().isEmpty()) {
            syncResponse.getContacts().addAll(ContactsMembersUtil.membersToContacts(batchSubscribeResponse.getNewMembers()));
        }

        if (batchSubscribeResponse.getUpdatedMembers() != null && !batchSubscribeResponse.getUpdatedMembers().isEmpty()) {
            syncResponse.getContacts().addAll(ContactsMembersUtil.membersToContacts(batchSubscribeResponse.getUpdatedMembers()));
        }

        return syncResponse;
    }
}
