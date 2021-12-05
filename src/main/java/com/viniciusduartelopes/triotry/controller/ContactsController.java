package com.viniciusduartelopes.triotry.controller;

import com.viniciusduartelopes.triotry.dto.SyncResponseDTO;
import com.viniciusduartelopes.triotry.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contacts")
public class ContactsController {

    @Autowired
    private SyncService syncService;

    @GetMapping("sync")
    public SyncResponseDTO sync() {
        return syncService.sync();
    }
}
