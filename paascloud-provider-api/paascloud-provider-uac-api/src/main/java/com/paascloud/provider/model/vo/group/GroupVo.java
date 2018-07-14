package com.paascloud.provider.model.vo.group;

import com.paascloud.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * The class Uac group.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = 781846964762961318L;
	/**
	 * 组织编码
	 */
	private String groupCode;

	/**
	 * 组织名称
	 */
	private String groupName;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 组织类型 1：仓库 2：基地
	 */
	private String type;

	/**
	 * 父ID
	 */
	private Long pid;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 是否叶子节点,1不是0是
	 */
	private Integer leaf;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 组织地址
	 */
	private String groupAddress;

	/**
	 * 省名称
	 */
	private String provinceName;

	/**
	 * 省编码
	 */
	private Long provinceId;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 城市编码
	 */
	private Long cityId;

	/**
	 * 区名称
	 */
	private String areaName;

	/**
	 * 区编码
	 */
	private Long areaId;

	/**
	 * 街道编码
	 */
	private Long streetId;

	/**
	 * 街道名称
	 */
	private String streetName;

	/**
	 * 详细地址
	 */
	private String detailAddress;

	/**
	 * 联系人手机号
	 */
	private String contactPhone;

	/**
	 * 描述
	 */
	private String remark;

	/**
	 * 预留排序字段
	 */
	private Integer number;

	/**
	 * 上级组织编码
	 */
	private String parentGroupCode;

	/**
	 * 上级组织名称
	 */
	private String parentGroupName;

	/**
	 * 四级地址数组
	 */
	private List<Long> addressList;

}