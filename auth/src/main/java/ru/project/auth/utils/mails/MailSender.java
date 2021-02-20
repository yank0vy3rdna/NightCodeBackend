package ru.project.auth.utils.mails;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class MailSender {

    private final JavaMailSender mailSender;
    private final String activateMessage = "Follow this link to activate your account! http://localhost:8080/auth/confirm/";

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        log.info(() -> "Mail Service load");
    }

    public void sendActivateLink(String emailTo, String subject, String message){
        System.out.println(username);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(emailTo);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(activateMessage + message );
        mailSender.send(simpleMailMessage);
    }
}
