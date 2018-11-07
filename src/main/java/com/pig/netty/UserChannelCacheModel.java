package com.pig.netty;

import java.io.Serializable;

/**
 * @author Arthas
 * @create 2018/11/7
 */
public class UserChannelCacheModel implements Serializable {

    private String channelId;
    private String ip;
    private Integer port;


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
