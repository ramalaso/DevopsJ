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

import java.io.File;
import java.io.IOException;

import com.jalasoft.webservice.db.QueryManager;
import com.jalasoft.webservice.utils.Utils;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Tesseract;

/**
 * The class implements methods to convert PDF files into image files, Creates the Controller Model using Tesseract
 * with the wrapper Tess4J.
 *
 * @author Andy Bazualdo on 9/23/19.
 * @version v1.0
 */
public class OCRExtractor implements IConvert {
    private OCRCriteria ocrCriteria;

    /**
     *
     * @param criteria the object have the parameters to start the method execution
     * @return returns the result of the method and error message in case of error
     * @throws IOException get input/output exception to read files
     */
    public Response convert(Criteria criteria) throws IOException {
        ocrCriteria = (OCRCriteria) criteria;
        ITesseract tesseract = new Tesseract();
        Utils fileManager = new Utils();
        tesseract.setLanguage(ocrCriteria.getLang());
        tesseract.setDatapath(fileManager.getThirdParty()+"/Tess4J/tessdata/");
        File imageFile = new File(ocrCriteria.getFilePath());

        Response res = new Response();

        try {
            String result = tesseract.doOCR(imageFile);
            res.setStatus(Response.Status.Ok);
            res.setMessage(result);
            return res;
        } catch (TesseractException e){
            res.setStatus(Response.Status.BadRequest);
            return res;

        }
    }
}
