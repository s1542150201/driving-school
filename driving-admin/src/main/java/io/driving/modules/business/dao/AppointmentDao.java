package io.driving.modules.business.dao;

import io.driving.modules.business.entity.AppointmentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 17:32:30
 */
@Mapper
public interface AppointmentDao extends BaseMapper<AppointmentEntity> {
	
}
