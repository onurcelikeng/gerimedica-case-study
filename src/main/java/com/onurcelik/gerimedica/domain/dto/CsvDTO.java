package com.onurcelik.gerimedica.domain.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class CsvDTO {

    @CsvBindByPosition(position = 0)
    private String source;

    @CsvBindByPosition(position = 1)
    private String codeListCode;

    @CsvBindByPosition(position = 2)
    private String code;

    @CsvBindByPosition(position = 3)
    private String displayValue;

    @CsvBindByPosition(position = 4)
    private String longDescription;

    @CsvBindByPosition(position = 5)
    @CsvDate(value = "dd-MM-yyyy")
    private Date fromDate;

    @CsvBindByPosition(position = 6)
    @CsvDate(value = "dd-MM-yyyy")
    private Date toDate;

    @CsvBindByPosition(position = 7)
    private Integer sortingPriority;
}