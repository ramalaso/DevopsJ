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

import com.jalasoft.webservice.db.QueryManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The class implements methods to convert PDF files into image files
 *
 * @author Andy Bazualdo on 9/23/19.
 * @version v1.0
 */
public class ImageConvert implements IConvert{

    /**
     *  Creates the Controller Model using Tesseract with the wrapper Tess4J.
     *
     */
    public Response convert(Criteria criteria) throws IOException {
        Response res = new Response();
        ImageCriteria imgCriteria = (ImageCriteria) criteria;


        try {
            String source = imgCriteria.getFilePath();
            String destination = imgCriteria.getDestinationPath();
            int dpi = imgCriteria.getDpi();
            String ext = imgCriteria.getExtension();

            //Loading an existing PDF document
            File file = new File(source);
            PDDocument document = PDDocument.load(file);

            //Instantiating the PDFRenderer class
            PDFRenderer renderer = new PDFRenderer(document);
            int count = document.getNumberOfPages();
            String [] filePaths = new String[count];
            for (int page = 0; page < count; ++page) {

                BufferedImage img = renderer.renderImageWithDPI(page, dpi, ImageType.RGB);
                String fileName = destination + page + "." + ext;
                ImageIOUtil.writeImage(img, fileName, 300);
                filePaths[page]=fileName;
            }
            document.close();
            ZipFiles zipFiles = new ZipFiles();
            zipFiles.zipFiles(filePaths);

            res.setStatus(Response.Status.Ok);
            res.setUrl("0.zip");
            return res;
        }   catch (Exception e) {
            res.setStatus(Response.Status.BadRequest);
            return res;

        }
    }
}
