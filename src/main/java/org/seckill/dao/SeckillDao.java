package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Created by pzq on 2017/3/18.
 */
public interface SeckillDao {
    Seckill getById(long seckillId);

    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    List<Seckill> findAll(@Param("offset") int offset, @Param("limit") int limit);
}
