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

/**
 * Implements the criteria needs for convert the video in other format.
 *
 * @author Raul Laredo
 * @version v1.0
 */
public class VideoCriteria extends Criteria {

     private String target;
     private String codec;
     private int bitRate;
     private int channels;
     private int samplingRate;
     private int frameRate;
     private String videoCodec;
     private int size1;
     private int size2;
     private String format;

    /**
     * Set target file
     * @param target file
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Get target file
     * @return string target
     */
    public String getTarget() {
        return target;
    }

    /**
     *Get codec for conversion
     * @return codec
     */
    public String getCodec() {
        return codec;
    }

    /**
     * Set codec for conversion
     * @param codec
     */
    public void setCodec(String codec) {
        this.codec = codec;
    }

    /**
     * Get bit rate for conversion
     * @return bitRate
     */
    public int getBitRate() {
        return bitRate;
    }

    /**
     * Set bit rate for conversion
     * @param bitRate
     */
    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    /**
     * Get channels for video conversion
     * @return channels
     */
    public int getChannels() {
        return channels;
    }

    /**
     * Set channels for video conversion
     * @param channels
     */
    public void setChannels(int channels) {
        this.channels = channels;
    }

    /**
     * Get Sampling Rate for video conversion
     * @return samplingRate
     */
    public int getSamplingRate() {
        return samplingRate;
    }

    /**
     * Set Sampling Rate for video conversion
     * @param samplingRate
     */
    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    /**
     * Get frame rate for video conversion
     * @return frameRate
     */
    public int getFrameRate() {
        return frameRate;
    }

    /**
     * Set frame rate for video conversion
     * @param frameRate
     */
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    /**
     * Get Size X for video conversion
     * @return size1
     */
    public int getSize1() {
        return size1;
    }

    /**
     * Set Size X for video conversion
     * @param size1
     */
    public void setSize1(int size1) {
        this.size1 = size1;
    }

    /**
     * Get Size Y for video conversion
     * @return size2
     */
    public int getSize2() {
        return size2;
    }

    /**
     * Set Size Y for video conversion
     * @param size2
     */
    public void setSize2(int size2) {
        this.size2 = size2;
    }

    /**
     * Get format for video conversion
     * @return format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Set format for video conversion
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public void setVideoCodec(String videoCodec) {
        this.videoCodec = videoCodec;
    }
}
