package com.micromata.feedback.service;

import com.micromata.feedback.persistence.FeedbackEntry;
import com.micromata.feedback.persistence.FeedbackEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Domain-logic for feedback.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Service
public class FeedbackService {
  private static final Logger LOG = LoggerFactory.getLogger(FeedbackService.class);

  @Autowired
  private FeedbackEntryRepository feedbackEntryRepository;

  @Autowired
  private EmailService emailService;

  @Value("${email}")
  private String email;

  @Value("${subject}")
  private String subject;

  /**
   * Stores a feedback entry and sends an email to the configured address.
   *
   * @param text the feedback text
   */
  public void persistAndSubmit(String text) {
    LOG.info("Persisting entry. text={}", text);
    FeedbackEntry feedbackEntry = new FeedbackEntry(text);
    feedbackEntryRepository.save(feedbackEntry);

    try {
      emailService.sendMail(
          emailService.getUsername(),
          email,
          subject,
          format(feedbackEntry)
      );
    } catch (MessagingException e) {
      LOG.warn("Unable to send email. error={}", e.getMessage(), e);
    }
  }

  /**
   * Creates the body of the email from a feedback entry.
   *
   * @param entry the feedback entry.
   * @return email body.
   */
  private String format(FeedbackEntry entry) {
    return String.format("%s<br/>%s<br/>", entry.getCreatedAt().toString(), entry.getText());
  }
}
