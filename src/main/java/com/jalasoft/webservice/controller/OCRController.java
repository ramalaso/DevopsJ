/**
 * Copyright (c) 2019 Jalasoft.
 *
 * This software is the confidential and proprietary information of Jalasoft.
 * ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */
package com.jalasoft.webservice.controller;
import com.jalasoft.webservice.model.DBManager;
import com.jalasoft.webservice.model.OCRCriteria;
import com.jalasoft.webservice.model.OCRExtractor;

import com.jalasoft.webservice.model.Response;
import com.jalasoft.webservice.utils.Checksum;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.management.Query;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *The class is an endpoit for OCR
 *
 * @author Fernando Hinojosa on 09/23/2019
 * @version v1.0
 */
@RestController
@RequestMapping ("/api/v1.0/ocr")
public class OCRController{
    /**
     *
     * @param file the parameter have the file path information
     * @param lang the parameter have the language set to recognize the text
     * @return returns a String with the text obtain and in case of error shows the error message
     * @throws IOException control the input output exception to handle file used in the method
     */
    @PostMapping
    public Response OCRExtractor (@RequestParam("file") MultipartFile file,
                                  @RequestParam(value = "checksum",defaultValue = "false")String checksum,
                                  @RequestParam(value = "lang", defaultValue = "") String lang) throws IOException,
                                  NoSuchAlgorithmException {

        String filePath = FileManager.getFilePath(file);
        Checksum checksum1 = new Checksum();
        Response test = new Response();
        DBManager db = new DBManager();
        String checksumResult = checksum1.checksum(filePath);
        String pathDb = "";
        if (checksum.equals(checksumResult)){
            if (db.getPath(checksumResult).isEmpty()){
                db.add(checksum,filePath);
            }else {
                pathDb= db.getPath(checksumResult);
            }
            OCRExtractor ocr = new OCRExtractor();
            OCRCriteria ocrCriteria = new OCRCriteria(lang,filePath);
            test = ocr.convert(ocrCriteria);
            return test;
        }
        else {
            test.setStatus(Response.Status.BadRequest);
            test.setMessage("The cheksum send is not match with checksum generated. System works with md5.");
            return test;
        }

    }
}
