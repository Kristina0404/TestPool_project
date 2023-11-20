package de.ait.tp.mail;

import de.ait.tp.models.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MailTemplatesUtil {
    private final Configuration freemarkerConfiguration;

    public String createConfirmationMail(User user, String link) {
        String html;
        try {
            Template template = freemarkerConfiguration.getTemplate("confirm_registration_mail.ftlh");
            Map<String, Object> model = new HashMap<>();
            model.put("firstName", user.getFirstName());
            model.put("lastName", user.getLastName());
            model.put("link", link);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}

