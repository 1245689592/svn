package com.cm.common.query;

import java.util.Date;

/**
 * 查询封装父类
 * @author yunlu
 *
 */
public class Query {
	
	/**默认页码为1*/
	private int pageNo=1;

	/**默认每页数量为10*/
	private int pageSize=10;
	
	private String keywords;
	
	private Integer deleteFlag;
	
	private Date startTime;
	
	private Date endTime;
	
	private int total;
	
	private String orderBy;
	/**0 desc 1 asc 默认null null为不排序*/
	private Integer orderType;
	
	private String loginUserId;
	
	private String loginName;
	
	private String loginIP;
	
	private String loginRoleCode;
	
	private String ids;
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getLoginRoleCode() {
		return loginRoleCode;
	}

	public void setLoginRoleCode(String loginRoleCode) {
		this.loginRoleCode = loginRoleCode;
	}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}	
	
}
