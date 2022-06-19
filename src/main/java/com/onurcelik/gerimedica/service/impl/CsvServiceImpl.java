package com.onurcelik.gerimedica.service.impl;

import com.onurcelik.gerimedica.domain.dto.CsvDTO;
import com.onurcelik.gerimedica.domain.entity.CsvEntity;
import com.onurcelik.gerimedica.exception.NoFoundException;
import com.onurcelik.gerimedica.repository.CsvRepository;
import com.onurcelik.gerimedica.service.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.onurcelik.gerimedica.domain.mapper.CsvMapper.CSV_MAPPER;

@Service
@RequiredArgsConstructor
public class CsvServiceImpl implements CsvService {

    private final CsvRepository csvRepository;

    @Override
    @Transactional
    public void saveAll(List<CsvDTO> csvDTOList) {
        List<CsvEntity> csvEntities = CSV_MAPPER.toCsvEntityList(csvDTOList);
        csvRepository.saveAll(csvEntities);
    }

    @Override
    public List<CsvDTO> fetchAll() {
        List<CsvEntity> csvEntities = csvRepository.findAll();
        if (csvEntities.isEmpty()) {
            throw new NoFoundException("CSV table is empty!");
        }

        return CSV_MAPPER.toCsvModelList(csvEntities);
    }

    @Override
    public CsvDTO fetchByCode(String code) {
        CsvEntity csvEntity = csvRepository.findByCode(code).orElseThrow(() -> new NoFoundException(code + " is not exist in table!"));
        return CSV_MAPPER.toCsvModel(csvEntity);
    }

    @Override
    @Transactional
    public void deleteAll() {
        List<CsvEntity> csvEntities = csvRepository.findAll();
        if (csvEntities.isEmpty()) {
            throw new NoFoundException("CSV table is empty!");
        }

        csvRepository.deleteAll();
    }
}
