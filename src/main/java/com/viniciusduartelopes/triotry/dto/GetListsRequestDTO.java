package com.viniciusduartelopes.triotry.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetListsRequestDTO {

    private List<ListDTO> lists;
}
