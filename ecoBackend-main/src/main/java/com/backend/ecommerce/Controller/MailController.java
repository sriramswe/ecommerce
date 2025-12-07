package com.backend.ecommerce.Controller;

import com.backend.ecommerce.Service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public  class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public String sendHtmlMail(@RequestParam String to,
                               @RequestParam String subject,
                               @RequestParam String message) throws MessagingException {
        mailService.sendHtmlMail(to, subject, message);
        return "✅ HTML mail sent successfully to " + to;
    }

    @PostMapping("/send-attachment")
    public String sendMailWithAttachment(@RequestParam String to,
                                         @RequestParam String subject,
                                         @RequestParam String message,
                                         @RequestParam String path) throws MessagingException {
        mailService.sendMailWithAttachment(to, subject, message, path);
        return "✅ Mail with attachment sent to " + to;
    }
}
