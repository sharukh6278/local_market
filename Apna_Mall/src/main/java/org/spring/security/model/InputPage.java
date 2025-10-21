package org.spring.security.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InputPage {
    int pageNumber;
    int pageSize;
    String sortDirection;
    String sortColumn;

}