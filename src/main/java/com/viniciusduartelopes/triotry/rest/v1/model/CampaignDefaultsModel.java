package com.viniciusduartelopes.triotry.rest.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignDefaultsModel {

    private String from_name;
    private String from_email;
    private String subject;
    private String language;
}