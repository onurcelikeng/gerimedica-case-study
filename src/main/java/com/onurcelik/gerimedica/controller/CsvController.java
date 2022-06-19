package com.onurcelik.gerimedica.controller;

import com.onurcelik.gerimedica.domain.dto.CsvDTO;
import com.onurcelik.gerimedica.service.CsvService;
import com.onurcelik.gerimedica.util.CsvFileOperation;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.onurcelik.gerimedica.constant.Constant.TEXT_CSV;

@Tag(name = "CSV")
@RestController
@RequestMapping("/api/csv")
@RequiredArgsConstructor
public class CsvController {

    private final CsvService csvService;
    private final CsvFileOperation csvFileOperation;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadData(@RequestParam("csv-file") MultipartFile file) throws IOException {
        List<CsvDTO> csvDTOList = csvFileOperation.reader(file);
        csvService.saveAll(csvDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public void fetchAllData(HttpServletResponse response) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
        response.setContentType(TEXT_CSV);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fetch-all-data.csv\"");

        List<CsvDTO> csvDTOList = csvService.fetchAll();
        csvFileOperation.writer(response, csvDTOList);
    }

    @GetMapping("/{code}")
    public void fetchByCode(HttpServletResponse response, @PathVariable("code") String code) throws Exception {
        response.setContentType(TEXT_CSV);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fetch-by-code.csv\"");

        CsvDTO csvDTO = csvService.fetchByCode(code);
        csvFileOperation.writer(response, List.of(csvDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllData() {
        csvService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
