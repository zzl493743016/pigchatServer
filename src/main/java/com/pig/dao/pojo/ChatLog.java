package com.pig.dao.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ChatLog implements Serializable {
    /**
     * 记录ID
     */
    private Integer id;

    /**
     * 接收者用户ID
     */
    private Integer recieverId;

    /**
     * 发送者用户ID
     */
    private Integer senderId;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 时间
     */
    private Date createTime;

    /**
     * 是否已经接收
     */
    private Integer isRecieve;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Integer recieverId) {
        this.recieverId = recieverId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsRecieve() {
        return isRecieve;
    }

    public void setIsRecieve(Integer isRecieve) {
        this.isRecieve = isRecieve;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChatLog other = (ChatLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRecieverId() == null ? other.getRecieverId() == null : this.getRecieverId().equals(other.getRecieverId()))
            && (this.getSenderId() == null ? other.getSenderId() == null : this.getSenderId().equals(other.getSenderId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsRecieve() == null ? other.getIsRecieve() == null : this.getIsRecieve().equals(other.getIsRecieve()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRecieverId() == null) ? 0 : getRecieverId().hashCode());
        result = prime * result + ((getSenderId() == null) ? 0 : getSenderId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsRecieve() == null) ? 0 : getIsRecieve().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", recieverId=").append(recieverId);
        sb.append(", senderId=").append(senderId);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", isRecieve=").append(isRecieve);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}