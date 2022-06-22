package io.driving.modules.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.driving.modules.sys.entity.SysUserEntity;
import io.driving.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.Query;

import io.driving.modules.business.dao.PayDao;
import io.driving.modules.business.entity.PayEntity;
import io.driving.modules.business.service.PayService;


@Service("payService")
public class PayServiceImpl extends ServiceImpl<PayDao, PayEntity> implements PayService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PayEntity> page = this.page(
                new Query<PayEntity>().getPage(params),
                new QueryWrapper<PayEntity>()
        );
        for(PayEntity pe : page.getRecords()){
            SysUserEntity sue = sysUserService.getById(pe.getUserId());
            if(sue != null){
                pe.setRealName(sue.getRealName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void deleteByUserIds(List<Long> asList) {
        UpdateWrapper<PayEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.in("user_id",asList);
        baseMapper.delete(updateWrapper);
    }

    @Override
    public PayEntity getInfoById(Long id) {
        PayEntity pe = this.getById(id);
        SysUserEntity sue = sysUserService.getById(pe.getUserId());
        if(sue != null){
            pe.setRealName(sue.getRealName());
        }
        return pe;
    }

    @Override
    public void updateDataById(PayEntity pay) {
        if(pay.getCreateTime() == null){
            //说明是第一次付款
            pay.setCreateTime(new Date());
            //同时更新用户，状态改为正常
            SysUserEntity sue = sysUserService.getById(pay.getUserId());
            sue.setStatus(1);
            sysUserService.updateById(sue);
        }
        this.updateById(pay);
    }

}
