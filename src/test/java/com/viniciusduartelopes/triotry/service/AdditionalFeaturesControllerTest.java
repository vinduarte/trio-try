package com.viniciusduartelopes.triotry.service;

import com.viniciusduartelopes.triotry.controller.AdditionalFeaturesController;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
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

    ContactDTO RECORD_1 = new ContactDTO("Augustine62@gmail.com", "Mohammed", "Koepp");
    ContactDTO RECORD_2 = new ContactDTO("Zoie50@gmail.com", "Adam", "Rolfson");
    ContactDTO RECORD_3 = new ContactDTO("Jazmyn15@gmail.com", "Norene", "Effertz");

    @Test
    public void getAllContacts() throws Exception {
        List<ContactDTO> contacts = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(mockIOService.getContacts()).thenReturn(contacts);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/additional/mockapi/contacts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[*]", anything("Jazmyn15@gmail.com")));
    }
}
