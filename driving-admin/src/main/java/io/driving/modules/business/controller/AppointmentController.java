package io.driving.modules.business.controller;

import java.util.Arrays;
import java.util.Map;

import io.driving.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.driving.modules.business.entity.AppointmentEntity;
import io.driving.modules.business.service.AppointmentService;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.R;



/**
 * 预约管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 17:32:30
 */
@RestController
@RequestMapping("business/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:appointment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = appointmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:appointment:info")
    public R info(@PathVariable("id") Long id){
        AppointmentEntity appointment = appointmentService.getById(id);

        return R.ok().put("appointment", appointment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:appointment:save")
    public R save(@RequestBody AppointmentEntity appointment){
        appointment.setConfirm(0);
        appointmentService.save(appointment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:appointment:update")
    public R update(@RequestBody AppointmentEntity appointment){
        ValidatorUtils.validateEntity(appointment);
        appointmentService.updateById(appointment);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:appointment:delete")
    public R delete(@RequestBody Long[] ids){
        appointmentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 预约
     */
    @RequestMapping("/book/{id}")
    @RequiresPermissions("business:appointment:book")
    public R book(@PathVariable("id") Long id){
        appointmentService.book(id);
        return R.ok();
    }

    /**
     * 确认
     */
    @RequestMapping("/confirm/{id}")
    @RequiresPermissions("business:appointment:confirm")
    public R confirm(@PathVariable("id") Long id){
        appointmentService.confirm(id);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/myList")
    public R myList(@RequestParam Map<String, Object> params){
        PageUtils page = appointmentService.queryMyPage(params);

        return R.ok().put("page", page);
    }

}
