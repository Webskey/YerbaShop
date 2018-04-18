package org.yerbashop.dummybuilders;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UsersModelBuilder {

	private Object o;

	public UsersModelBuilder(Class<?> clazz) {
		try {
			Constructor<?> constructor = clazz.getConstructor();
			Object o = constructor.newInstance();
			clazz.getMethod("setUsername", String.class).invoke(o, "username");
			clazz.getMethod("setPassword", String.class).invoke(o, "password");
			clazz.getMethod("setFirstname", String.class).invoke(o, "firstname");
			clazz.getMethod("setLastname", String.class).invoke(o, "lastname");
			clazz.getMethod("setEmail", String.class).invoke(o, "email@email.com");
			clazz.getMethod("setPhoneNr", String.class).invoke(o, "123789456");
			clazz.getMethod("setEnabled", boolean.class).invoke(o, true);
			this.o=o;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public Object getObject() {
		return o;
	}
}
