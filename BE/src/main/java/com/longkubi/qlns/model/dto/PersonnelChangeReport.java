package com.longkubi.qlns.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonnelChangeReport {
    private String name;
    private List<Integer> value = new ArrayList<>();
    private Integer count;

    public PersonnelChangeReport(String name, List<Integer> value) {
        this.name = name;
        this.value = value;
    }
}
