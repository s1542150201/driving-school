

package io.driving.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.driving.common.annotation.DataFilter;
import io.driving.modules.sys.dao.SysDeptDao;
import io.driving.modules.sys.entity.SysDeptEntity;
import io.driving.modules.sys.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
	
	@Override
	@DataFilter(subDept = true, user = false, tableAlias = "t1")
	public List<SysDeptEntity> queryList(Map<String, Object> params){
		return baseMapper.queryList(params);
	}

	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return baseMapper.queryDetpIdList(parentId);
	}

	@Override
	public List<Long> getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		return deptIdList;
	}

	/**
	 * 递归
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		//循环子部门ID
		for(Long deptId : subIdList){
			//判断是否存在子部门
			List<Long> list = queryDetpIdList(deptId);
			//如果存在子部门，则继续递归
			if(list.size() > 0){
				//递归
				getDeptTreeList(list, deptIdList);
			}
			//部门ID添加到部门ID列表
			deptIdList.add(deptId);
		}
	}
}
