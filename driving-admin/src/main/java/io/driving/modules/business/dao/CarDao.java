package io.driving.modules.business.dao;

import io.driving.modules.business.entity.CarEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车辆管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 15:33:27
 */
@Mapper
public interface CarDao extends BaseMapper<CarEntity> {
	
}
