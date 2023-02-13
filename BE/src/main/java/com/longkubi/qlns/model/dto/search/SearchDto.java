package com.longkubi.qlns.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchDto {
    private UUID id;

    private String creator;

    private Date dateCreated;

    private String changedBy;

    private Date dateChange;

    private Integer pageIndex;

    private Integer pageSize;

    private String orderByFilter;
}
