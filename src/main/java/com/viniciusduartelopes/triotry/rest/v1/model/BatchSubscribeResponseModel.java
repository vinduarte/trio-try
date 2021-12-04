package com.viniciusduartelopes.triotry.rest.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchSubscribeResponseModel {

    private List<MemberModel> new_members;
    private List<MemberModel> updated_members;
    private Integer total_created;
    private Integer total_updated;
}
