package com.dmsd.service.redis;

public interface JedisClient {

    public String set(String key, String value);
    public String get(String key);

    //添加哈希值
    public Long hset(String key, String item, String value);
    //获取哈希
    public String hget(String key, String item);
    //增加1
    public Long incr(String key);
    //减少1
    public Long decr(String key);
    //设置有效时间
    public Long expire(String key, int seconds);
    //查看当前有效时间time to live
    public Long ttl(String key);
    //删除
    public Long hdel(String key,String item);

}
