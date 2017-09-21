package com.capside.education;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
/**
 *
 * @author Javi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class PiCtrlApiMockIT {
    private static final String PATH = "/pi?iterations=%s";
    private static final BigDecimal PI_APROX = new BigDecimal(
        "3.14159265358979323846264338327950288419716939937" + 
        "5105820974944592307816406286208998628034825342117" + 
        "06798214808651328230664709384");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void checkValidApproximation() throws Exception {
        int iterations = 20;
        String url = String.format(PATH, iterations);
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk()
        // More about jsonpath: https://github.com/jayway/JsonPath1
        ).andExpect(MockMvcResultMatchers.jsonPath(
                "$.value", Matchers.closeTo(PI_APROX, new BigDecimal("1E-20")))
        );
    }
}
