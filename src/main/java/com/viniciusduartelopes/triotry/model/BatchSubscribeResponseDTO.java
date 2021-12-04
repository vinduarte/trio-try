package com.viniciusduartelopes.triotry.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchSubscribeResponseDTO {

    private List<MemberDTO> new_members;
    private List<MemberDTO> updated_members;
    private Integer total_created;
    private Integer total_updated;
}
