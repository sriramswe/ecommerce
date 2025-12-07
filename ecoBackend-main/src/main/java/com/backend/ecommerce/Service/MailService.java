package com.backend.ecommerce.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.SocketOption;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    public void sendHtmlMail(String to , String subject,String htmlContent) throws  MessagingException{
         MimeMessage message = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message , true);
          helper.setFrom("sris70440@gmail.com");
          helper.setTo(to);
          helper.setSubject(subject);
          helper.setText(htmlContent,true);
          javaMailSender.send(message);
    }
    public void sendMailWithAttachment(String to , String subject,String htmlContent,String attachmentPath)throws  MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("sris70440@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent,true);

        FileSystemResource file = new FileSystemResource(new File(attachmentPath));
        if(file.exists()){
            helper.addAttachment(file.getFilename(),file);

        }
else {
            System.out.println("Attachment file Not found:"+attachmentPath);

        }
javaMailSender.send(message);
    }
}
