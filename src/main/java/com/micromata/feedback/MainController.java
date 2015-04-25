package com.micromata.feedback;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles request to /.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class MainController {
  @RequestMapping("/")
  public String index() {
    return "index";
  }
}
