package com.micromata.feedback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * An injectiable service which allows the sending of emails.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Service
public class EmailService {
  private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

  @Value("${email.host}")
  protected String server;

  @Value("${email.port}")
  protected String port;

  @Value("${email.username}")
  protected String username;

  @Value("${email.password}")
  protected String password;

  @Autowired
  private Environment environment;

  /**
   * Sends an email.
   *
   * @param from    the sender
   * @param to      the receiver
   * @param subject the subject
   * @param text    the email text (text/html)
   */
  public void sendMail(String from, String to, String subject, String text) throws MessagingException {
    if (environment.acceptsProfiles("dev")) {
      LOG.warn("Profile 'dev' is activated. Email NOT send.");
    } else {
      internalSendMail(from, to, subject, text);
    }

    LOG.info("Mail send successfully. to:{}, subject:{}", to, subject);
  }

  /**
   * Send email without caring for exceptions.
   *
   * @param from    the sender
   * @param to      the receiver
   * @param subject the subject
   * @param text    the email text (text/html)
   * @throws MessagingException thrown if an error happened while sending an email.
   */
  private void internalSendMail(final String from, final String to, final String subject, final String text) throws MessagingException {
    // Setup mail server properties.
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", server);
    properties.setProperty("mail.smtp.port", port);

    // See http://www.mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.socketFactory.port", "465");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    // Get the default Session object.
    Session session = Session.getDefaultInstance(properties,
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    // Create a default MimeMessage object.
    MimeMessage message = new MimeMessage(session);

    // Set email meta data.
    message.setFrom(new InternetAddress(from));
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(subject);

    // Allow arbitrary email lengths.
    message.setContent(text, "text/html");

    // Send message
    Transport.send(message);
  }

  public String getUsername() {
    return username;
  }
}
