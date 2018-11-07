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
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Autowired
    private NettyChannelInitializer initializer;
    @Value("${netty.port}")
    private Integer port;
    private EventLoopGroup masterGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap bootstrap;

    /**
     * 启动服务器
     */
    public void start() {
        try {
            // 创建 主线程池
            masterGroup = new NioEventLoopGroup();
            // 创建 子线程池
            subGroup = new NioEventLoopGroup();
            // 启动服务
            bootstrap = new ServerBootstrap();
            bootstrap.group(masterGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer);
            // 绑定端口，启动netty服务
            ChannelFuture channelFuture = bootstrap.bind(8088).sync();
            // 当容器关闭时，netty能优雅关闭
            channelFuture.channel().closeFuture().sync();
            logger.info("netty服务启动成功");
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
}
