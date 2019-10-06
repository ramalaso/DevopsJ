/**
 * Copyright (c) 2019 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */
package com.jalasoft.webservice.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The class generates checksum code for files
 * @author Andy Bazualdo on 9/23/19.
 * @version v1.0
 */
public class Checksum {

    /**
     * The method return a checksum string based on a file path
     * @param filepath file send to generate checksum
     * @return returns a string with generate checksum
     * @throws IOException exception controls Input output errors
     * @throws NoSuchAlgorithmException exception handler no such algorithm exceptions
     */
    public String checksum(String filepath) throws IOException, NoSuchAlgorithmException {
        // file hashing with DigestInputStream
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }
        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();

    }
}
