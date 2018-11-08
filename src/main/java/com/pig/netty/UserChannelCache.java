package com.pig.netty;

import com.pig.utils.JsonUtil;
import com.pig.utils.RedisService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户与channel的关系 存储器
 * @author Arthas
 * @create 2018/11/7
 */
@Component
public class UserChannelCache {

    @Autowired
    private RedisService redisService;
    @Value("${netty.port}")
    private Integer port;
    private static final Logger logger = LoggerFactory.getLogger(UserChannelCache.class);
    private static ConcurrentHashMap<Integer, Channel> channels = new ConcurrentHashMap<>();
    private static final String KEY_NAME = "CHANNEL_USER_ID_";

    /**
     * 添加用户与channel的对应关系
     */
    public void add(Integer userId, Channel channel) throws Exception {
        logger.warn("添加通道，用户id:" + userId);
        UserChannelCacheModel model = new UserChannelCacheModel();
        model.setChannelId(channel.id().asLongText());
        model.setIp(InetAddress.getLocalHost().getHostAddress());
        model.setPort(port);
        //
        channels.put(userId, channel);
        //
        redisService.set(KEY_NAME + userId, JsonUtil.objToStr(model));
    }
    /**
     * 移除用户与channel的对应关系
     */
    public void removeByChannel(Channel channel) {
        Integer userId = null;
        for (Map.Entry<Integer, Channel> entry : channels.entrySet()) {
            if (channel.equals(entry.getValue())) {
                userId = entry.getKey();
            }
        }
        if (!ObjectUtils.isEmpty(userId)) {
            // 关闭对应的channel
            if (channel.isOpen()) {
                channel.close();
            }
            logger.warn("移除通道，用户id:" + userId);
            //
            channels.remove(userId);
            //
            redisService.del(KEY_NAME + userId);
        }
    }
    /**
     * 移除用户与channel的对应关系
     */
    public void removeByUserId(Integer userId) {
        Channel channel = channels.get(userId);
        if (!ObjectUtils.isEmpty(channel)) {
            removeByChannel(channel);
        }
    }
    /**
     * 通过用户id获取对应的channel
     */
    public Channel getChannelByUserId(Integer userId) {
        try {
            String str = redisService.get(KEY_NAME + userId);
            UserChannelCacheModel model = JsonUtil.strToObj(str, UserChannelCacheModel.class);
            Channel returnChannel = channels.get(userId);
            if (returnChannel.id().asLongText().equals(model.getChannelId())) {
                return returnChannel;
            }
            // 如果没有找到用户对应的channel，则删除redis中多余的关系缓存
            if (!ObjectUtils.isEmpty(str)) {
                removeByUserId(userId);
            }
        } catch (Exception e) {
            logger.error("获取用户channel异常",e);
        }
        return null;
    }
    /**
     *  更新状态
     */
    public void reload(Channel channel) {

    }
}
