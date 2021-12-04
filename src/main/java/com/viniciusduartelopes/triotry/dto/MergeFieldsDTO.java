package com.viniciusduartelopes.triotry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MergeFieldsDTO {

    @JsonProperty("FNAME")
    private String firstName;

    @JsonProperty("LNAME")
    private String lastName;
}
