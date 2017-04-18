package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExcution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillState;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by pzq on 2017/3/19.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final  Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @RequestMapping("list")
    public String toList(Model model){
        model.addAttribute("list",seckillService.findAll());
        return "list";
    }
    @RequestMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }
    @RequestMapping(value = "/{seckillId}/expose",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<Exposer> exposervoid(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exposeSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.info(e.getMessage());
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/excution",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult excution(@PathVariable("seckillId") long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long userphone){
        if(userphone == null){
            return  new SeckillResult<SeckillExcution>(false,"未注册");
        }
        SeckillResult result;
        try {
            SeckillExcution seckillExcution = seckillService.excuteSeckill(seckillId,userphone,md5);
            result = new SeckillResult<SeckillExcution>(true,seckillExcution);
        }catch (RepeatKillException e){
            logger.info(e.getMessage());
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillState.REPEATE_KILL);
            result = new SeckillResult<SeckillExcution>(true,seckillExcution);
        }catch (SeckillCloseException e){
            logger.info(e.getMessage());
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillState.END);
            result = new SeckillResult<SeckillExcution>(true,seckillExcution);
        }catch (Exception e){
            logger.info(e.getMessage());
            SeckillExcution seckillExcution = new SeckillExcution(seckillId, SeckillState.INNER_ERROR);
            result = new SeckillResult<SeckillExcution>(true,seckillExcution);
        }
        return  result;
    }

    @RequestMapping(value ="/now/time",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        return  new SeckillResult<Long>(true,new Date().getTime());
    }

    public static void main(String[] args) {
        System.out.println(SeckillState.END.getStateInfo());
    }
}
