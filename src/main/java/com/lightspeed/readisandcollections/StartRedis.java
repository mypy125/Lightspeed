package com.lightspeed.readisandcollections;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class StartRedis {
    public static void main(String[] args) {
        String password = "admin_125";
        Set<HostAndPort> redisNodes = new HashSet<>();
        redisNodes.add(new HostAndPort("localhost", 6379));

        RedisHashMap redisMap = new RedisHashMap("myHash", redisNodes, password);
        redisMap.put("key1", 10);
        redisMap.put("key2", 20);

        System.out.println("key1: " + redisMap.get("key1"));
        System.out.println("key2: " + redisMap.get("key2"));
        System.out.println("Size: " + redisMap.size());
    }
}
