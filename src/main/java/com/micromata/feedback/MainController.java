package com.micromata.feedback;

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
 * Handles request to /.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class MainController {
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @Autowired
  private FeedbackEntryRepository feedbackEntryRepository;

  @Autowired
  private FeedbackService feedbackService;

  @RequestMapping("/")
  public String index() {
    LOG.info("Feedback page called");
    return "index";
  }

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
  public ResponseEntity<Iterable<String>> list(
      @RequestParam("token") String token,
      @Value("${token}") String expectedToken) {
    if (StringUtils.pathEquals(token, expectedToken) == false) {
      LOG.warn("Illegal token submitted");
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    List<String> messages = new LinkedList<>();
    for (FeedbackEntry entry : feedbackEntryRepository.findAll()) {
      messages.add(entry.getCreatedAt().toString() + " | " + entry.getText());
    }
    return new ResponseEntity<>(messages, HttpStatus.OK);
  }

}
