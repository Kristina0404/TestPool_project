package de.ait.tp.controllers;

import de.ait.tp.controllers.api.SupportApi;
import de.ait.tp.dto.SupportEmailRequest;
import de.ait.tp.mail.SupportMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SupportController implements SupportApi {

    private final SupportMailSender supportMailSender;

    public String sendSupportEmail(@RequestBody SupportEmailRequest emailRequest) {
        Authentication updateAuthentication = SecurityContextHolder.getContext().getAuthentication();
        String senderEmail = updateAuthentication.getName();
        supportMailSender.supportSend(senderEmail, emailRequest.getSubject(), emailRequest.getText());
        return "Email sent successfully!";
    }
}

