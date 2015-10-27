package org.exoplatform.bch.testHttpClientPooling.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
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
public class MyClient {
  private final DefaultHttpClient httpClient;
  private static final Logger LOGGER = LoggerFactory.getLogger(MyClient.class);

  public MyClient() {
    httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
    httpClient.getCredentialsProvider().setCredentials(
        new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
        new UsernamePasswordCredentials("test", "test"));
  }

  @RequestMapping(value = "/callService", method = RequestMethod.GET)
  public @ResponseBody String sayHelloSlowly() throws IOException {
    LOGGER.info("Start callService");
    HttpGet request = new HttpGet("http://localhost:8080/testHttpClientPooling/sayHello/");
    HttpResponse response = httpClient.execute(request);
    String result = EntityUtils.toString(response.getEntity());
    LOGGER.info("End callService");
    return result;
  }
}
