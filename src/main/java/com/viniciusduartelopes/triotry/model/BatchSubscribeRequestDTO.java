package com.viniciusduartelopes.triotry.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BatchSubscribeRequestDTO {

    private List<MemberDTO> members;
    private boolean update_existing;
}
