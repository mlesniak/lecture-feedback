package com.micromata.feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

  @RequestMapping(value = "/submit", method = RequestMethod.POST)
  public ModelAndView submit(@RequestParam("text") String text) {
    LOG.info("Feedback text='{}'", text);
    ModelAndView mav = new ModelAndView("index");
    mav.addObject("submit", true);
    return mav;
  }
}
