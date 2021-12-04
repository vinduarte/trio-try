package com.viniciusduartelopes.triotry.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchSubscribeResponseDTO {

    @JsonProperty("new_members")
    private List<MemberDTO> newMembers;

    @JsonProperty("updated_members")
    private List<MemberDTO> updatedMembers;

    @JsonProperty("total_created")
    private Integer totalCreated;

    @JsonProperty("total_updated")
    private Integer totalUpdated;
}
