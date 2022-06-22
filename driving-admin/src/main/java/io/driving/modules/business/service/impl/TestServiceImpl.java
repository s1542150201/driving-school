package io.driving.modules.business.service.impl;

import io.driving.common.utils.Constant;
import io.driving.modules.sys.entity.SysUserEntity;
import io.driving.modules.sys.service.SysUserService;
import io.driving.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.Query;

import io.driving.modules.business.dao.TestDao;
import io.driving.modules.business.entity.TestEntity;
import io.driving.modules.business.service.TestService;


@Service("testService")
public class TestServiceImpl extends ServiceImpl<TestDao, TestEntity> implements TestService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        SysUserEntity u = ShiroUtils.getUserEntity();
        String ui = "";
        String studentId = "";
        if(u.getType() == 0){
            //是教练登录
            //不是系统管理员，只查自己的
            if(u.getUserId() != Constant.SUPER_ADMIN){
                ui = u.getUserId().toString();
            }
        }else{
            studentId =  u.getUserId().toString();
        }
        IPage<TestEntity> page = this.page(
                new Query<TestEntity>().getPage(params),
                new QueryWrapper<TestEntity>().eq(StringUtils.isNotBlank(ui),"coach_id", ui)
                        .eq(StringUtils.isNotBlank(studentId),"student_id", studentId)
        );
        //
        for(TestEntity te : page.getRecords()){
            SysUserEntity sue = sysUserService.getById(te.getStudentId());
            if(sue != null){
                te.setStudentName(sue.getRealName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public List<TestEntity> getByStudentId(Long studentId) {
        QueryWrapper<TestEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("student_id",studentId);
        queryWrapper.orderByDesc("id");//根据id倒叙
        return baseMapper.selectList(queryWrapper);
    }

}
