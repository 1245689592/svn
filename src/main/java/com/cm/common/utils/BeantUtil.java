package com.cm.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 对象转换工具
 * 
 * @author xs
 * 
 */
public class BeantUtil {

	/**
	 * 复制Properties(包含fieldValue为null的属性)
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static Object copyProperties(Object source, Object target)
			throws Exception {
		if (null == source)
			return source;

		Class<?> classType = source.getClass();
		Field[] fields = classType.getDeclaredFields();
		int length = fields.length;
		for (int i = 0; i < length; i++) {
			String fieldName = fields[i].getName();
			Object fieldValue = getFieldValue(source, fieldName);
			setFieldValue(target, fieldName, fieldValue);
		}
		return target;
	}

	/**
	 * 复制属性(不包含fieldValue为null的属性)
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static Object copyPropertiesNotNull(Object source, Object target)
			throws Exception {
		if (null == source || target == null)
			return null;

		Class<?> classType = source.getClass();
		Field[] fields = classType.getDeclaredFields();

		int length = fields.length;

		for (int i = 0; i < length; i++) {

			String fieldName = fields[i].getName();
			Object fieldValue = getFieldValue(source, fieldName);
			if (fieldValue != null) {
				setFieldValue(target, fieldName, fieldValue);
			}
		}
		return target;
	}
	/**
	 * 复制属性(不包含fieldValue为null和为empty的属性)
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static Object copyPropertiesNotNullAndNotEmpty(Object source, Object target)
			throws Exception {
		if (null == source || target == null)
			return null;

		Class<?> classType = source.getClass();
		Field[] fields = classType.getDeclaredFields();

		int length = fields.length;

		for (int i = 0; i < length; i++) {

			String fieldName = fields[i].getName();
			Object fieldValue = getFieldValue(source, fieldName);
			if (fieldValue != null && !"".equals(fieldValue.toString().trim())) {
				setFieldValue(target, fieldName, fieldValue);
			}
		}
		return target;
	}
	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value) throws Exception {
		Field field = getDeclaredField(object, fieldName);

		if (null != field) {
			makeAccessible(field);
			try {
				// 非final属性则修改该属性的值
				if (!Modifier.isFinal(field.getModifiers()))
					field.set(object, value);
			} catch (IllegalAccessException e) {
				// throw e;
			} catch (IllegalArgumentException e2) {
			}
		}
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 * 
	 * @throws NoSuchFieldException
	 */
	public static Object getFieldValue(final Object object,
			final String fieldName) throws IllegalAccessException,
			NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object value = null;
		try {
			value = field.get(object);
		} catch (IllegalAccessException e) {
			throw e;
		}
		return value;
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getDeclaredField(final Object object,
			final String fieldName) throws NoSuchFieldException {
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {// NOSONAR
				// throw e;
			}
		}
		return null;
	}

	/**
	 * 强行设置Field可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	public static void convertObjToMap(Object obj, Map map) throws Exception {
		if (obj != null) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = obj.getClass().getDeclaredField(fields[i].getName());
				f.setAccessible(true);
				Object o = f.get(obj);
				map.put(fields[i].getName(), o);
				f.setAccessible(false);
			}
		}
	}
	
	public static Map convertObjToMap(Object obj) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		if (obj != null) {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field f = obj.getClass().getDeclaredField(fields[i].getName());
				f.setAccessible(true);
				Object o = f.get(obj);
				map.put(fields[i].getName(), o);
				f.setAccessible(false);
			}
		}
		return map;
	}
}
