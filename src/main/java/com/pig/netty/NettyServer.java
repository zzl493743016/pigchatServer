package com.pig.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Netty服务器
 * @author Arthas
 * @create 2018/10/31
 */
@Component
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private NettyChannelInitializer nettyChannelInitializer;

    private EventLoopGroup masterGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap bootstrap;

    /**
     * 启动服务器
     */
    public void start() {
        // 创建 主线程池
        masterGroup = new NioEventLoopGroup();
        // 创建 子线程池
        subGroup = new NioEventLoopGroup();
        // 启动服务
        bootstrap = new ServerBootstrap();
        bootstrap.group(masterGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(nettyChannelInitializer);
        // 绑定端口
        bootstrap.bind(8088);
        logger.info("netty服务启动成功");
    }

}
