package com.viniciusduartelopes.triotry.rest.v1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetListsRequestModel {

    private List<ListModel> lists;
}
