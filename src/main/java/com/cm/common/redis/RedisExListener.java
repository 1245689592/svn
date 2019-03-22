package com.cm.common.redis;

import redis.clients.jedis.JedisPubSub;

public class RedisExListener extends JedisPubSub{

	@Override
	public void onMessage(String channel, String message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		// TODO Auto-generated method stub
		super.onPMessage(pattern, channel, message);
	}
	
	
}
