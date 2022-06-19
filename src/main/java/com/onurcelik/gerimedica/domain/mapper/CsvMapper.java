package com.onurcelik.gerimedica.domain.mapper;

import com.onurcelik.gerimedica.domain.dto.CsvDTO;
import com.onurcelik.gerimedica.domain.entity.CsvEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CsvMapper {

    CsvMapper CSV_MAPPER = Mappers.getMapper(CsvMapper.class);

    CsvDTO toCsvModel(CsvEntity csvEntity);

    List<CsvDTO> toCsvModelList(List<CsvEntity> csvEntityList);

    CsvEntity toCsvEntity(CsvDTO csvModel);

    List<CsvEntity> toCsvEntityList(List<CsvDTO> csvModelList);
}