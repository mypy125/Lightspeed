package com.lightspeed.readisandcollections;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.*;

public class RedisHashMap implements Map<String, Integer> {
    private final JedisCluster jedisCluster;
    private final String redisHashName;

    public RedisHashMap(String redisHashName, Set<HostAndPort> redisNodes, String password) {
        this.redisHashName = redisHashName;
        this.jedisCluster = new JedisCluster(redisNodes, 2000, 2000, 5, password, new JedisPoolConfig());
    }

    @Override
    public int size() {
        Long size = jedisCluster.hlen(redisHashName);
        return size != null ? size.intValue() : 0;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return jedisCluster.hexists(redisHashName, key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public Integer get(Object key) {
        String value = jedisCluster.hget(redisHashName, key.toString());
        return value != null ? Integer.valueOf(value) : null;
    }

    @Override
    public Integer put(String key, Integer value) {
        String previousValue = jedisCluster.hget(redisHashName, key);
        jedisCluster.hset(redisHashName, key, value.toString());
        return previousValue != null ? Integer.valueOf(previousValue) : null;
    }

    @Override
    public Integer remove(Object key) {
        String removeValue = jedisCluster.hget(redisHashName, key.toString());
        jedisCluster.hdel(redisHashName, key.toString());
        return removeValue != null ? Integer.valueOf(removeValue): null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> map) {
        for(Entry<? extends String, ? extends Integer> entry : map.entrySet()){
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        jedisCluster.del(redisHashName);
    }

    @Override
    public Set<String> keySet() {
        return jedisCluster.hkeys(redisHashName);
    }

    @Override
    public Collection<Integer> values() {
        Collection<String> values = jedisCluster.hvals(redisHashName);
        List<Integer> intValues = new ArrayList<>();
        for(String value : values){
            intValues.add(Integer.valueOf(value));
        }
        return intValues;
    }

    @Override
    public Set<Entry<String, Integer>> entrySet() {
        Map<String, String> redisMap = jedisCluster.hgetAll(redisHashName);
        Set<Entry<String, Integer>> entrySet = new HashSet<>();
        for(Entry<String, String> entry : redisMap.entrySet()){
            entrySet.add(new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), Integer.valueOf(entry.getValue())));
        }
        return entrySet;
    }
}
