package io.driving.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.driving.common.utils.PageUtils;
import io.driving.modules.business.entity.AppointmentEntity;

import java.util.Map;

/**
 * 预约管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 17:32:30
 */
public interface AppointmentService extends IService<AppointmentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void book(Long id);

    void confirm(Long id);

    PageUtils queryMyPage(Map<String, Object> params);
}

