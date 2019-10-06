/**
 * Copyright (c) 2019 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */
package com.jalasoft.webservice.model;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Implements Responses for implementing response the status of the controller and methods.
 *
 * @author Raul Laredo
 * @version v1.0
 */
public class Response {

    private String url;
    private Status status;
    private String message;
    /**
     * Get Status  for response
     * @param
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set Status  for response
     * @param
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Get URl  for response
     * @param
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set URl  for response
     * @param
     */
    public void setUrl(String nameFile) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path("file/").path(nameFile).toUriString();
        this.url = uri;
    }

    /**
     * Get message  for response
     * @param
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message  for response
     * @param
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Status for responding
     */
    public enum Status{
        Ok,
        Created,
        NoContent,
        BadRequest,
        Forbidden,
        NotFound,
        MethodNotAllowed,
        Conflict,
        InternalServerError
    }

}




