package com.onurcelik.gerimedica.util;


import com.onurcelik.gerimedica.domain.dto.CsvDTO;
import com.onurcelik.gerimedica.exception.UnsupportedFileException;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import static com.onurcelik.gerimedica.constant.Constant.TEXT_CSV;

@Component
public class CsvFileOperation {

    public List<CsvDTO> reader(MultipartFile file) throws IOException {
        if(!TEXT_CSV.equals(file.getContentType())) {
            throw new UnsupportedFileException(file.getContentType());
        }

        Reader reader = new InputStreamReader(file.getInputStream());

        CsvToBean<CsvDTO> csvToBean = new CsvToBeanBuilder(reader)
                .withSkipLines(1)
                .withType(CsvDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }

    public void writer(HttpServletResponse response, List<CsvDTO> list) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        StatefulBeanToCsv<CsvDTO> writer = new StatefulBeanToCsvBuilder<CsvDTO>(response.getWriter())
                .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(true)
                .build();
        writer.write(list);
        response.getWriter().close();
    }
}
