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
import java.security.NoSuchAlgorithmException;

/**
 *The class is an endpoint for VideoConverter
 *
 * @author Isaac Vargas on 09/19/2019
 * @version v1.0
 */
@RestController
@RequestMapping("/api/v1.0/videoConv")
public class VideoController {
    /**
     * Converts to another type of video
     * @param file has the file to be converted in another type
     * @param nameFile has the nameFile of the output
     * @param codec has the codec to be converted in another type
     * @param bitRate has the bitrate to be converted in another type
     * @param channels has the channels to be converted in another type
     * @param samplingRate has the samplingrate to be converted in another type
     * @param videoCodec has the videocodec to be converted in another type
     * @param videoBitRate has the videobitrate to be converted in another type
     * @param frameRate has the framerate to be converted in another type
     * @param size1 has the first size to be converted in another type
     * @param size2 has the second size to be converted in another type
     * @param format has the format of the output
     * @return type requested of video
     * @throws IOException throws the input/output exceptions
     */
    @PostMapping
    public Response convert (@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "metadata",defaultValue = "false")boolean metadata,
                             @RequestParam(value = "checksum",defaultValue = "false")String checksum,
                             @RequestParam("nameFile") String nameFile,
                             @RequestParam(value = "codec", defaultValue = "libmp3lame") String codec,
                             @RequestParam(value = "bitRate", defaultValue = "64000") int bitRate,
                             @RequestParam(value = "channels", defaultValue = "1") int channels,
                             @RequestParam(value = "samplingRate", defaultValue = "22050") int samplingRate,
                             @RequestParam(value = "videoCodec", defaultValue = "h264") String videoCodec,
                             @RequestParam(value = "videoBitRate", defaultValue = "160000") int videoBitRate,
                             @RequestParam(value = "frameRate", defaultValue = "24") int frameRate,
                             @RequestParam(value = "sizeX", defaultValue = "400") int size1,
                             @RequestParam(value = "sizeY", defaultValue = "300") int size2,
                             @RequestParam(value = "format", defaultValue = "mp4") String format)
                             throws IOException, TikaException, SAXException, NoSuchAlgorithmException {
        String filePath = FileManager.getFilePath(file);
        Checksum checksum1 = new Checksum();
        Response response = new Response();
        DBManager db = new DBManager();
        String checksumResult = checksum1.checksum(filePath);
        VideoCriteria cri = new VideoCriteria();
        Utils utils = new Utils();
        String fileTarget = utils.getPublic() + nameFile + "." + format ;
        IConvert video = new VideoConvert();

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
            cri.setFilePath(filePath);
            cri.setTarget(fileTarget);
            cri.setCodec(codec);
            cri.setBitRate(bitRate);
            cri.setChannels(channels);
            cri.setSamplingRate(samplingRate);
            cri.setVideoCodec(videoCodec);
            cri.setBitRate(videoBitRate);
            cri.setFrameRate(frameRate);
            cri.setSize1(size1);
            cri.setSize2(size2);
            cri.setFormat(format);
        }
        else {
            response.setStatus(Response.Status.BadRequest);
            response.setMessage("The cheksum send is not match with checksum generated. System works with md5.");
            return response;
        }
        return video.convert(cri);
    }
}
