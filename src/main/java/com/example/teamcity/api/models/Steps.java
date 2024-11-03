package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Parameterizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Steps {
    private Integer count;
    @Parameterizable
    private List<Step> step;
}
