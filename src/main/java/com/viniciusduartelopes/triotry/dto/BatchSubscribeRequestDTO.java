package com.viniciusduartelopes.triotry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BatchSubscribeRequestDTO {

    private List<MemberDTO> members;

    @JsonProperty("update_existing")
    private boolean updateExisting;
}
