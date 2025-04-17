package com.ciwei.remoting.transport.netty.client;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * store 和 get Channel 对象
 * Create by LzWei on 2025/4/15
 */
@Slf4j
public class ChannelProvider {

    private final Map<String, Channel> channelMap;

    public ChannelProvider() {
        this.channelMap = new ConcurrentHashMap<>();
    }

    public Channel get(InetSocketAddress inetSocketAddress) {
        String key = inetSocketAddress.toString();
        //确定是否有相应地址的连接
        if (channelMap.containsKey(key)) {
            Channel channel = channelMap.get(key);
            //如果是，请确定连接是否可用，如果可用，请直接获取
            if (channel != null && channel.isActive()) {
                return channel;
            } else {
                channelMap.remove(key);
            }
        }
        return null;
    }

    public void set(InetSocketAddress inetSocketAddress,Channel channel) {
        String key = inetSocketAddress.toString();
        channelMap.put(key,channel);
    }

    public void remove(InetSocketAddress inetSocketAddress) {
        String key = inetSocketAddress.toString();
        channelMap.remove(key);
        log.debug("Channel map size : [{}]",channelMap.size());
    }
}
