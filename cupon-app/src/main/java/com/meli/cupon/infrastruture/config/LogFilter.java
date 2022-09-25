package com.meli.cupon.infrastruture.config;

import com.twitter.finagle.Service;
import com.twitter.finagle.SimpleFilter;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.Response;
import com.twitter.util.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFilter extends SimpleFilter<Request, Response> {
    private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public Future apply(Request request, Service<Request, Response> service) {
        LOG.info("Request: " + request.method() + " "+ request.uri());
        return service.apply(request);
    }
}

