package com.pig.constant;

/**
 * @author Arthas
 * @create 2018/11/7
 */
public class NettyMsgConst {

    public enum MSG_TYPE {
        //
        REGISTER(1, "注册"),
        //
        SINGLE_CHAT(2, "单聊"),
        //
        HEART_BEAT(3, "心跳");

        private Integer type;
        private String desc;

        MSG_TYPE(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public Integer getType() {
            return type;
        }
    }
}
