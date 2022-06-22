package io.driving.modules.business.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.driving.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.driving.modules.business.entity.PayEntity;
import io.driving.modules.business.service.PayService;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.R;


/**
 * 缴费管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 12:30:33
 */
@RestController
@RequestMapping("business/pay")
public class PayController {
    @Autowired
    private PayService payService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = payService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        PayEntity pay = payService.getInfoById(id);

        return R.ok().put("pay", pay);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PayEntity pay){
        ValidatorUtils.validateEntity(pay);
        payService.updateDataById(pay);
        return R.ok();
    }

}
