package io.driving.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 缴费管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 12:30:33
 */
@Data
@TableName("tb_pay")
public class PayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 缴费金额
	 */
	private BigDecimal money;
	/**
	 * 用户姓名
	 */
	@TableField(exist=false)
	private String realName;

}
