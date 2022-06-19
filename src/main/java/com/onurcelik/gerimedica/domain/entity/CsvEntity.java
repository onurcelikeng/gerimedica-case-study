package com.onurcelik.gerimedica.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_csv")
public class CsvEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "code_list_code", nullable = false)
    private String codeListCode;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "display_value", nullable = false)
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;
}