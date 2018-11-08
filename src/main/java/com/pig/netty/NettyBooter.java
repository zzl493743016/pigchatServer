package com.pig.netty;

import com.pig.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Arthas
 * @create 2018/10/31
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(NettyBooter.class);
    @Autowired
    private NettyServer nettyServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            try {
                ThreadPoolUtil.addExecuteTask(nettyServer);
            } catch (Exception e) {
                logger.error("netty启动失败",e);
            }
        }
    }
}
