package com.dmsd.service.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisClientSingle implements JedisClient{

    @Autowired
    private JedisPool jedisPool;


    /**
     * 设置值K-V
     */
    @Override
    public String set(String key, String value) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //设置数组
        String result = jedis.set(key, value);
        //关闭连接
        jedis.close();
        return result;
    }

    /**
     * 获取数组
     */
    @Override
    public String get(String key) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //设置数组
        String result = jedis.get(key);
        //关闭连接
        jedis.close();
        return null;
    }

    @Override
    public Long hset(String key, String item, String value) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //设置哈希
        Long result = jedis.hset(key, item, value);
        //关闭连接
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key, String item) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //读取哈希
        String result = jedis.hget(key, item);
        //关闭连接
        jedis.close();
        return result;
    }

    @Override
    public Long incr(String key) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //+1
        Long result = jedis.incr(key);
        //关闭连接
        jedis.close();
        return result;
    }

    @Override
    public Long decr(String key) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //-1
        Long result = jedis.decr(key);
        //关闭连接
        jedis.close();
        return result;
    }

    @Override
    public Long expire(String key, int seconds) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //设置有效时间
        Long result = jedis.expire(key,seconds);
        //关闭连接
        jedis.close();
        return result;
    }

    @Override
    public Long ttl(String key) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //查看有效时间
        Long result = jedis.ttl(key);
        //关闭连接
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String item) {
        //建立连接
        Jedis jedis = jedisPool.getResource();
        //查看有效时间
        Long result = jedis.hdel(key, item);
        //关闭连接
        jedis.close();
        return result;
    }
}
