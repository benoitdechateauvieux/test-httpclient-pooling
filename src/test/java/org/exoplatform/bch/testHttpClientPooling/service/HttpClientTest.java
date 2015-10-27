package org.exoplatform.bch.testHttpClientPooling.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.HashSet;
import java.util.concurrent.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

/**
 * Created by The eXo Platform SAS Author : eXoPlatform exo@exoplatform.com
 * 10/27/15
 */
public class HttpClientTest {

  @Test
  public void testConcurrentAccess() throws ExecutionException, InterruptedException {
    //Given
    int nbThreads = 2;
    ExecutorService pool = Executors.newFixedThreadPool(nbThreads);
    HashSet<Future<String>> set = new HashSet<>();
    long startTime = System.currentTimeMillis();
    //When
    for (int i=0; i<nbThreads; i++) {
      Callable<String> callable = new ServiceCallable();
      Future<String> future = pool.submit(callable);
      set.add(future);
    }
    //Then
    for (Future<String> future : set) {
      assertThat(future.get(), is(MyService.HELLO_WORLD));
    }
    assertTrue((System.currentTimeMillis() - startTime) < nbThreads * MyService.TIMER);
  }

  public static class ServiceCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
      DefaultHttpClient httpClient = new DefaultHttpClient();
      HttpGet request = new HttpGet("http://localhost:8080/testHttpClientPooling/callService/");
      HttpResponse response = httpClient.execute(request);
      InputStream is = response.getEntity().getContent();
      String result = IOUtils.toString(is, "UTF-8");
      is.close();
      return result;
    }
  }
}
