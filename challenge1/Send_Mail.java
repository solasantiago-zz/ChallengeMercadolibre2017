package challenge1;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Send_Mail {
    String from_mail = "gmailusername@gmail.com";   //  Insert username
    public void sendmail(String username, String password, String user_mail){
        try {
            Properties p = new Properties ();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", from_mail);
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p,null);
            BodyPart body_message = new MimeBodyPart();
            body_message.setText("Su nombre de usuario es: " + username + "\nY su contraseña temporal: " + password);
            
            MimeMultipart m = new MimeMultipart();
            m.addBodyPart (body_message);
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom (new InternetAddress (from_mail));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(user_mail));
            mensaje.setSubject ("Datos de usuario");
            mensaje.setContent(m);
            
            Transport t = s.getTransport("smtp");
            t.connect (from_mail, "gmailpass");   //  Insert gmail password
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close ();
            System.out.println("===== Mail sent successfully =====");
        } catch (MessagingException ex) {
            Logger.getLogger(Send_Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
