package com.viniciusduartelopes.triotry.rest.v1.configuration.service;

import com.viniciusduartelopes.triotry.rest.v1.model.ContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.SyncResponseModel;
import java.util.ArrayList;
import java.util.Arrays;
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

    public SyncResponseModel mockSync() {
        ContactModel mock1 = new ContactModel("my_mail01@gmail.com", "My First Name", "Last Name 1");
        ContactModel mock2 = new ContactModel("my_mail02@gmail.com", "My Second Name", "Last Name 2");
        SyncResponseModel syncModel = new SyncResponseModel();
        syncModel.setSyncedContacts(2);
        syncModel.setContacts(Arrays.asList(mock1, mock2));
        return syncModel;
    }

    public SyncResponseModel sync() {
        SyncResponseModel syncResponse = new SyncResponseModel(0, new ArrayList<>());

        List<ContactModel> contacts;

        try {
            contacts = mockIOService.getContacts();
        } catch (Exception ex) {
            log.log(Level.INFO, "Error getting contacts from MockIO API");
            return syncResponse;
        }

        return syncResponse;

    }
}
