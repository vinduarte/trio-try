package com.viniciusduartelopes.triotry.rest.v1.configuration.service;

import com.viniciusduartelopes.triotry.rest.v1.model.ContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.SyncResponseModel;
import java.util.Arrays;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    public SyncResponseModel mockSync() {
        ContactModel mock1 = new ContactModel("my_mail01@gmail.com", "My First Name", "Last Name 1");
        ContactModel mock2 = new ContactModel("my_mail02@gmail.com", "My Second Name", "Last Name 2");
        SyncResponseModel syncModel = new SyncResponseModel();
        syncModel.setSyncedContacts(2);
        syncModel.setContacts(Arrays.asList(mock1, mock2));
        return syncModel;
    }
}
