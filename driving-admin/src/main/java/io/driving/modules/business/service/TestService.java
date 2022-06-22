package io.driving.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.driving.common.utils.PageUtils;
import io.driving.modules.business.entity.TestEntity;

import java.util.List;
import java.util.Map;

/**
 * 成绩管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-20 13:10:48
 */
public interface TestService extends IService<TestEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TestEntity> getByStudentId(Long studentId);
}

