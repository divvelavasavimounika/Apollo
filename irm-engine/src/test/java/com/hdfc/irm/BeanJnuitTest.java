package com.hdfc.irm;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;

public class BeanJnuitTest {
	private Class clazz;

	public BeanJnuitTest(Class clazz) {
		super();
		this.clazz = clazz;
	}

	public void test() throws InstantiationException, IllegalAccessException {
		Method[] methods = clazz.getDeclaredMethods();
		Object obj = clazz.newInstance();
		Stream.of(methods).forEach(method -> {
			try {
				Parameter[] params = method.getParameters();
				Object[] objs = createParams(params);
				method.invoke(obj, objs);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		});
	}

	private Object[] createParams(Parameter[] params) {
		Object[] obj = new Object[params.length];
		int i = 0;
		for (Parameter parameter : params) {
			obj[i++] = getTypeDefaultValue(parameter.getParameterizedType().getTypeName());
		}
		return obj;
	}

	public static final Object getTypeDefaultValue(String typeName) {
		if (typeName.equals("byte"))
			return 0;
		if (typeName.equals("short"))
			return 0;
		if (typeName.equals("int"))
			return 0;
		if (typeName.equals("long"))
			return 0L;
		if (typeName.equals("char"))
			return "";
		if (typeName.equals("float"))
			return 0.0F;
		if (typeName.equals("double"))
			return 0.0d;
		if (typeName.equals("boolean"))
			return false;
		else
			return null;
	}
}
