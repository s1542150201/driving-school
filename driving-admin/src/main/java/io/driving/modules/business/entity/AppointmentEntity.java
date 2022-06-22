package io.driving.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-19 17:32:30
 */
@Data
@TableName("tb_appointment")
public class AppointmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 教练id
	 */
	private Long coachId;
	/**
	 * 学员id
	 */
	private Long studentId;
	/**
	 * 车辆id
	 */
	private Long carId;
	/**
	 * 时间段 0上午 1下午
	 */
	private Integer period;
	/**
	 * 日期
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date day;
	/**
	 * 教练姓名
	 */
	@TableField(exist=false)
	private String coachName;
	/**
	 * 车牌
	 */
	@TableField(exist=false)
	private String carNumber;
	/**
	 * 学员姓名
	 */
	@TableField(exist=false)
	private String studentName;

	/**
	 * 是否确认 0学员未来；1学员应邀
	 */
	private Integer confirm;

}
