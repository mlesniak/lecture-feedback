package com.micromata.feedback.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Access database feedback entries.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
public interface FeedbackEntryRepository extends CrudRepository<FeedbackEntry, Long> {
  List<FeedbackEntry> findAllByOrderByCreatedAtDesc();
}
