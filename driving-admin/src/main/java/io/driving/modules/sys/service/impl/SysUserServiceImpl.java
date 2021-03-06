

package io.driving.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.driving.common.annotation.DataFilter;
import io.driving.common.utils.Constant;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.Query;
import io.driving.modules.business.entity.PayEntity;
import io.driving.modules.business.service.PayService;
import io.driving.modules.sys.dao.SysUserDao;
import io.driving.modules.sys.entity.SysDeptEntity;
import io.driving.modules.sys.entity.SysUserEntity;
import io.driving.modules.sys.service.SysDeptService;
import io.driving.modules.sys.service.SysUserRoleService;
import io.driving.modules.sys.service.SysUserService;
import io.driving.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	@Autowired
	private PayService payService;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");

		IPage<SysUserEntity> page = this.page(
			new Query<SysUserEntity>().getPage(params),
			new QueryWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username).ne("user_id",1)//管理员不查自己的信息
				.apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
		);

		for(SysUserEntity sysUserEntity : page.getRecords()){
			SysDeptEntity sysDeptEntity = sysDeptService.getById(sysUserEntity.getDeptId());
			sysUserEntity.setDeptName(sysDeptEntity.getName());
		}

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveUser(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		//学员用户添加到缴费表
		if(user.getRoleIdList().get(0).longValue()==2L){
			PayEntity pe = new PayEntity();
			pe.setUserId(user.getUserId());
			payService.save(pe);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			SysUserEntity userEntity = this.getById(user.getUserId());
			user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
		}
		this.updateById(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}


	@Override
	public boolean updatePassword(Long userId, String password, String newPassword,String plaintext) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
		userEntity.setPlaintext(plaintext);
        return this.update(userEntity,
        	new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

	@Override
	public void deleteByIds(List<Long> asList) {
		this.removeByIds(asList);
		payService.deleteByUserIds(asList);
	}

	@Override
	public List<SysUserEntity> select() {
		return baseMapper.select();
	}

	@Override
	public List<SysUserEntity> studentSelect() {
		return baseMapper.studentSelect();
	}

}
