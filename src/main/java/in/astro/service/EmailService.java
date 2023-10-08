package in.astro.service;

import in.astro.dao.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

    public boolean sendEmail(String subject,String message,String to){

        boolean flag = false;

        String from = "cp792002@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties() ;
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,"mufv npwn xrrl uyys");
            }
        });

        session.setDebug(true);
        MimeMessage m = new MimeMessage(session);
        try {
            m.setFrom(from);
            m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            m.setSubject(subject);
//            m.setText(message);
            m.setContent(message,"text/html");
            Transport.send(m);
            flag =true;
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
}
