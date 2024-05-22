package com.nniett.kikaishin.app.web.controller.construction;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public interface UsesHttpServletRequest {
    Logger logger = LoggerFactory.getLogger(UsesHttpServletRequest.class);
    default HttpServletRequest getHttpServletRequest() {
        logger.debug("Requesting HttpServletRequest.");
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
