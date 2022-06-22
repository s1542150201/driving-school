package io.driving.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.driving.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.driving.modules.business.entity.CarEntity;
import io.driving.modules.business.service.CarService;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.R;



/**
 * 车辆管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 15:33:27
 */
@RestController
@RequestMapping("business/car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = carService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CarEntity car = carService.getById(id);

        return R.ok().put("car", car);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CarEntity car){
        carService.save(car);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CarEntity car){
        ValidatorUtils.validateEntity(car);
        carService.updateById(car);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        carService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 车辆列表
     */
    @RequestMapping("/select")
    public R select(){
        List<CarEntity> list = carService.list();

        return R.ok().put("list", list);
    }

}
