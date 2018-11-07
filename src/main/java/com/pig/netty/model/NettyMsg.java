package com.pig.netty.model;

/**
 * @author Arthas
 * @create 2018/11/7
 */
public class NettyMsg {

    private Integer type;
    private Integer userId;


    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
}
