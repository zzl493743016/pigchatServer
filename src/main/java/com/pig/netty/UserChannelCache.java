package com.pig.netty;

import com.pig.utils.JsonUtil;
import com.pig.utils.RedisService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Map;

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

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final String CHANNEL_MAP = "CHANNEL_MAP";

    /**
     * 缓存channel到本地
     */
    public void addChannel(Channel channel) {
        channels.add(channel);
    }
    /**
     * 移除channel缓存，并删除channel与用户的关系
     */
    public void removeChannel(Channel channel) {
        channels.remove(channel);
        for (Map.Entry entry : redisService.hgetall(CHANNEL_MAP).entrySet()) {
            UserChannelCacheModel model = JsonUtil.strToObj((String) entry.getValue(), UserChannelCacheModel.class);
            if (channel.id().asLongText().equals(model.getChannelId())) {
                removeRelation((Integer) entry.getKey());
            }
        }
    }
    /**
     * 缓存用户与channel的关系到redis中
     */
    public void saveRelation(Integer userId, String channelId) throws Exception{
        UserChannelCacheModel model = new UserChannelCacheModel();
        model.setChannelId(channelId);
        model.setIp(InetAddress.getLocalHost().getHostAddress());
        model.setPort(port);
        redisService.hset(CHANNEL_MAP, userId.toString(), JsonUtil.objToStr(model));
    }
    /**
     * 移除redis中用户与channel的关系
     */
    private void removeRelation(Integer userId) {
        redisService.hdel(CHANNEL_MAP, userId.toString());
    }
    /**
     * 通过用户id获取对应的channel
     */
    public Channel getChannelByUserId(Integer userId) {
        UserChannelCacheModel model = JsonUtil.strToObj((String) redisService.hget(CHANNEL_MAP, userId.toString()), UserChannelCacheModel.class);
        for (Channel channel : channels) {
            if (channel.id().asLongText().equals(model.getChannelId())) {
                return channel;
            }
        }
        return null;
    }
}
