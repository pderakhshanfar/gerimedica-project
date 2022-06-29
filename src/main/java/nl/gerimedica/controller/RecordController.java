package nl.gerimedica.controller;

import nl.gerimedica.model.Record;
import nl.gerimedica.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/grecord")
public class RecordController {

    private static final Logger LOG = LoggerFactory.getLogger(RecordController.class);

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping(produces = "application/json")
    public List<Record> getAllData(){
        return this.recordService.getAllData();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadCSV(@RequestParam("file") MultipartFile csvFile){
        recordService.saveInDatabase(csvFile);
    }

    @GetMapping(path = "{code}", produces = "application/json")
    public List<Record> getByCode(@PathVariable("code") String code){
        LOG.debug("Code {} requested!",code);
        return this.recordService.getByCode(code);
    }
}
