package com.micromata.feedback.controller;

import com.micromata.feedback.persistence.FeedbackEntry;
import com.micromata.feedback.persistence.FeedbackEntryRepository;
import com.micromata.feedback.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles all feedback-related requests.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
  private static final Logger LOG = LoggerFactory.getLogger(FeedbackController.class);

  @Autowired
  private FeedbackEntryRepository feedbackEntryRepository;

  @Autowired
  private FeedbackService feedbackService;

  @RequestMapping(value = "/submit", method = RequestMethod.POST)
  public ModelAndView submit(@RequestParam("text") String text) {
    LOG.info("Feedback text='{}'", text);
    ModelAndView mav = new ModelAndView("index");
    mav.addObject("submit", true);
    feedbackService.persistAndSubmit(text);
    return mav;
  }

  @ResponseBody
  @RequestMapping(value = "/list")
  public ResponseEntity<Iterable<String[]>> list(
      @RequestParam("token") String token,
      @Value("${token}") String expectedToken) {
    if (StringUtils.pathEquals(token, expectedToken) == false) {
      LOG.warn("Illegal token submitted");
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    List<String[]> messages = new LinkedList<>();
    for (FeedbackEntry entry : feedbackEntryRepository.findAllByOrderByCreatedAtDesc()) {
      String[] row = new String[2];
      row[0] = entry.getCreatedAt().toString();
      row[1] = entry.getText();
      messages.add(row);
    }
    return new ResponseEntity<>(messages, HttpStatus.OK);
  }
}
