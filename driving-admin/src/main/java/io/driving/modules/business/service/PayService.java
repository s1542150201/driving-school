package io.driving.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.driving.common.utils.PageUtils;
import io.driving.modules.business.entity.PayEntity;

import java.util.List;
import java.util.Map;

/**
 * 缴费管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 12:30:33
 */
public interface PayService extends IService<PayEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void deleteByUserIds(List<Long> asList);

    PayEntity getInfoById(Long id);

    void updateDataById(PayEntity pay);
}

