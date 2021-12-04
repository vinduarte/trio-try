package com.viniciusduartelopes.triotry.controller;

import com.viniciusduartelopes.triotry.service.SyncService;
import com.viniciusduartelopes.triotry.model.SyncResponseDTO;
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
    public SyncResponseDTO sync() {
        return syncService.sync();
    }
}
