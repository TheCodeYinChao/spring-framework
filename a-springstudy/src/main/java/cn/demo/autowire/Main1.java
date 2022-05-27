package cn.demo.autowire;

import cn.demo.autowire.bytype.Config;
import cn.demo.autowire.bytype.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main1 {

	/**
	 * byType
	 */
	@Test
	public void test1() {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserService.class);
		beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		DefaultListableBeanFactory parentBeanFactory = (DefaultListableBeanFactory)context.getBeanFactory();
		parentBeanFactory.registerBeanDefinition("userService",beanDefinitionBuilder.getBeanDefinition());
//		context.refresh();
		UserService bean = (UserService)context.getBean("userService");
		bean.queryByDao();

	}
}
