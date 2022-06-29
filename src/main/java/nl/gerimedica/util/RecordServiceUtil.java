package nl.gerimedica.util;

import nl.gerimedica.exception.CSVParseException;
import nl.gerimedica.exception.IllegalFileTypeException;
import nl.gerimedica.model.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class RecordServiceUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RecordServiceUtil.class);

    private final List<String> expectedHeaders =
            Arrays.asList("source",
                    "codeListCode",
                    "code",
                    "displayValue",
                    "longDescription",
                    "fromDate",
                    "toDate",
                    "sortingPriority"
            );

    public void ValidateCSVFile(MultipartFile csvFile) {

        // check if the uploaded file is CSV. If it is not we show BAD_REQUEST response (look at exception directory)
        if(!csvFile.getContentType().equals("text/csv")){
            throw new IllegalFileTypeException("The given file should be csv file while we received "+csvFile.getContentType());
        }
    }


    public Iterable<CSVRecord> parseCSVFile(MultipartFile csvFile){
        Iterable<CSVRecord> csvRecords;
        try {
            BufferedReader fileReader = new BufferedReader(new
                    InputStreamReader(csvFile.getInputStream(), "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader, getCSVFormat());
            csvRecords = csvParser.getRecords();
            // check headers with the expected ones
            List<String> extractedHeaders = csvParser.getHeaderNames();
            // Throw exceptions if the headers are not expected
            if (!extractedHeaders.equals(this.expectedHeaders)) {
                throw new IllegalFileTypeException("Not expected headers" + csvParser.getHeaderNames());
            }
        } catch (IOException e) {
            LOG.error("could not parse the CSV file!");
            throw new CSVParseException("could not parse the CSV file!");
        }

        return csvRecords;
    }

    // This method generates the CSVFormat that fits with the exercise.csv
    private CSVFormat getCSVFormat(){
        CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader()
                .setSkipHeaderRecord(true)
                .build();

        return format;
    }

    public Record generateRecordObject(CSVRecord record) {
            return new Record(record);
    }
}
