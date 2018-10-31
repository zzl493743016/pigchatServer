package com.pig.netty;

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

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            nettyServer.start();
        }
    }
}
