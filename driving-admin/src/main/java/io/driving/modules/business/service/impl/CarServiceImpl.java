package io.driving.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.Query;

import io.driving.modules.business.dao.CarDao;
import io.driving.modules.business.entity.CarEntity;
import io.driving.modules.business.service.CarService;


@Service("carService")
public class CarServiceImpl extends ServiceImpl<CarDao, CarEntity> implements CarService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CarEntity> page = this.page(
                new Query<CarEntity>().getPage(params),
                new QueryWrapper<CarEntity>()
        );
        return new PageUtils(page);
    }

}
