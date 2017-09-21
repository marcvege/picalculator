
package com.capside.education;

import com.jayway.jsonpath.JsonPath;
import java.math.BigDecimal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ciberado
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PiCtrlApiHttpIT {
    private static final String PATH = "/pi?iterations=%s";
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void calculatePI() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = String.format(PATH, 10);
        ResponseEntity<String> response = 
                restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        assertEquals("Successfull invocation", HttpStatus.OK,response.getStatusCode());

        String responseBody = response.getBody();        
        BigDecimal dec = JsonPath.read(responseBody, "value");

        assertNotNull("Result exists and is numeric.", dec);
    }

}
