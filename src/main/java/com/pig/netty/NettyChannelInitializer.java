package com.pig.netty;

import com.pig.netty.handler.ChatHandler;
import com.pig.netty.handler.HeartBeatHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Arthas
 * @create 2018/10/31
 */
@Component
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ChatHandler chatHandler;
    @Autowired
    private HeartBeatHandler heartBeatHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        // websocket基于http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对于大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // 心跳检测
        pipeline.addLast(new IdleStateHandler(10, 20, 30, TimeUnit.SECONDS));
        // 自定义handler对于不同的空闲类型进行相应处理
        pipeline.addLast(heartBeatHandler);

        // 配置访问路径
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 配置自定义channel
        pipeline.addLast(chatHandler);

    }
}
