package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by pzq on 2017/3/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring_dao.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillDao.getById(1000);
        System.out.println(seckill);
    }

    @Test
    public void reduceNumber() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        List<Seckill> seckills = seckillDao.findAll(0,10);
        System.out.println(seckills.size());
    }

}