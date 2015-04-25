package com.micromata.feedback.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  @RequestMapping("/about")
  public String about() {
    LOG.info("About page called");
    return "about";
  }
}
