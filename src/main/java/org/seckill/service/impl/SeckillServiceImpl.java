package org.seckill.service.impl;

import org.seckill.dao.RedisDao;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillState;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by pzq on 2017/3/19.
 */
@Service
public class SeckillServiceImpl implements SeckillService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;
    private final String salt = "fafjdifsofjsdalfjie";

    public Seckill getById(Long seckillId) {
        return seckillDao.getById(seckillId);
    }

    public List<Seckill> findAll() {
        return seckillDao.findAll(0,4);
    }

    public Exposer exposeSeckillUrl(long seckillId) {
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill == null){
            seckill = getById(seckillId);
            if (seckill != null){
                redisDao.putSeckill(seckill);
            }else {
            return new Exposer(false,seckillId);
            }
        }
        Date start = seckill.getStartTime();
        Date end = seckill.getEndTime();
        Date now = new Date();
        if(start.getTime() > now.getTime() || end.getTime() < now.getTime()){
            return new Exposer(false,seckillId,now.getTime(),start.getTime(),end.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }
    private String getMd5(long seckillId){
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
    @Transactional
    public SeckillExcution excuteSeckill(long seckillId, long userphone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException {
        try {
            if(md5 == null || !md5.equals(getMd5(seckillId))){
                throw new SeckillException("seckill data rewrite");
            }
            Date now = new Date();
            int reduceState = seckillDao.reduceNumber(seckillId,now);
            //秒杀未开启或已关闭
            if(reduceState <= 0){
                throw new SeckillCloseException("seckill is closed");
            }  else {
                int result = successKilledDao.insertSuccessKilled(seckillId,userphone);
                //重复秒杀
                if(result <= 0){
                    throw new RepeatKillException("seckill repeated");
                }
                SuccessKilled successKilled = successKilledDao.getByIdWithSeckill(seckillId);
                return new SeckillExcution(seckillId, SeckillState.SUCCESS,successKilled);
            }
        }catch (SeckillCloseException e1){
            throw  e1;
        }catch (RepeatKillException e2){
            throw  e2;
        }catch (Exception e){
            throw new SeckillException(e.getMessage());
        }

    }
}
