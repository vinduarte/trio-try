package com.viniciusduartelopes.triotry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewListRequestDTO {

    private String name;
    private ListContactDTO contact;
    private String permission_reminder;
    private boolean email_type_option;
    private CampaignDefaultsDTO campaign_defaults;
}
