package org.exoplatform.bch.testHttpClientPooling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 * exo@exoplatform.com
 * 10/27/15
 */
@Controller
public class MyService {
  private static final Logger LOGGER = LoggerFactory.getLogger(MyClient.class);
  public static final int TIMER = 2 * 1000;
  public static final String HELLO_WORLD = "Hello World!";

  @RequestMapping(value = "/sayHello", method = RequestMethod.GET)
  public @ResponseBody String sayHelloSlowly() {
    LOGGER.info("Start sayHelloSlowly");
    try {
      Thread.sleep(TIMER);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    LOGGER.info("End sayHelloSlowly");
    return HELLO_WORLD;
  }
}
