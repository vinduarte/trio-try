package com.viniciusduartelopes.triotry.service;

import com.viniciusduartelopes.triotry.controller.AdditionalFeaturesController;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
import com.viniciusduartelopes.triotry.dto.GetListsRequestDTO;
import com.viniciusduartelopes.triotry.dto.GetMembersRequestDTO;
import com.viniciusduartelopes.triotry.dto.ListDTO;
import com.viniciusduartelopes.triotry.dto.MemberDTO;
import com.viniciusduartelopes.triotry.dto.MergeFieldsDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdditionalFeaturesController.class)
public class AdditionalFeaturesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MockIOService mockIOService;

    @MockBean
    MailChimpService mailChimpService;

    ContactDTO CONTACT_RECORD_1 = new ContactDTO("Augustine62@gmail.com", "Mohammed", "Koepp");
    ContactDTO CONTACT_RECORD_2 = new ContactDTO("Zoie50@gmail.com", "Adam", "Rolfson");
    ContactDTO CONTACT_RECORD_3 = new ContactDTO("Jazmyn15@gmail.com", "Norene", "Effertz");

    ListDTO LIST_RECORD_1 = new ListDTO("abc123", "mock list1");
    ListDTO LIST_RECORD_2 = new ListDTO("xyz123", "mock list2");
    ListDTO LIST_RECORD_3 = new ListDTO("qwert1", "mock list3");

    MemberDTO MEMBER_RECORD_1 = new MemberDTO("email1@email.com", "subcribed", new MergeFieldsDTO("firstname1", "lastname1"));
    MemberDTO MEMBER_RECORD_2 = new MemberDTO("email2@email.com", "subcribed", new MergeFieldsDTO("firstname2", "lastname2"));
    MemberDTO MEMBER_RECORD_3 = new MemberDTO("email3@email.com", "subcribed", new MergeFieldsDTO("firstname3", "lastname3"));

    static final String MOCKAPI = "/additional/mockapi";
    static final String MAILCHIMP_API = "/additional/mailchimp";

    @Test
    public void getMockAPIAllContacts() throws Exception {
        List<ContactDTO> contacts = new ArrayList<>(Arrays.asList(CONTACT_RECORD_1, CONTACT_RECORD_2, CONTACT_RECORD_3));

        Mockito.when(mockIOService.getContacts()).thenReturn(contacts);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MOCKAPI + "/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything("Jazmyn15@gmail.com")));
    }

    @Test
    public void getMockAPINoContacts() throws Exception {
        List<ContactDTO> contacts = new ArrayList<>();

        Mockito.when(mockIOService.getContacts()).thenReturn(contacts);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MOCKAPI + "/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    public void getMailChimpAPIAllLists() throws Exception {
        GetListsRequestDTO mockedListRequest = new GetListsRequestDTO(new ArrayList<>(Arrays.asList(LIST_RECORD_1, LIST_RECORD_2, LIST_RECORD_3)));

        Mockito.when(mailChimpService.getAllLists()).thenReturn(mockedListRequest);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MAILCHIMP_API + "/lists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything("xyz123")));
    }

    @Test
    public void getMailChimpAPIAllListsEmpty() throws Exception {
        GetListsRequestDTO mockedListRequest = new GetListsRequestDTO();

        Mockito.when(mailChimpService.getAllLists()).thenReturn(mockedListRequest);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MAILCHIMP_API + "/lists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", anything("lists=null")));
    }

    @Test
    public void getMailChimpAPISubscribeRandom() throws Exception {
        Mockito.when(mailChimpService.subscribeRandomContacts()).thenReturn("new_members...");

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(MAILCHIMP_API + "/lists/subscribe-random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", anything("new_members")));
    }

    @Test
    public void getMailChimpAPIGetAllMembers() throws Exception {
        GetMembersRequestDTO mockedMembersRequest = new GetMembersRequestDTO(Arrays.asList(MEMBER_RECORD_1, MEMBER_RECORD_2, MEMBER_RECORD_3));

        Mockito.when(mailChimpService.getAllMembers()).thenReturn(mockedMembersRequest);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MAILCHIMP_API + "/list/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything("email1@email.com")))
                .andExpect(jsonPath("$[*]", anything("firsname1")));
    }

    @Test
    public void getMailChimpAPIRestart() throws Exception {
        String name = "abc123";
        String list = "my new list";
        ListDTO listMock = new ListDTO(name, list);

        Mockito.when(mailChimpService.createList()).thenReturn(listMock);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(MAILCHIMP_API + "/restart-environment")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything(list)))
                .andExpect(jsonPath("$[*]", anything(name)));
    }
}
