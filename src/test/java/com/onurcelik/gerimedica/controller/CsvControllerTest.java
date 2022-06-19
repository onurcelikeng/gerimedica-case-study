package com.onurcelik.gerimedica.controller;

import com.onurcelik.gerimedica.domain.dto.CsvDTO;
import com.onurcelik.gerimedica.service.CsvService;
import com.onurcelik.gerimedica.util.CsvFileOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CsvControllerTest {

    @Mock
    private CsvService csvService;

    @Mock
    private CsvFileOperation csvFileOperation;

    @InjectMocks
    private CsvController csvController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        csvController = new CsvController(csvService, csvFileOperation);
        mockMvc = MockMvcBuilders.standaloneSetup(csvController).build();
    }

    @Test
    void givenCsvFile_whenUploadData_andCsvFileFormatCorrect_returnCreated() throws Exception {
        InputStream is = csvController.getClass().getClassLoader().getResourceAsStream("exercise.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("csv-file", "exercise.csv", "multipart/form-data", is);
        List<CsvDTO> csvDTOList = new ArrayList<>();
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));

        when(csvFileOperation.reader(any())).thenReturn(csvDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/csv")
                        .file(mockMultipartFile))
                .andExpect(status().isCreated());
    }

    @Test
    void whenFetchAllData_returnOK() throws Exception {
        List<CsvDTO> csvDTOList = new ArrayList<>();
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", new Date(), null, 1));
        csvDTOList.add(new CsvDTO("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2));

        when(csvService.fetchAll()).thenReturn(csvDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/csv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenFetchDataByCode_returnOK() throws Exception {
        CsvDTO csvDTO = new CsvDTO("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, new Date(), null, 2);
        when(csvService.fetchByCode(any())).thenReturn(csvDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/csv/271636001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDeleteData_returnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/csv")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}