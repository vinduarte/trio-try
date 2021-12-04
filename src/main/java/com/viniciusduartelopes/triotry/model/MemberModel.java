package com.viniciusduartelopes.triotry.model;

import javax.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class MemberModel {

    @Getter
    @Setter
    private String email_address;

    @Getter
    @Setter
    private String status;

    private MergeFieldsModel merge_fields;

    @XmlElement(name = "merge_fields")
    public MergeFieldsModel getMerge_fields() {
        return merge_fields;
    }

    public void setMerge_fields(MergeFieldsModel merge_fields) {
        this.merge_fields = merge_fields;
    }
}
