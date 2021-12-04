package com.viniciusduartelopes.triotry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampaignDefaultsDTO {

    @JsonProperty("from_name")
    private String fromName;

    @JsonProperty("from_email")
    private String fromEmail;

    private String subject;
    private String language;
}
