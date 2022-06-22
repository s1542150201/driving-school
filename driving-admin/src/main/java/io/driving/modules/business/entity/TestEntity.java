package io.driving.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.driving.common.validator.group.AddGroup;
import io.driving.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 成绩管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2022-03-20 13:10:48
 */
@Data
@TableName("tb_test")
public class TestEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 学员id
	 */
	private Long studentId;
	/**
	 * 教练id
	 */
	private Long coachId;
	/**
	 * 考试科目
	 */
	@NotBlank(message="考试科目不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String subject;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 考试时间
	 */
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	private Date createTime;
	/**
	 * 学员姓名
	 */
	@TableField(exist=false)
	private String studentName;

}
