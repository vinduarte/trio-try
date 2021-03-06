package com.viniciusduartelopes.triotry.service;

import com.viniciusduartelopes.triotry.configuration.ApplicationProperties;
import com.viniciusduartelopes.triotry.dto.BatchSubscribeRequestDTO;
import com.viniciusduartelopes.triotry.dto.BatchSubscribeResponseDTO;
import com.viniciusduartelopes.triotry.dto.CampaignDefaultsDTO;
import com.viniciusduartelopes.triotry.dto.ContactDTO;
import com.viniciusduartelopes.triotry.dto.GetListsRequestDTO;
import com.viniciusduartelopes.triotry.dto.GetMembersRequestDTO;
import com.viniciusduartelopes.triotry.dto.ListContactDTO;
import com.viniciusduartelopes.triotry.dto.ListDTO;
import com.viniciusduartelopes.triotry.dto.MemberDTO;
import com.viniciusduartelopes.triotry.dto.MergeFieldsDTO;
import com.viniciusduartelopes.triotry.dto.NewListRequestDTO;
import com.viniciusduartelopes.triotry.util.ContactsMembersUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Log
public class MailChimpService {

    @Setter
    @Getter
    private ListDTO list;

    @Autowired
    private ApplicationProperties applicationProperties;

    private WebClient webClientToMailChimp;

    @PostConstruct
    public void init() {
        initBaseWebClientToMailChimp();
        updateOrCreateList();
        log.log(Level.INFO, "Successfully started MailChimp service.");
    }

    private void initBaseWebClientToMailChimp() {
        if (webClientToMailChimp == null) {
            webClientToMailChimp = WebClient.builder()
                    .baseUrl(applicationProperties.getMailChimpUrl())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, applicationProperties.getAuth())
                    .build();
        }
    }

    private void updateOrCreateList() {
        GetListsRequestDTO listsRequestModel = getAllLists();

        if (listsRequestModel != null && listsRequestModel.getLists() != null && !listsRequestModel.getLists().isEmpty()) {
            list = listsRequestModel.getLists().stream()
                    .filter(lista -> lista.getName().equals(applicationProperties.getListName()))
                    .findAny()
                    .get();

            if (list == null) {
                list = createList();
            }
        } else {
            list = createList();
        }
    }

    public GetListsRequestDTO getAllLists() {
        return webClientToMailChimp.get()
                .uri("/lists")
                .retrieve()
                .bodyToFlux(GetListsRequestDTO.class)
                .blockFirst();
    }

    private MemberDTO createRandomMember() {
        Random random = new Random();
        MergeFieldsDTO mergeFields = new MergeFieldsDTO("contactfname" + random.nextInt(), "contactlname" + random.nextInt());
        return new MemberDTO(random.nextInt() + "@gmail.com", "subscribed", mergeFields);
    }

    private MemberDTO alwaysTheSame1() {
        MergeFieldsDTO mergeFields = new MergeFieldsDTO("nice guy", "smith");
        return new MemberDTO("smith334333@gmail.com", "subscribed", mergeFields);
    }

    private MemberDTO alwaysTheSame2() {
        MergeFieldsDTO mergeFields = new MergeFieldsDTO("trivium", "quadrivium");
        return new MemberDTO("trivium-quadrivium@gmail.com", "subscribed", mergeFields);
    }

    public String subscribeRandomContacts() {
        BatchSubscribeRequestDTO batch = new BatchSubscribeRequestDTO(
                Arrays.asList(createRandomMember(), createRandomMember(), alwaysTheSame1(), alwaysTheSame2()), true);

        return webClientToMailChimp.post()
                .uri("/lists/" + list.getId())
                .body(Mono.just(batch), BatchSubscribeRequestDTO.class)
                .retrieve()
                .bodyToFlux(String.class).blockFirst();
    }

    public BatchSubscribeResponseDTO subscribeContacts(List<ContactDTO> contacts) {
        BatchSubscribeRequestDTO batch
                = new BatchSubscribeRequestDTO(ContactsMembersUtil.contactsToMembers(contacts), true);

        return webClientToMailChimp.post()
                .uri("/lists/" + list.getId())
                .body(Mono.just(batch), BatchSubscribeRequestDTO.class)
                .retrieve()
                .bodyToFlux(BatchSubscribeResponseDTO.class).blockFirst();
    }

    public GetMembersRequestDTO getAllMembers() {
        return webClientToMailChimp.get()
                .uri(uriBuilder -> uriBuilder
                .path("/lists")
                .path("/" + list.getId())
                .path("/members")
                .queryParam("count", "1000")
                .build())
                .retrieve()
                .bodyToFlux(GetMembersRequestDTO.class)
                .blockFirst();
    }

    public GetListsRequestDTO deleteList() {
        GetListsRequestDTO listsRequestModel;

        try {
            listsRequestModel = webClientToMailChimp.delete()
                    .uri(uriBuilder -> uriBuilder
                    .path("/lists")
                    .path("/" + list.getId())
                    .build())
                    .retrieve()
                    .bodyToFlux(GetListsRequestDTO.class).blockFirst();
        } catch (Exception ex) {
            log.log(Level.INFO, "Not possible to delete a list.");
            return null;
        }

        list = null;
        return listsRequestModel;
    }

    public ListDTO createList() {
        NewListRequestDTO newList = new NewListRequestDTO();
        ListContactDTO listContact = new ListContactDTO("my_company", "my_address1", "my_address2", "my_city", "my_state", "my_zip_code", "BR");
        CampaignDefaultsDTO campaignDefault = new CampaignDefaultsDTO("my_from_name", "my_from_email@gmail.com", "my_subject", "EN_US");
        newList.setCampaignDefaults(campaignDefault);
        newList.setContact(listContact);
        newList.setEmailTypeOption(true);
        newList.setName(applicationProperties.getListName());
        newList.setPermissionReminder("permission_reminder");

        ListDTO listModel = webClientToMailChimp.post()
                .uri("/lists")
                .body(Mono.just(newList), NewListRequestDTO.class)
                .retrieve()
                .bodyToFlux(ListDTO.class)
                .blockFirst();

        list = listModel;
        return listModel;
    }
}
