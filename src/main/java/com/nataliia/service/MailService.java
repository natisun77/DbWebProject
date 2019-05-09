package com.nataliia.service;

import com.nataliia.utils.RandomHelper;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService {
    private static final Logger logger = Logger.getLogger(MailService.class);

    public String sendMailWithCode(String userEmail) {
        final String username = "khmel.test.email@gmail.com";
        final String password = "khmelovska1%";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("khmel.test.email@gmail.com"));
//            message.setRecipients(
//                    Message.RecipientType.TO,
//                    InternetAddress.parse(userEmail)
//            );

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

            message.setSubject("Одноразовый код подтверждения покупки");
            String randomCode = RandomHelper.getRandomCode();
            message.setText("Ваш код для подтверждения покупки равен " + randomCode);

            Transport.send(message);

            logger.info("Done");
            return randomCode;

        } catch (MessagingException e) {
            logger.error("Can't send message", e);
            return "";
        }
    }
}
