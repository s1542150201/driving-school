package io.driving.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.driving.common.validator.ValidatorUtils;
import io.driving.common.validator.group.AddGroup;
import io.driving.common.validator.group.UpdateGroup;
import io.driving.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.driving.modules.business.entity.TestEntity;
import io.driving.modules.business.service.TestService;
import io.driving.common.utils.PageUtils;
import io.driving.common.utils.R;



/**
 * 成绩管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-20 13:10:48
 */
@RestController
@RequestMapping("business/test")
public class TestController {
    @Autowired
    private TestService testService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:test:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = testService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("business:test:info")
    public R info(@PathVariable("id") Long id){
        TestEntity test = testService.getById(id);

        return R.ok().put("test", test);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:test:save")
    public R save(@RequestBody TestEntity test){
        test.setCoachId(ShiroUtils.getUserId());
        ValidatorUtils.validateEntity(test, AddGroup.class);
        testService.save(test);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:test:update")
    public R update(@RequestBody TestEntity test){
        ValidatorUtils.validateEntity(test, UpdateGroup.class);
        testService.updateById(test);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:test:delete")
    public R delete(@RequestBody Long[] ids){
        testService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 通过学员id获取考试信息
     */
    @RequestMapping("/getByStudentId/{studentId}")
    public R getByStudentId(@PathVariable("studentId") Long studentId){
        List<TestEntity> test = testService.getByStudentId(studentId);

        return R.ok().put("test", test);
    }

}
