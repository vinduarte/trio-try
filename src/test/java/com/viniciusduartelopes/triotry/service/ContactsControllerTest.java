package com.viniciusduartelopes.triotry.service;

import com.viniciusduartelopes.triotry.controller.ContactsController;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
import com.viniciusduartelopes.triotry.dto.SyncResponseDTO;
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

@WebMvcTest(ContactsController.class)
public class ContactsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SyncService syncService;

    ContactDTO CONTACT_RECORD_1 = new ContactDTO("Augustine62@gmail.com", "Mohammed", "Koepp");
    ContactDTO CONTACT_RECORD_2 = new ContactDTO("Zoie50@gmail.com", "Adam", "Rolfson");
    ContactDTO CONTACT_RECORD_3 = new ContactDTO("Jazmyn15@gmail.com", "Norene", "Effertz");

    @Test
    public void doSyncThasReallySyncs() throws Exception {
        List<ContactDTO> contacts = new ArrayList<>(Arrays.asList(CONTACT_RECORD_1, CONTACT_RECORD_2, CONTACT_RECORD_3));
        SyncResponseDTO syncResponse = new SyncResponseDTO(3, contacts);

        Mockito.when(syncService.sync()).thenReturn(syncResponse);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/contacts/sync")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything("Jazmyn15@gmail.com")))
                .andExpect(jsonPath("$[*]", anything("Rolfson")))
                .andExpect(jsonPath("$[*]", anything("syncedContacts\": 3,")));
    }

    @Test
    public void doSyncThasDontReallySyncs() throws Exception {
        List<ContactDTO> contacts = new ArrayList<>();
        SyncResponseDTO syncResponse = new SyncResponseDTO(0, contacts);

        Mockito.when(syncService.sync()).thenReturn(syncResponse);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/contacts/sync")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything("contacts\":")))
                .andExpect(jsonPath("$[*]", anything("syncedContacts\": 0,")));
    }
}
