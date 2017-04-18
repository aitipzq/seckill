package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by pzq on 2017/3/19.
 */
public interface SeckillService {
    Seckill getById(Long seckillId);
    List<Seckill> findAll();
    /**
     * 获得暴露秒杀接口
     */
    Exposer exposeSeckillUrl(long seckillId);
    /**
     * 执行秒杀
     */
    SeckillExcution excuteSeckill(long seckillId, long userphone, String md5)
    throws SeckillException,SeckillCloseException,RepeatKillException;
}
