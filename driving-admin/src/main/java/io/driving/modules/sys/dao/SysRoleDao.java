

package io.driving.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.driving.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	

}
