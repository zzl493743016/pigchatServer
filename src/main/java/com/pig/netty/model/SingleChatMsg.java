package com.pig.netty.model;

/**
 * @author Arthas
 * @create 2018/11/7
 */
public class SingleChatMsg extends NettyMsg {

    private String content;

    private Integer receiverUserId;

    public Integer getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Integer receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
