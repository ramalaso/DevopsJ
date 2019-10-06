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

import com.jalasoft.webservice.utils.Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Implements Download Controller
 *
 * @author Fernando Hinojosa on 10/03/2019
 * @version v1.0
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    /**
     * This classes display the file in the browser to download the file.
     *
     */
    @GetMapping ("/file/{fileName:.+}")
    public void download(HttpServletResponse response, @PathVariable("fileName")String fileName) {
        Utils utils = new Utils();
        try {
            File file = new File(utils.getPublic());
            if (file.exists()){
                response.setContentType("application/octet-stream");
                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                FileCopyUtils.copy(inputStream,response.getOutputStream());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
