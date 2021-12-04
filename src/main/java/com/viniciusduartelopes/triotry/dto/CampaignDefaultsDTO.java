package com.viniciusduartelopes.triotry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignDefaultsDTO {

    private String from_name;
    private String from_email;
    private String subject;
    private String language;
}
