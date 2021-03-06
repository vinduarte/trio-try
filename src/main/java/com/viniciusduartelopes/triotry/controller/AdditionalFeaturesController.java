package com.viniciusduartelopes.triotry.controller;

import com.viniciusduartelopes.triotry.service.MailChimpService;
import com.viniciusduartelopes.triotry.service.MockIOService;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
import com.viniciusduartelopes.triotry.dto.GetListsRequestDTO;
import com.viniciusduartelopes.triotry.dto.GetMembersRequestDTO;
import com.viniciusduartelopes.triotry.dto.ListDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("additional")
public class AdditionalFeaturesController {

    @Autowired
    private MailChimpService mailChimpService;

    @Autowired
    private MockIOService mockIOService;

    @GetMapping(path = "/mailchimp/lists")
    public GetListsRequestDTO getAllLists() {
        return mailChimpService.getAllLists();
    }

    @PostMapping(path = "/mailchimp/lists/subscribe-random")
    public String subscribe() {
        return mailChimpService.subscribeRandomContacts();
    }

    @GetMapping(path = "mockapi/contacts")
    public List<ContactDTO> getFromMockApi() {
        return mockIOService.getContacts();
    }

    @GetMapping(path = "/mailchimp/list/members")
    public GetMembersRequestDTO getAllMembers() {
        return mailChimpService.getAllMembers();
    }

    @PostMapping(path = "/mailchimp/restart-environment")
    public ListDTO restart() {
        mailChimpService.deleteList();
        return mailChimpService.createList();
    }
}
