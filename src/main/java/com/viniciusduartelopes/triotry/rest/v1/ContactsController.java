package com.viniciusduartelopes.triotry.rest.v1;

import com.viniciusduartelopes.triotry.rest.v1.configuration.service.SyncService;
import com.viniciusduartelopes.triotry.rest.v1.model.SyncResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contacts")
public class ContactsController {

    @Autowired
    private SyncService syncService;

    @PostMapping("sync")
    public SyncResponseModel sync() {
        return syncService.sync();
    }
}
