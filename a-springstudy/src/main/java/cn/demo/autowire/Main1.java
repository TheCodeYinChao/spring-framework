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
	 *  这是个失败的例子， 这里本来并不想通过xml的方式去设置bd的自动装配参数
	 *  但是测试中只能通过spring的扩展方式来进行修改bd的自动装配参数
	 */
	@Test
	public void test1() {
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserService.class);
		beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		DefaultListableBeanFactory parentBeanFactory = (DefaultListableBeanFactory)context.getBeanFactory();
		parentBeanFactory.registerBeanDefinition("userService",beanDefinitionBuilder.getBeanDefinition());
//		context.refresh(); //不能多次刷新 AnnotationConfigApplicationContext构造中已经执行refresh
		UserService bean = (UserService)context.getBean("userService");
		bean.queryByDao();

	}
}
