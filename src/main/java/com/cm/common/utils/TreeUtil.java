package com.cm.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 树结构排序
 * @author yunlu
 *
 */
public class TreeUtil {
	
	private static String parentId="parentId";
	
	private static String id="id";
	
	private static String rootId="0";
	
	private static String children="children";
	/**
	 * 树排序
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> sort(List<T> list)throws Exception{
		return sort(list,parentId,rootId,id,children);
	}
	/**
	 * 树排序
	 * @param list
	 * @param parentIdName 指定parentid字段名
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> sort(List<T> list,String parentIdName)throws Exception{
		if(LogicUtil.isNullOrEmpty(parentIdName)){
			parentIdName=parentId;
		}
		return sort(list,parentIdName,rootId,id,children);
	}
	/**
	 * 树排序
	 * @param list
	 * @param parentIdName 指定parentid字段名
	 * @param rootIdValue 指定root节点字段名
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> sort(List<T> list,String parentIdName,String rootIdValue)throws Exception{
		if(LogicUtil.isNullOrEmpty(parentIdName)){
			parentIdName=parentId;
		}
		if(LogicUtil.isNullOrEmpty(rootIdValue)){
			rootIdValue=rootId;
		}
		return sort(list,parentIdName,rootIdValue,id,children);
	}
	/**
	 * 树排序
	 * @param list
	 * @param parentIdName 指定parentid字段名
	 * @param rootIdValue 指定root节点字段名
	 * @param idName 指定当前节点id字段名
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> sort(List<T> list,String parentIdName,String rootIdValue,String idName)throws Exception{
		if(LogicUtil.isNullOrEmpty(parentIdName)){
			parentIdName=parentId;
		}
		if(LogicUtil.isNullOrEmpty(rootIdValue)){
			rootIdValue=rootId;
		}
		if(LogicUtil.isNullOrEmpty(idName)){
			idName=id;
		}
		return sort(list,parentIdName,rootIdValue,idName,children);
	}
	/**
	 * 树排序
	 * @param list
	 * @param parentIdName 指定parentid字段名
	 * @param rootIdValue 指定root节点字段名
	 * @param idName 指定当前节点id字段名
	 * @param childrenName 指定children地段名
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> sort(List<T> list,String parentIdName,String rootIdValue,String idName,String childrenName)throws Exception{
		if(list==null||list.size()<=1){
			return list;
		}
		if(LogicUtil.isNullOrEmpty(parentIdName)){
			parentIdName=parentId;
		}
		if(LogicUtil.isNullOrEmpty(rootIdValue)){
			rootIdValue=rootId;
		}
		if(LogicUtil.isNullOrEmpty(idName)){
			idName=id;
		}
		if(LogicUtil.isNullOrEmpty(childrenName)){
			childrenName=children;
		}
		//定义一个map 做缓冲层;
		Map<String,List<T>> map = new HashMap<String,List<T>>();
		//初次排序 按parentId分组
		for(T t:list){
			if(t==null) {
				continue;
			}
			String tempParentId = BeanUtil.get(t, parentIdName).toString();
			List<T> tempList = map.get(tempParentId);
			if(tempList==null||tempList.size()==0){
				tempList=new ArrayList<T>();
				map.put(tempParentId, tempList);
			}
			tempList.add(t);
		}
		//二次排序 指定子集合
		for(T t:list){
			if(t==null){
				continue;
			}
			String tempId = BeanUtil.get(t, idName).toString();
			List<T> tempList = map.get(tempId);
			if(tempList!=null){
				BeanUtil.setChildren(t, childrenName, tempList);
			}
		}
		return map.get(rootIdValue);
	}
}

