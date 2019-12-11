//package com.learn.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.Set;
//
// * @author dshuyou
// * @date 2019/11/22 11:30
//
//
//@Configuration
//public class RedisConfig extends CachingConfigurerSupport {
//
//    @Autowired
//    private RedisConnectionFactory factory;
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(factory);
//        System.out.println("redisTemplate");
//        return redisTemplate;
//    }
//
//    @Value("${spring.redis.cluster.nodes}")
//    private String cluster;
//
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//
//    @Value("${spring.redis.jedis.pool.max-active}")
//    private int maxActive;
//
//    @Value("${spring.redis.jedis.pool.max-idle}")
//    private int maxIdle;
//
//    @Value("${spring.redis.jedis.pool.min-idle}")
//    private int minIdle;
//
//    @Value("${spring.redis.jedis.pool.max-wait}")
//    private long maxWaitMillis;
//
//    @Bean
//    public JedisCluster jedisCluster() {
//
//        String[] nodes = cluster.split(",");
//        Set<HostAndPort> nodeSet = new HashSet<HostAndPort>(nodes.length);
//
//        for (String node : nodes) {
//            String[] nodeAttrs = node.trim().split(":");
//            HostAndPort hap = new HostAndPort(nodeAttrs[0], Integer.valueOf(nodeAttrs[1]));
//            nodeSet.add(hap);
//        }
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setMaxTotal(maxActive);
//        jedisPoolConfig.setMinIdle(minIdle);
//        // TODO:password and poolconfig
//        return new JedisCluster(nodeSet,timeout,jedisPoolConfig);
//    }
//}
