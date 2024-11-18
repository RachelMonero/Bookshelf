package com.bookshelf.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class JavaEmailService {

    // SMTP server configuration
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String EMAIL = "mealmate09@gmail.com";
    private static final String PASSWORD = "mwjc lmpu zadd hqlr";

    public void sendVerificationEmail(String recipientEmail, String verificationLink) throws MessagingException {
        System.out.println("Attempting to send verification email to: " + recipientEmail);
        // Set up properties for the SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Verify Your Account");
            message.setText("Click the following link to verify your account: " + verificationLink);

            System.out.println("SMTP session and message are successfully set up. Attempting to send the email...");

            // Send the email
            Transport.send(message);
            System.out.println("Verification email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Failed to send verification email to: " + recipientEmail);
            e.printStackTrace();  // Print detailed error to troubleshoot
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

}
