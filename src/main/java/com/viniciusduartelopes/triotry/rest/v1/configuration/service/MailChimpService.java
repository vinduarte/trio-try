package com.viniciusduartelopes.triotry.rest.v1.configuration.service;

import com.viniciusduartelopes.triotry.rest.v1.configuration.ConfigurationSingleton;
import com.viniciusduartelopes.triotry.rest.v1.model.BatchSubscribeRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.BatchSubscribeResponseModel;
import com.viniciusduartelopes.triotry.rest.v1.model.CampaignDefaultsModel;
import com.viniciusduartelopes.triotry.rest.v1.model.ContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.GetListsRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.GetMembersRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.model.ListContactModel;
import com.viniciusduartelopes.triotry.rest.v1.model.ListModel;
import com.viniciusduartelopes.triotry.rest.v1.model.MemberModel;
import com.viniciusduartelopes.triotry.rest.v1.model.MergeFieldsModel;
import com.viniciusduartelopes.triotry.rest.v1.model.NewListRequestModel;
import com.viniciusduartelopes.triotry.rest.v1.util.ContactsMembersUtil;
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
    private ListModel list;

    @Autowired
    private ConfigurationSingleton configurationSingleton;

    @Getter
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
                    .baseUrl(configurationSingleton.getMailChimpUrl())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, configurationSingleton.getAuth())
                    .build();
        }
    }

    private void updateOrCreateList() {
        GetListsRequestModel listsRequestModel = getAllLists();

        if (listsRequestModel != null && listsRequestModel.getLists() != null && !listsRequestModel.getLists().isEmpty()) {
            list = listsRequestModel.getLists().stream()
                    .filter(lista -> lista.getName().equals(configurationSingleton.getListName()))
                    .findAny()
                    .get();

            if (list == null) {
                list = createList();
            }
        } else {
            list = createList();
        }
    }

    public GetListsRequestModel getAllLists() {
        return getWebClientToMailChimp().get()
                .uri("/lists")
                .retrieve()
                .bodyToFlux(GetListsRequestModel.class)
                .blockFirst();
    }

    private MemberModel createRandomMember() {
        Random random = new Random();
        MergeFieldsModel mergeFields = new MergeFieldsModel("contactfname" + random.nextInt(), "contactlname" + random.nextInt());
        return new MemberModel(random.nextInt() + "@gmail.com", "subscribed", mergeFields);
    }

    private MemberModel alwaysTheSame1() {
        MergeFieldsModel mergeFields = new MergeFieldsModel("nice guy", "smith");
        return new MemberModel("smith334333@gmail.com", "subscribed", mergeFields);
    }

    private MemberModel alwaysTheSame2() {
        MergeFieldsModel mergeFields = new MergeFieldsModel("trivium", "quadrivium");
        return new MemberModel("trivium-quadrivium@gmail.com", "subscribed", mergeFields);
    }

    public String subscribeRandomContacts() {
        BatchSubscribeRequestModel batch = new BatchSubscribeRequestModel(
                Arrays.asList(createRandomMember(), createRandomMember(), alwaysTheSame1(), alwaysTheSame2()), true);

        return getWebClientToMailChimp().post()
                .uri("/lists/" + list.getId())
                .body(Mono.just(batch), BatchSubscribeRequestModel.class)
                .retrieve()
                .bodyToFlux(String.class).blockFirst();
    }

    public BatchSubscribeResponseModel subscribeContacts(List<ContactModel> contacts) {
        BatchSubscribeRequestModel batch
                = new BatchSubscribeRequestModel(ContactsMembersUtil.contactsToMembers(contacts), true);

        return getWebClientToMailChimp().post()
                .uri("/lists/" + list.getId())
                .body(Mono.just(batch), BatchSubscribeRequestModel.class)
                .retrieve()
                .bodyToFlux(BatchSubscribeResponseModel.class).blockFirst();
    }

    public GetMembersRequestModel getAllMembers() {
        return getWebClientToMailChimp().get()
                .uri(uriBuilder -> uriBuilder
                .path("/lists")
                .path("/" + list.getId())
                .path("/members")
                .queryParam("count", "1000")
                .build())
                .retrieve()
                .bodyToFlux(GetMembersRequestModel.class)
                .blockFirst();
    }

    public GetListsRequestModel deleteList() {
        GetListsRequestModel listsRequestModel;

        try {
            listsRequestModel = getWebClientToMailChimp().delete()
                    .uri(uriBuilder -> uriBuilder
                    .path("/lists")
                    .path("/" + list.getId())
                    .build())
                    .retrieve()
                    .bodyToFlux(GetListsRequestModel.class).blockFirst();
        } catch (Exception ex) {
            log.log(Level.INFO, "Not possible to delete a list.");
            return null;
        }

        list = null;
        return listsRequestModel;
    }

    public ListModel createList() {
        NewListRequestModel newList = new NewListRequestModel();
        ListContactModel listContact = new ListContactModel("my_company", "my_address1", "my_address2", "my_city", "my_state", "my_zip_code", "BR");
        CampaignDefaultsModel campaignDefault = new CampaignDefaultsModel("my_from_name", "my_from_email@gmail.com", "my_subject", "EN_US");
        newList.setCampaign_defaults(campaignDefault);
        newList.setContact(listContact);
        newList.setEmail_type_option(true);
        newList.setName(configurationSingleton.getListName());
        newList.setPermission_reminder("permission_reminder");

        ListModel listModel = getWebClientToMailChimp().post()
                .uri("/lists")
                .body(Mono.just(newList), NewListRequestModel.class)
                .retrieve()
                .bodyToFlux(ListModel.class)
                .blockFirst();

        list = listModel;
        return listModel;
    }
}
