package io.driving.modules.business.dao;

import io.driving.modules.business.entity.MedicalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 体检信息
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 15:33:27
 */
@Mapper
public interface MedicalDao extends BaseMapper<MedicalEntity> {
	
}
