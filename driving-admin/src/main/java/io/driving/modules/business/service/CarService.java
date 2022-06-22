package io.driving.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.driving.common.utils.PageUtils;
import io.driving.modules.business.entity.CarEntity;

import java.util.Map;

/**
 * 车辆管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 15:33:27
 */
public interface CarService extends IService<CarEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

