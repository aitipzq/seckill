package org.seckill.dao;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by pzq on 2017/3/22.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool = new JedisPool("192.168.0.74",6379);
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
//    public RedisDao(String host,Integer port){
//        jedisPool =  new JedisPool(host,port);
//    }

    public Seckill getSeckill(long seckillId){
        Jedis jedis =  jedisPool.getResource();
        try {
            try {
                String key = "Seckill:" + seckillId;
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                Seckill seckill = schema.newMessage();
                ProtobufIOUtil.mergeFrom(bytes,seckill,schema);
                return  seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        Jedis jedis = jedisPool.getResource();
        try {
            try {
                String key = "Seckill:" + seckill.getSeckillId();
                int time = 60*60;
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
             String result = jedis.setex(key.getBytes(),time,bytes);
                System.out.println(result);
                return  result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return  null;
    }

    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress);
    }

}
