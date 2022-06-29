package nl.gerimedica.service;

import nl.gerimedica.model.Record;
import nl.gerimedica.repository.RecordRepository;
import nl.gerimedica.util.RecordServiceUtil;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {


    private final RecordRepository recordRepository;
    private final RecordServiceUtil recordServiceUtil;

    @Autowired
    public RecordService(RecordRepository csvRepository, RecordServiceUtil csvUtils) {
        this.recordRepository = csvRepository;
        this.recordServiceUtil = csvUtils;
    }


    public List<Record> getAllData() {
        return recordRepository.findAll();
    }

    public void saveInDatabase(MultipartFile csvFile) {
        // First, we validate the given file
        recordServiceUtil.ValidateCSVFile(csvFile);

        // Check if the given CSV has the right template. After validation generate CSVRecords.
        Iterable<CSVRecord> records = recordServiceUtil.parseCSVFile(csvFile);

        // Extract each CSV record and save in an ArrayList
        List<Record> extractedRecords = new ArrayList<>();
        for (CSVRecord record : records){
            Record newRecord = recordServiceUtil.generateRecordObject(record);
            extractedRecords.add(newRecord);
        }

        // Save the extracted records in the database
        recordRepository.saveAll(extractedRecords);
    }

    public List<Record> getByCode(String code) {
        // Find the record with requested code
        Optional<Record> detectedRecord = recordRepository.findRecordByCode(code);

        // If the requested record exists, return it as the only element in the json.
        // If it does not exist, return empty list.
        List result = new ArrayList<>();
        if(detectedRecord.isPresent()){
            result.add(detectedRecord.get());
        }
        return result;
    }
}
