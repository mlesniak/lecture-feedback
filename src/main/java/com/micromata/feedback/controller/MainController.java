package com.micromata.feedback.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles request to /.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class MainController {
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  @RequestMapping("/")
  public String index() {
    LOG.info("Feedback page called");
    return "index";
  }

  /**
   * Special endpoint to keep the dyno alive. In particular, no logging is done on purpose.
   */
  @RequestMapping("/ping")
  public ResponseEntity<String> ping() {
    return new ResponseEntity<>("PONG", HttpStatus.OK);
  }

  @RequestMapping("/about")
  public String about() {
    LOG.info("About page called");
    return "about";
  }
}
