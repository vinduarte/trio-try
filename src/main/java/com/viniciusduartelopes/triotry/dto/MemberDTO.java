package com.viniciusduartelopes.triotry.dto;

import javax.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    @Getter
    @Setter
    private String email_address;

    @Getter
    @Setter
    private String status;

    private MergeFieldsDTO merge_fields;

    @XmlElement(name = "merge_fields")
    public MergeFieldsDTO getMerge_fields() {
        return merge_fields;
    }

    public void setMerge_fields(MergeFieldsDTO merge_fields) {
        this.merge_fields = merge_fields;
    }
}
