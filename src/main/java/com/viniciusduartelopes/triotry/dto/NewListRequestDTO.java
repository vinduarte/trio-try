package com.viniciusduartelopes.triotry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewListRequestDTO {

    private String name;
    private ListContactDTO contact;

    @JsonProperty("permission_reminder")
    private String permissionReminder;

    @JsonProperty("email_type_option")
    private boolean emailTypeOption;

    @JsonProperty("campaign_defaults")
    private CampaignDefaultsDTO campaignDefaults;
}
