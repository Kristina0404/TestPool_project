package de.ait.tp.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-local.yml")

public class SupportMailSender {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailUsername;

    @Async
    public void supportSend(String senderEmail, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            System.out.println(mailUsername);
            helper.setFrom(senderEmail);
            helper.setTo(mailUsername);
            helper.setSubject(subject);
            helper.setText(text, true);

        } catch (MessagingException e) {
            throw new IllegalArgumentException(e);
        }

        javaMailSender.send(message);

    }
}