package com.onurcelik.gerimedica.service.impl;

import com.onurcelik.gerimedica.domain.dto.CsvDTO;
import com.onurcelik.gerimedica.domain.entity.CsvEntity;
import com.onurcelik.gerimedica.domain.mapper.CsvMapper;
import com.onurcelik.gerimedica.exception.NoFoundException;
import com.onurcelik.gerimedica.repository.CsvRepository;
import com.onurcelik.gerimedica.service.CsvService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvServiceImplTest {

    @Mock
    private CsvRepository mockCsvRepository;

    private CsvService mockCsvService;

    @BeforeEach
    void setUp() {
        mockCsvService = new CsvServiceImpl(mockCsvRepository);
    }

    @Test
    void givenCsvDtoList_whenSaveAllDtoList_saveSuccessfully() {
        // given
        List<CsvDTO> csvDTOList = new ArrayList<>();
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "Type 1", "Losse harde keutels, zoals noten", null, new Date(), null, null));

        List<CsvEntity> csvEntityList = CsvMapper.CSV_MAPPER.toCsvEntityList(csvDTOList);
        when(mockCsvRepository.saveAll(any())).thenReturn(csvEntityList);

        // when
        mockCsvService.saveAll(csvDTOList);

        // then
        verify(mockCsvRepository).saveAll(any());
    }

    @Test
    void whenFetchAllCsv_andDataExists_returnCsvDtoList() {
        // given
        List<CsvEntity> csvEntityList = new ArrayList<>();
        csvEntityList.add(new CsvEntity(1L, "ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvEntityList.add(new CsvEntity(2L, "ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));
        csvEntityList.add(new CsvEntity(3L,"ZIB", "ZIB001", "Type 1", "Losse harde keutels, zoals noten", null, new Date(), null, null));

        List<CsvDTO> expectedCsvDTOList = CsvMapper.CSV_MAPPER.toCsvModelList(csvEntityList);
        when(mockCsvRepository.findAll()).thenReturn(csvEntityList);

        // when
        List<CsvDTO> actualCsvDTOList = mockCsvService.fetchAll();

        // then
        assertEquals(expectedCsvDTOList.size(), actualCsvDTOList.size());
    }

    @Test
    void whenFetchAllCsv_andNoDataExists_throwNoFoundException() {
        // given
        when(mockCsvRepository.findAll()).thenReturn(new ArrayList<>());

        // then
        Assertions.assertThrows(NoFoundException.class, () -> mockCsvService.fetchAll());
    }

    @Test
    void givenCode_whenFetchByCode_andCodeExists_returnCsvDto() throws Exception {
        // given
        String code = "271636001";
        CsvEntity expectedCsvEntity = new CsvEntity(1L, "ZIB", "ZIB001", code, "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1);

        when(mockCsvRepository.findByCode(code)).thenReturn(Optional.of(expectedCsvEntity));

        // when
        CsvDTO actualCsvDTO = mockCsvService.fetchByCode(code);

        // then
        assertEquals(expectedCsvEntity.getCode(), actualCsvDTO.getCode());
    }

    @Test
    void givenCode_whenFetchByCode_andCodeNotExits_throwNoFoundException() {
        // given
        String code = "271636001";
        when(mockCsvRepository.findByCode(code)).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(NoFoundException.class, () -> mockCsvService.fetchByCode(code));
    }

    @Test
    void whenDeleteAll_andDataExits_deleteSuccessfully() {
        // given
        List<CsvEntity> csvEntityList = new ArrayList<>();
        csvEntityList.add(new CsvEntity(1L, "ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvEntityList.add(new CsvEntity(2L, "ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));
        csvEntityList.add(new CsvEntity(3L,"ZIB", "ZIB001", "Type 1", "Losse harde keutels, zoals noten", null, new Date(), null, null));

        when(mockCsvRepository.findAll()).thenReturn(csvEntityList);

        // when
        mockCsvService.deleteAll();

        // then
        verify(mockCsvRepository).deleteAll();
    }

    @Test
    void whenDeleteAll_andDataNotExists_throwNoFoundException() {
        // given
        when(mockCsvRepository.findAll()).thenReturn(new ArrayList<>());

        // then
        Assertions.assertThrows(NoFoundException.class, () -> mockCsvService.deleteAll());
    }
}