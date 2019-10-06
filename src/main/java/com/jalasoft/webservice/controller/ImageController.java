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

import com.jalasoft.webservice.model.*;
import com.jalasoft.webservice.utils.Checksum;
import com.jalasoft.webservice.utils.Utils;
import org.apache.tika.exception.TikaException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;

/**
 * Implements ImageController with the Imageconvert classes
 * @author Fernando Hinojosa on 09/24/2019
 * @version v1.0
 */
@RestController
@RequestMapping("/api/v1.0/image")
public class ImageController {

    /**
     * Implements the Convert classes
     * @param file the parameter have the file path information
     * @param dpi the parameter have the dpi information
     * @param ext the parameter have the ext information
     * @return
     * @throws IOException
     */
        @PostMapping
        public Response Convert (@RequestParam("file") MultipartFile file,
                                 @RequestParam(value = "metadata",defaultValue = "false")boolean metadata,
                                 @RequestParam(value = "checksum",defaultValue = "false")String checksum,
                                 @RequestParam (value = "dpi", defaultValue = "") int dpi,
                                 @RequestParam(value = "ext", defaultValue = "") String ext) throws IOException,
                                 NoSuchAlgorithmException, TikaException, SAXException {
            String filePath = FileManager.getFilePath(file);
            Checksum checksum1 = new Checksum();
            Response response = new Response();
            DBManager db = new DBManager();
            String checksumResult = checksum1.checksum(filePath);
            ImageConvert img = new ImageConvert();
            ImageCriteria imageCriteria = new ImageCriteria();
            Utils utils = new Utils();
            if (metadata){
                MetadataFileCreator metadataF =  new MetadataFileCreator();
                metadataF.getMetada(filePath);
            }
            String pathDb = "";
            if (checksum.equals(checksumResult)){
                if (db.getPath(checksumResult).isEmpty()){
                    db.add(checksum,filePath);
                }else {
                    pathDb= db.getPath(checksumResult);
                }
                imageCriteria.setFilePath(filePath);
                imageCriteria.setDpi(dpi);
                imageCriteria.setDestinationPath(utils.getPublic());
                imageCriteria.setExtension(ext);
            }
            else {
                response.setStatus(Response.Status.BadRequest);
                response.setMessage("The cheksum send is not match with checksum generated. System works with md5.");
                return response;
            }
            response = img.convert(imageCriteria);
            return response;
        }
}
