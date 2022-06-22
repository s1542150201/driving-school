package io.driving.modules.business.service.impl;

import io.driving.common.utils.Constant;
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

import io.driving.modules.business.dao.MedicalDao;
import io.driving.modules.business.entity.MedicalEntity;
import io.driving.modules.business.service.MedicalService;


@Service("medicalService")
public class MedicalServiceImpl extends ServiceImpl<MedicalDao, MedicalEntity> implements MedicalService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        String ui = "";
        //不是系统管理员，只查自己的
        if(userId != Constant.SUPER_ADMIN){
            ui = userId.toString();
        }
        IPage<MedicalEntity> page = this.page(
                new Query<MedicalEntity>().getPage(params),
                new QueryWrapper<MedicalEntity>().eq(StringUtils.isNotBlank(ui),"user_id", ui)
        );
        for(MedicalEntity me : page.getRecords()){
            SysUserEntity sue = sysUserService.getById(me.getUserId());
            if(sue != null){
                me.setRealName(sue.getRealName());
            }
        }
        return new PageUtils(page);
    }

}
