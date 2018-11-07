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
        logger.info("添加channel");
        userChannelCache.addChannel(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        logger.info("移除channel");
        userChannelCache.removeChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.info("channel发生错误", cause);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame frame) throws Exception {
        logger.info("接受到的数据:" + frame.text());
        NettyMsg msg = JsonUtil.strToObj(frame.text(), NettyMsg.class);
        Channel curChannel = channelHandlerContext.channel();
        if (NettyMsgConst.MSG_TYPE.REGISTER.getType().equals(msg.getType())) {
            // 注册
            RegisterMsg registerMsg = JsonUtil.strToObj(frame.text(), RegisterMsg.class);
            // 保存用户与channel的关系
            userChannelCache.saveRelation(registerMsg.getUserId(), curChannel.id().asLongText());
            logger.info(registerMsg.toString());
        } else if (NettyMsgConst.MSG_TYPE.SINGLE_CHAT.getType().equals(msg.getType())) {
            // todo 单聊
            SingleChatMsg singleChatMsg = JsonUtil.strToObj(frame.text(), SingleChatMsg.class);
            Channel receiverChannel = userChannelCache.getChannelByUserId(singleChatMsg.getReceiverUserId());
            receiverChannel.writeAndFlush(new TextWebSocketFrame(singleChatMsg.getContent()));
            logger.info(singleChatMsg.toString());
        } else if (NettyMsgConst.MSG_TYPE.HEART_BEAT.getType().equals(msg.getType())) {
            // todo 心跳
        }

    }
}
