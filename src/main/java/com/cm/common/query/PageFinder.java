package com.cm.common.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageFinder<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 每页的记录数
	 */
	private int pageSize = 10;

	/**
	 * 当前页中存放的数据
	 */
	private List<T> data;

	/**
	 * 总记录数
	 */
	private int rowCount;

	/**
	 * 页数
	 */
	private int pageCount;

	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 是否有上一页
	 */
	private boolean hasPrevious = false;

	/**
	 * 是否有下一页
	 */
	private boolean hasNext = false;

	public PageFinder(int page, int rowCount) {
		this.page = page;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		refresh();
	}

	/**
	 * 构造方法
	 */
	public PageFinder(int page, int pageSize, int rowCount) {
		this.page = page;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		refresh();
	}

	/**
	 * 
	 */
	public PageFinder(int page, int pageSize, int rowCount, List<T> data) {
		this.page = page;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.pageCount = getTotalPageCount();
		this.data = data;
		refresh();
	}
	
	public PageFinder(Query query, List<T> data) {
		this.page = query.getPageNo();
		this.pageSize = query.getPageSize();
		this.rowCount = query.getTotal();
		this.pageCount = getTotalPageCount();
		this.data = data;
		refresh();
	}
	
	/**
	 * 取总页数
	 */
	private final int getTotalPageCount() {
		if (rowCount % pageSize == 0)
			return rowCount / pageSize;
		else
			return rowCount / pageSize + 1;
	}

	/**
	 * 刷新当前分页对象数据
	 */
	private void refresh() {
		if (pageCount <= 1) {
			hasPrevious = false;
			hasNext = false;
		} else if (page == 1) {
			hasPrevious = false;
			hasNext = true;
		} else if (page == pageCount) {
			hasPrevious = true;
			hasNext = false;
		} else {
			hasPrevious = true;
			hasNext = true;
		}
	}

	/**
	 * 取每页数据数
	 */
	public int getPageSize() {
		return pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public static <K> PageFinder<K> bulid(int page, int pageSize, List<K> data)throws Exception{
		List<K> list = new ArrayList<K>();
		for(int i=0;i<pageSize&&i<data.size();i++){
			list.add(data.get((page-1)*pageSize+i));
		}
		return new PageFinder<K>(page,pageSize,data.size(),list);
	}
}
