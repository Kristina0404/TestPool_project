package de.ait.tp.controllers;

import de.ait.tp.config.TestSecurityConfig;
import de.ait.tp.mail.SupportMailSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
@DisplayName("Endpoint /send email is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
class SupportIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private SupportMailSender supportMailSender;

    @WithUserDetails(value = "romanova@gmail.com")
    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void supportSend_shouldSendEmail() {
        // Arrange
        String from = "from@example.com";
        String to = "to@example.com";
        String subject = "Test Subject";
        String text = "Test Text";

        MimeMessage mimeMessageMock = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessageMock);

        supportMailSender.supportSend( from, to, subject, text);

        verify(javaMailSender).createMimeMessage();
        verify(javaMailSender).send(mimeMessageMock);

    }
}
