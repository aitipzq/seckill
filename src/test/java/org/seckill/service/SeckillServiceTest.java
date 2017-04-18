package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by pzq on 2017/3/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring*.xml"})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill = {}",seckill);
    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void exposeSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exposeSeckillUrl(1000L);
        if(exposer.isExposed()){
            try {
                SeckillExcution excution = seckillService.excuteSeckill(exposer.getSeckillId(),13275021213L,exposer.getMd5());
            }catch (RepeatKillException e){
                logger.warn(e.getMessage());
            }catch (SeckillCloseException e){
                logger.warn(e.getMessage());
            }catch (SeckillException e){
                logger.warn(e.getMessage());
            }
        }else {
            logger.warn("exposer = {}",exposer);

        }
    }

    @Test
    public void excuteSeckill() throws Exception {

    }

}