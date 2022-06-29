package nl.gerimedica.service;

import nl.gerimedica.model.Record;
import nl.gerimedica.repository.RecordRepository;
import nl.gerimedica.util.RecordServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
