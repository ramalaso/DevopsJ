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

import com.jalasoft.webservice.db.DBConnection;
import com.jalasoft.webservice.db.QueryManager;
import ws.schild.jave.*;

import javax.management.Query;
import java.io.File;
import java.io.IOException;

/**
 * Implements the video convert implementing IConvert for using in the conversion.
 *
 * @author Raul Laredo
 * @version v1.0
 */
public class AudioConvert implements IConvert {

    /**
     * Converts the data video data type in another type using the criterias
     *
     * @param criteria has the params of the convert method
     * @return the video transformed in another video data type
     * @throws IOException
     */
    @Override
    public Response convert(Criteria criteria) throws IOException {
        Response res = new Response();

        try {
            AudioCriteria audiocri = (AudioCriteria) criteria;

            File source = new File(audiocri.getFilePath()) ;
            File target = new File(audiocri.getTarget());

            //Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec(audiocri.getCodec());
            audio.setBitRate(audiocri.getBitRate());
            audio.setChannels(audiocri.getChannels());
            audio.setSamplingRate(audiocri.getSamplingRate());

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat(audiocri.getFormat());
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);

            res.setStatus(Response.Status.Ok);
            res.setMessage("Audio conversion succesfully.");
            res.setUrl(source.getName());
            ZipFiles zipFiles = new ZipFiles();
            String [] filePaths = new String[5];
            filePaths[0]=audiocri.getTarget();
            zipFiles.zipFiles(filePaths);

            return res;
        }    catch (Exception e) {
            res.setStatus(Response.Status.BadRequest);
            return res;
        }
    }
}
