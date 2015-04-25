package com.micromata.feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Handles application errors and logs them.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@Controller
public class FeedbackErrorController implements ErrorController {
  private static final Logger LOG = LoggerFactory.getLogger(FeedbackErrorController.class);

  /**
   * Path to error template.
   */
  public static final String PATH = "/error";

  @Autowired
  private ErrorAttributes errorAttributes;

  @RequestMapping(value = PATH)
  public String error(HttpServletRequest request) {
    Map<String, Object> attributes = getErrorAttributes(request, false);
    LOG.error("Error at {}: '{}'", attributes.get("path"), attributes.get("message"));
    return PATH;
  }

  @Override
  public String getErrorPath() {
    return PATH;
  }

  /**
   * Collect error values from request.
   *
   * Copied from Spring's BasicErrorController.
   *
   * @param request the HttpServletRequest of this error.
   * @param includeStackTrace true if a stack trace should be included.
   * @return a map with error attributes.
   */
  private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
    RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
  }
}
