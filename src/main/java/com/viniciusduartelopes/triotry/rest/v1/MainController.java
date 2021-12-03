package com.viniciusduartelopes.triotry.rest.v1;

import com.viniciusduartelopes.triotry.rest.v1.configuration.service.MailChimpService;
import com.viniciusduartelopes.triotry.rest.v1.configuration.service.MockIOService;
import com.viniciusduartelopes.triotry.rest.v1.model.ContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.GetListsRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.GetMembersRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.ListModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private MailChimpService mailChimpService;

    @Autowired
    private MockIOService mockIOService;

    @GetMapping(path = "lists")
    public GetListsRequestModel getAllLists() {
        return mailChimpService.getAllLists();
    }

    @PostMapping(path = "subscribeRandomContacts")
    public String subscribe() {
        return mailChimpService.subscribeRandomContacts();
    }

    @GetMapping(path = "mockapi")
    public List<ContactModel> getFromMockApi() {
        return mockIOService.getContacts();
    }

    @GetMapping(path = "allmembers")
    public GetMembersRequestModel getAllMembers() {
        return mailChimpService.getAllMembers();
    }

    @DeleteMapping(path = "deletelist")
    public GetListsRequestModel deleteList() {
        return mailChimpService.deleteList();
    }

    @PostMapping(path = "newlist")
    public ListModel createList() {
        return mailChimpService.createList();
    }

    @PostMapping(path = "restart")
    public ListModel restart() {
        mailChimpService.deleteList();
        return mailChimpService.createList();
    }
}
