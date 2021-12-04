package com.viniciusduartelopes.triotry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewListRequestModel {

    private String name;
    private ListContactModel contact;
    private String permission_reminder;
    private boolean email_type_option;
    private CampaignDefaultsModel campaign_defaults;
}
