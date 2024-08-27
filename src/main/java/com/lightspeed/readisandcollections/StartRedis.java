package com.lightspeed.readisandcollections;

import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class StartRedis {
    public static void main(String[] args) {
        String password = "admin_125";
        
        Set<HostAndPort> redisNodes = new HashSet<>();
        redisNodes.add(new HostAndPort("172.22.0.4", 6379));
        redisNodes.add(new HostAndPort("172.22.0.3", 6379));
        redisNodes.add(new HostAndPort("172.22.0.2", 6379));

        RedisHashMap redisMap = new RedisHashMap("myHash", redisNodes, password);
        redisMap.put("key1", 10);
        redisMap.put("key2", 20);

        System.out.println("key1: " + redisMap.get("key1"));
        System.out.println("key2: " + redisMap.get("key2"));
        System.out.println("Size: " + redisMap.size());
    }
}
