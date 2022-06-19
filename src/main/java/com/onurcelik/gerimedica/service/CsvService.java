package com.onurcelik.gerimedica.service;

import com.onurcelik.gerimedica.domain.dto.CsvDTO;

import java.util.List;

public interface CsvService {

    void saveAll(List<CsvDTO> list);

    List<CsvDTO> fetchAll();

    CsvDTO fetchByCode(String code) throws Exception;

    void deleteAll();
}