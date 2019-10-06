package com.jalasoft.webservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerController {

    Logger logger = LoggerFactory.getLogger(LoggerController.class);

    @RequestMapping("/logger")
    public String index() {
        logger.trace("This is a TRACE message.");
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.warn("This is a WARN message.");
        logger.error("You guessed it, an ERROR message.");

        return "Welcome to Spring Logging! Check the console to see the log messages.";
    }
}