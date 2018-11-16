package com.pig.netty.handler;

import com.pig.constant.NettyMsgConst;
import com.pig.netty.UserChannelCache;
import com.pig.netty.model.NettyMsg;
import com.pig.netty.model.RegisterMsg;
import com.pig.netty.model.SingleChatMsg;
import com.pig.utils.JsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import springfox.documentation.spring.web.json.Json;

/**
 * @author Arthas
 * @create 2018/10/31
 */
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);

    @Autowired
    private UserChannelCache userChannelCache;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        userChannelCache.removeByChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.info("channel发生异常", cause);
        userChannelCache.removeByChannel(ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().read();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 消息处理
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg) throws Exception {

        System.out.println(msg.text());
        /*
        logger.info("接受到的数据:" + frame.text());
        NettyMsg msg = JsonUtil.strToObj(frame.text(), NettyMsg.class);
        Channel curChannel = channelHandlerContext.channel();
        if (NettyMsgConst.MSG_TYPE.REGISTER.getType().equals(msg.getType())) {
            // 注册
            RegisterMsg registerMsg = JsonUtil.strToObj(frame.text(), RegisterMsg.class);
            if (!ObjectUtils.isEmpty(registerMsg)) {
                // 保存用户与channel的关系
                userChannelCache.add(registerMsg.getUserId(), curChannel);
            }
        } else if (NettyMsgConst.MSG_TYPE.SINGLE_CHAT.getType().equals(msg.getType())) {
            // todo 单聊
            SingleChatMsg singleChatMsg = JsonUtil.strToObj(frame.text(), SingleChatMsg.class);
            if (!ObjectUtils.isEmpty(singleChatMsg)) {
                Channel receiverChannel = userChannelCache.getChannelByUserId(singleChatMsg.getReceiverUserId());
                if (!ObjectUtils.isEmpty(receiverChannel)) {
                    // 找到在线的用户，发送消息
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(singleChatMsg.getContent()));
                } else {
                    // 发送离线消息（推送）
                }
            }
        } else if (NettyMsgConst.MSG_TYPE.SIGNED.getType().equals(msg.getType())) {
            // todo 签收消息
        }
        */

    }
}
