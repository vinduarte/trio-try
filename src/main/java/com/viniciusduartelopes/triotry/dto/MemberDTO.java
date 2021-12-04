package com.viniciusduartelopes.triotry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @Getter
    @Setter
    @JsonProperty("email_address")
    private String emailAddress;

    @Getter
    @Setter
    private String status;

    @JsonProperty("merge_fields")
    private MergeFieldsDTO mergeFields;

    public MergeFieldsDTO getMergeFields() {
        return mergeFields;
    }

    public void setMergeFields(MergeFieldsDTO merge_fields) {
        this.mergeFields = merge_fields;
    }
}
