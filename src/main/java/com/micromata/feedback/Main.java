package com.micromata.feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Main application class.
 *
 * @author Michael Lesniak (mlesniak@micromata.de)
 */
@SpringBootApplication
public class Main {
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  @PostConstruct
  public void started() {
    LOG.info("Application started");
  }
}
