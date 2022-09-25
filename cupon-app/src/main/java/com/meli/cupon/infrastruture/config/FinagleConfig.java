package com.meli.cupon.infrastruture.config;

import com.meli.cupon.infrastruture.remote.HttpClientErrorsResponseFilter;
import com.twitter.finagle.Backoff;
import com.twitter.finagle.GlobalRequestTimeoutException;
import com.twitter.finagle.Http;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.finagle.param.HighResTimer;
import com.twitter.finagle.service.*;
import com.twitter.finagle.stats.NullStatsReceiver;
import com.twitter.finagle.util.DefaultTimer;
import com.twitter.util.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This finable config was obtained from:
 * https://medium.com/javarevisited/how-to-connect-to-a-restapi-microservice-with-twitters-finagle-client-c1a76cfb528c
 */
@Configuration
public class FinagleConfig {

//    Intencianalmente dejado como comentario
//    @Bean
//    Service<Request, Response> httpClient(
//        @Value("${server.port:8080}") int port,
//        @Value("${global-timeout:5000}") int globalTimeout,
//        @Value("${request-timeout:1000}") int requestTimeout) {
//
//        Duration timeoutDuration = Duration.fromMilliseconds(globalTimeout);
//        TimeoutFilter<Request, Response> timeoutFilter = new TimeoutFilter<>(
//            timeoutDuration,
//            new GlobalRequestTimeoutException(timeoutDuration),
//            DefaultTimer.getInstance()
//        );
//
//        var backoff = Backoff.exponentialJittered(Duration.fromMilliseconds(100), Duration.fromMilliseconds(30_000));
//        RetryExceptionsFilter<Request, Response> rt = new RetryExceptionsFilter<>(
//            RetryPolicy.backoffJava(Backoff
//                    .toJava(backoff),
//                RetryPolicy.TimeoutAndWriteExceptionsOnly()), HighResTimer.Default(), NullStatsReceiver.get());
//
//        HttpClientErrorsResponseFilter httpClientErrorsResponseFilter =
//            new HttpClientErrorsResponseFilter();
//
//        RetryBudget budget = RetryBudgets.newRetryBudget(Duration.fromMilliseconds(1000), 10, 1);
//        Http.Client client = Http.client()
//            .withRetryBudget(budget)
//            .withRetryBackoff(backoff)
//            .withRequestTimeout(Duration.fromMilliseconds(requestTimeout));
//
//        return new LogFilter()
//            .andThen(timeoutFilter)
//            .andThen(rt)
//            .andThen(httpClientErrorsResponseFilter)
//            .andThen(client.newService(":" + port));
//
//    }
}

