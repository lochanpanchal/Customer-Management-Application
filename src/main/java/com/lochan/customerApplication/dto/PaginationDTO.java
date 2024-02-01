package com.lochan.customerApplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDTO {

    private int page = 0;
    private int size = 10;
}
