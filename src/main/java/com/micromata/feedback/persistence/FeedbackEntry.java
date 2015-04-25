package com.micromata.feedback.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Stores data and text of a single feedback.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Entity
public class FeedbackEntry {
  @Id
  @GeneratedValue
  private Long id;

  private Date createdAt;

  @Column(length = 16384)
  private String text;

  protected FeedbackEntry() {
    // Necessary for spring-jpa.
  }

  /**
   * Creates a new entry where the creation-date is automatically set.
   *
   * @param text the feedback text.
   */
  public FeedbackEntry(String text) {
    this.text = text;
    createdAt = new Date();
  }

  public Long getId() {
    return id;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "FeedbackEntry{" +
        "id=" + id +
        ", createdAt=" + createdAt +
        ", text='" + text + '\'' +
        '}';
  }
}
