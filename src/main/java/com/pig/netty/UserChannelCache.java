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
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final String KEY_NAME = "CHANNEL_USERID_";
    /**
     * 缓存过期时长，单位：秒
     */
    public static final long EXPIRE_TIME = 60 * 60 * 24 * 7;
    /**
     * 缓存channel到本地
     */
    public void addChannel(Channel channel) {
        channels.add(channel);
    }
    /**
     * 移除channel缓存
     */
    public void removeChannel(Channel channel) {
        // 移除本地channel缓存
        if (channel.isOpen()) {
            channel.close();
        }
        channels.remove(channel);
    }
    /**
     * 缓存用户与channel的关系到redis中
     */
    public void saveRelation(Integer userId, String channelId) throws Exception {
        logger.warn("添加通道，用户id:" + userId);
        UserChannelCacheModel model = new UserChannelCacheModel();
        model.setChannelId(channelId);
        model.setIp(InetAddress.getLocalHost().getHostAddress());
        model.setPort(port);
        redisService.setex(KEY_NAME + userId, JsonUtil.objToStr(model), EXPIRE_TIME);
    }
    /**
     * 移除redis中用户与channel的关系
     */
    private void removeRelation(Integer userId) {
        logger.warn("移除通道，用户id:" + userId);
        redisService.del(KEY_NAME + userId);
    }
    /**
     * 通过用户id获取对应的channel
     */
    public Channel getChannelByUserId(Integer userId) {
        String str = redisService.get(KEY_NAME + userId);
        UserChannelCacheModel model = JsonUtil.strToObj(str, UserChannelCacheModel.class);
        if (!ObjectUtils.isEmpty(model)) {
            for (Channel channel : channels) {
                if (channel.id().asLongText().equals(model.getChannelId())) {
                    // 找到用户对应的channel，并返回
                    return channel;
                }
            }
        }
        // 如果没有找到用户对应的channel，则删除redis中多余的关系缓存
        if (!ObjectUtils.isEmpty(str)) {
            removeRelation(userId);
        }
        return null;
    }
}
