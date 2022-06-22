package io.driving.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.driving.common.utils.PageUtils;
import io.driving.modules.business.entity.MedicalEntity;

import java.util.Map;

/**
 * 体检信息
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 15:33:27
 */
public interface MedicalService extends IService<MedicalEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

