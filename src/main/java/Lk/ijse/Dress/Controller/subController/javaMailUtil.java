package Lk.ijse.Dress.Controller.subController;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class javaMailUtil {
    public static int saveRandomNumber;



    public  void sendMail(String recepient)throws Exception{
        System.out.println("preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail="luxoradress@gmail.com";
        String password ="xfpfwejiagtmswmi";
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message=prepareMessage(session, myAccountEmail,recepient);
        Transport.send(message);
        System.out.println("msg send successful");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        Random random= new Random();
        int SaveRandomNumber;
        try {
            int min = 100000;
            int max = 999999;
            int randomNumber = random.nextInt(max - min + 1) + min;
            saveRandomNumber = randomNumber;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("LUXORA Fashion Desgin & rental Serivecs");
            message.setText("Hey there your Pin number "+ randomNumber);


            return  message;
        } catch (Exception ex) {
            Logger.getLogger(javaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }
    public int getSaveRandomNumber() {
        return saveRandomNumber;
    }


}