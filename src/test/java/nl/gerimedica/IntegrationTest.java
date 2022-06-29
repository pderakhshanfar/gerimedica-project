package nl.gerimedica;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {


    private final String serverUrl = "/api/grecord";

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

}
