package io.driving.modules.business.dao;

import io.driving.modules.business.entity.PayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 缴费管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 12:30:33
 */
@Mapper
public interface PayDao extends BaseMapper<PayEntity> {
	
}
