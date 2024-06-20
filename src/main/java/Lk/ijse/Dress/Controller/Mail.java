package Lk.ijse.Dress.Controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mail {
    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "luxoradress@gmail.com";
        String password = "xfpfwejiagtmswmi";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recepient);
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("LUXORA Fashion Design & Rental Services");
            message.setText("Subject: Welcome to LUXORA Fashion!\n" +
                    "\n" +
                    "Dear Sir/Madam ,\n" +
                    "\n" +
                    "We are thrilled to welcome you to LUXORA Fashion, your destination for exquisite fashion and personalized service.\n" +
                    "\n" +
                    "At LUXORA, we are dedicated to providing you with the latest trends, impeccable quality, and unparalleled customer experience. As a new member of our community, you are now part of a passionate and fashionable family.\n" +
                    "\n" +
                    "Whether you're looking for the perfect outfit for a special occasion or seeking everyday essentials that make a statement, our curated collections have something for everyone.\n" +
                    "\n" +
                    "We invite you to explore our website and discover the world of LUXORA Fashion. If you have any questions or need assistance, our dedicated team is here to help you every step of the way.\n" +
                    "\n" +
                    "Thank you for choosing LUXORA Fashion. We look forward to helping you express your unique style and making your fashion dreams a reality.\n" +
                    "\n" +
                    "Warm regards,\n" +
                    "\n" +
                    "[pasindu priyanjana]\n" +
                    "[Customer Experience Specialist\n]\n" +
                    "LUXORA Fashion\n");

            return message;
        } catch (Exception ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
