package com.viniciusduartelopes.triotry.rest.v1;

import com.viniciusduartelopes.triotry.rest.v1.configuration.service.MailChimpService;
import com.viniciusduartelopes.triotry.rest.v1.configuration.service.MockIOService;
import com.viniciusduartelopes.triotry.rest.v1.model.ContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.GetListsRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.GetMembersRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.ListModel;
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
    public GetListsRequestModel getAllLists() {
        return mailChimpService.getAllLists();
    }

    @PostMapping(path = "/mailchimp/lists/subscribe-random")
    public String subscribe() {
        return mailChimpService.subscribeRandomContacts();
    }

    @GetMapping(path = "mockapi/contacts")
    public List<ContactModel> getFromMockApi() {
        return mockIOService.getContacts();
    }

    @GetMapping(path = "/mailchimp/list/members")
    public GetMembersRequestModel getAllMembers() {
        return mailChimpService.getAllMembers();
    }

    @PostMapping(path = "/mailchimp/restart-environment")
    public ListModel restart() {
        mailChimpService.deleteList();
        return mailChimpService.createList();
    }
}
