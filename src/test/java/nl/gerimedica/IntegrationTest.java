package nl.gerimedica;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {


    private final String serverUrl = "/api/grecord";
    private final String userDir = System.getProperty("user.dir");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testEmptyGetAll(){
        //events
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);
        JsonArray jsonArray = new Gson().fromJson(response.getBody(), JsonArray.class);
        //assertions
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // The get all request without uploading any CSV should return an empty list.
        Assertions.assertThat(jsonArray.size()).isEqualTo(0);
    }


    @Test
    public void testUploadAndFullGetAll() throws IOException {
        //events
        // upload is a method for making a fake CSV file and then upload it
        this.upload(true);
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);
        JsonArray jsonArray = new Gson().fromJson(response.getBody(), JsonArray.class);
        //assertion
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Since, sampleCsvGenerate adds 4 records, we check the size
        Assertions.assertThat(jsonArray.size()).isEqualTo(4);
    }

    @Test
    void testUniqueCode() throws IOException {
        //events
        // Two uploads
        this.upload(true);
        ResponseEntity<String> response  = this.upload(true);
        // get all records saved in database
        ResponseEntity<String> getResponse = restTemplate.getForEntity(serverUrl, String.class);
        JsonArray jsonArray = new Gson().fromJson(getResponse.getBody(), JsonArray.class);
        //assertion
        // The response should be UNPROCESSABLE_ENTITY
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        // the second one should not be saved in the database.
        Assertions.assertThat(jsonArray.size()).isEqualTo(4);
    }

    @Test
    public void testGettingExistingRecordByCode() throws IOException {
        //events
        this.upload(true);
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl+"/276885007", String.class);
        JsonArray jsonArray = new Gson().fromJson(response.getBody(), JsonArray.class);

        //assertion
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(jsonArray.size()).isEqualTo(1);

    }

    @Test
    public void testGettingNonExistingRecordByCode() throws IOException {
        //events
        this.upload(true);
        // record with code 23 should not exist.
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl+"/23", String.class);
        JsonArray jsonArray = new Gson().fromJson(response.getBody(), JsonArray.class);

        //assertion
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Empty list should be returned
        Assertions.assertThat(jsonArray.size()).isEqualTo(0);
    }

    @Test
    public void testUploadAndFullGetAllWithWrongCSVTemplate() throws IOException {
        //events
        ResponseEntity<String> response = this.upload(false);
        //assertion
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<String> upload(boolean correct) throws IOException {
        Path sample_csv_file = Paths.get(userDir,"sample.csv");
        // generate CSV file
        if (correct)
            sampleCsvGenerate(sample_csv_file);
        else
            wrongSampleCSVGenerate(sample_csv_file);
        // Prepare header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        // Prepare body
        MultiValueMap<String, Object> body
                = new LinkedMultiValueMap<>();
        FileSystemResource resource = new FileSystemResource(sample_csv_file);
        body.add("file", resource);

        // Send POST request
        HttpEntity<MultiValueMap<String, Object>> requestEntity
                = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

        // Delete file
        File csvFile = sample_csv_file.toFile();
        csvFile.delete();

        // Return the response
        return response;
    }


    private void sampleCsvGenerate(Path sample_csv_file) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(sample_csv_file);
        // These entries are taken from the exercise.csv file
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT); {
            csvPrinter.printRecord("source",
                    "codeListCode",
                    "code",
                    "displayValue",
                    "longDescription",
                    "fromDate",
                    "toDate",
                    "sortingPriority");
            csvPrinter.printRecord("ZIB","ZIB002","Type 2","Als een worst, maar klonterig","","01-01-2019","","");
            csvPrinter.printRecord("ZIB","ZIB003","276885007","Kern temperatuur (invasief gemeten)","","01-01-2019","","");
            csvPrinter.printRecord("ZIB","ZIB002","Type 4","Als een worst of slang, glad en zacht","","01-01-2019","","");
            csvPrinter.printRecord("ZIB","ZIB001","271636001","Polsslag regelmatig","The long description is necessary","01-01-2019","","1");

            csvPrinter.flush();
        }
    }



    private void wrongSampleCSVGenerate(Path sample_csv_file) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(sample_csv_file);

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT); {
            csvPrinter.printRecord("source",
                    "codeListCode",
                    "code",
                    "displayValues",
                    "longDescription",
                    "fromDate",
                    "toDate");
            csvPrinter.printRecord("ZIB","ZIB002","Type 2","Als een worst, maar klonterig","","01-01-2019","","");
            csvPrinter.printRecord("ZIB","ZIB003","276885007","Kern temperatuur (invasief gemeten)","","01-01-2019","","");
            csvPrinter.printRecord("ZIB","ZIB002","Type 4","Als een worst of slang, glad en zacht","","01-01-2019","","");
            csvPrinter.printRecord("ZIB","ZIB001","271636001","Polsslag regelmatig","The long description is necessary","01-01-2019","","1");

            csvPrinter.flush();
        }
    }


    @Test
    public void testDeletedAll() throws IOException {
        //events
        // First, upload a CSV file with 4 records.
        this.upload(true);
        // Then, we delete it.
        restTemplate.delete(serverUrl);
        // Next, send a Get all request
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl, String.class);
        JsonArray jsonArray = new Gson().fromJson(response.getBody(), JsonArray.class);

        //assertion
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // There should be no record saved in the database
        Assertions.assertThat(jsonArray.size()).isEqualTo(0);
    }

}
