package nl.hsleiden.gamecenter.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailService {

    @Value("${email_address}")
    private String email;

    @Value("${email_password}")
    private String password;

    private void sendEmail(String recipientEmail, String messageSubject, String messageText) {
        Properties properties = createProperties();
        Session session = createSession(properties, email, password);

        try {
            Transport.send(createMessage(session, recipientEmail, messageSubject, messageText));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties createProperties() {

        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        return properties;
    }

    private Session createSession(Properties properties, String email, String password) {
        Session session = Session.getInstance(
                properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        return session;
    }

    private Message createMessage(Session session, String recipientEmail, String messageSubject, String messageText) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@gmail.com"));

        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail)
        );
        message.setSubject(messageSubject);
        message.setText("Beste gebruiker, \n\n" + messageText + "\n\n" + "Met vriendelijke groet,\n" + "Het Spine-team");

        return message;
    }
}