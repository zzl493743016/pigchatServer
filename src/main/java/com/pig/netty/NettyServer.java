package com.pig.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Netty服务器
 * @author Arthas
 * @create 2018/10/31
 */
@Component
public class NettyServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private NettyChannelInitializer initializer;
    @Value("${netty.port}")
    private Integer port;
    @Value("${netty.master.group.threads}")
    private Integer masterGroupThreads;
    @Value("${netty.sub.group.threads}")
    private Integer subGroupThreads;

    /**
     * 启动服务器
     */
    public void start() {
        // 创建 主线程池
        EventLoopGroup masterGroup = new NioEventLoopGroup(masterGroupThreads);
        // 创建 子线程池
        EventLoopGroup subGroup = new NioEventLoopGroup(subGroupThreads);
        try {
            // 启动服务
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(masterGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer);
            // 绑定端口，启动netty服务
            ChannelFuture future = bootstrap.bind(port).sync();
            // 判断启动是否成功
            if (future.isSuccess()) {
                logger.info("netty服务启动成功!!!!");
                future.channel().closeFuture().sync();
            } else {
                logger.warn("netty服务启动失败，尝试重启");
                start();
            }
        } catch (Exception e) {
            logger.error("netty服务启动失败",e);
        } finally {
            // 优雅关闭
            if (!ObjectUtils.isEmpty(subGroup)) {
                subGroup.shutdownGracefully();
            }
            if (!ObjectUtils.isEmpty(masterGroup)) {
                masterGroup.shutdownGracefully();
            }
        }
    }
    @Override
    public void run() {
        start();
    }
}
