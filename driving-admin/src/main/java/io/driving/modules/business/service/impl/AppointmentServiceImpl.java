package io.driving.modules.business.service.impl;

import io.driving.common.utils.Constant;
import io.driving.modules.business.entity.CarEntity;
import io.driving.modules.business.service.CarService;
import io.driving.modules.sys.entity.SysUserEntity;
import io.driving.modules.sys.service.SysUserService;
import io.driving.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.Query;

import io.driving.modules.business.dao.AppointmentDao;
import io.driving.modules.business.entity.AppointmentEntity;
import io.driving.modules.business.service.AppointmentService;


@Service("appointmentService")
public class AppointmentServiceImpl extends ServiceImpl<AppointmentDao, AppointmentEntity> implements AppointmentService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CarService carService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        SysUserEntity u = ShiroUtils.getUserEntity();
        String ui = "";
        if(u.getType() == 0){ //0是教练
            //是教练登录
            //不是系统管理员，只查自己的
            if(u.getUserId() != Constant.SUPER_ADMIN){
                ui = u.getUserId().toString();
            }
        }
        IPage<AppointmentEntity> page = this.page(
                new Query<AppointmentEntity>().getPage(params),
                new QueryWrapper<AppointmentEntity>().eq(StringUtils.isNotBlank(ui),"coach_id", ui)
        );
        for(AppointmentEntity ae : page.getRecords()){
            SysUserEntity sue = sysUserService.getById(ae.getCoachId());
            if(sue != null){
                ae.setCoachName(sue.getRealName());
            }
            CarEntity ce = carService.getById(ae.getCarId());
            if(ce != null){
                ae.setCarNumber(ce.getNumber());
            }
            if(ae.getStudentId() != null){
                SysUserEntity sue1 = sysUserService.getById(ae.getStudentId());
                if(sue1 != null){
                    ae.setStudentName(sue1.getRealName());
                }
            }
        }
        return new PageUtils(page);
    }
    /**
     * 预约
     */
    @Override
    public void book(Long id) {
        AppointmentEntity ae = this.getById(id);
        ae.setStudentId(ShiroUtils.getUserId());
        this.updateById(ae);
    }
    /**
     * 确认
     */
    @Override
    public void confirm(Long id) {
        AppointmentEntity ae = this.getById(id);
        ae.setConfirm(1);
        this.updateById(ae);
    }

    @Override
    public PageUtils queryMyPage(Map<String, Object> params) {
        IPage<AppointmentEntity> page = this.page(
                new Query<AppointmentEntity>().getPage(params),
                new QueryWrapper<AppointmentEntity>().eq("student_id", ShiroUtils.getUserId())
        );
        for(AppointmentEntity ae : page.getRecords()){
            SysUserEntity sue = sysUserService.getById(ae.getCoachId());
            if(sue != null){
                ae.setCoachName(sue.getRealName());
            }
            CarEntity ce = carService.getById(ae.getCarId());
            if(ce != null){
                ae.setCarNumber(ce.getNumber());
            }
        }
        return new PageUtils(page);
    }

}
