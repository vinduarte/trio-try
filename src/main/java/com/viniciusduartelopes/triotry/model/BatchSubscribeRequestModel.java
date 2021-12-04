package com.viniciusduartelopes.triotry.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BatchSubscribeRequestModel {

    private List<MemberModel> members;
    private boolean update_existing;
}
