package de.ait.tp.controllers;

import de.ait.tp.config.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
@DisplayName("Endpoint /send email is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class SupportIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @WithUserDetails(value = "romanova@gmail.com")
    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  public void supportSend_shouldSendEmail() throws Exception {
        String from = "from@example.com";
        String subject = "Test Subject";
        String text = "Test Text";
        mockMvc.perform(post("/api/sendEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "senderEmail": "from",
                                  "subject": "subject",
                                  "text": "text"
                                }"""))
                .andExpect(status().isOk());
    }
}
