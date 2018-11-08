package com.pig.netty.handler;

import com.pig.netty.UserChannelCache;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Arthas
 * @create 2018/11/7
 */
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Autowired
    private UserChannelCache userChannelCache;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.warn("channel读空闲");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                logger.warn("channel写空闲");
            } else if (event.state() == IdleState.ALL_IDLE) {
                logger.warn("channel完全空闲");
                userChannelCache.removeByChannel(ctx.channel());
            }
        }

    }
}
