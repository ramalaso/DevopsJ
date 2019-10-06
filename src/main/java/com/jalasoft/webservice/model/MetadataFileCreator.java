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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import com.google.gson.Gson;
import org.xml.sax.SAXException;

/**
 * The class generates a json file
 * @author Andy Bazualdo on 9/23/19.
 * @version v1.0
 */
public class MetadataFileCreator {
    /**
     * The method generates a json file based a path file
     * @throws IOException trows input output exceptions
     * @throws TikaException trows Tika exceptions
     * @throws SAXException trows SAX exceptions
     */
    public void getMetada(String filepath) throws IOException, TikaException, SAXException{
        //Assume that boy.jpg is in your current directory
        File file = new File(filepath);
        //Parser method parameters
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputStream = new FileInputStream(file);
        ParseContext context = new ParseContext();
        parser.parse(inputStream, handler, metadata, context);
        System.out.println(handler.toString());
        //getting the list of all meta data elements
        String[] metadataNames = metadata.names();
        Gson gson = new Gson();
        //convert java object to JSON format
        String json = gson.toJson(metadata);

        try {
            //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter(filepath+".json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
