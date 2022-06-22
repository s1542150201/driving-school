package io.driving.modules.business.dao;

import io.driving.modules.business.entity.TestEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 成绩管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-20 13:10:48
 */
@Mapper
public interface TestDao extends BaseMapper<TestEntity> {
	
}
