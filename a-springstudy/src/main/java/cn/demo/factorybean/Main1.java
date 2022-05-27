package cn.demo.factorybean;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.*;


public class Main1 {

	/**
	 * 当前缀加& 则获取的是DemoFactoryBean类本身的实例对象
	 * 当前缀不加& 则获取的是DemoBean类的实例对象,这个类是通过DemoFactoryBean.getObject()来实例化获取的
	 */
	@Test
	public  void test1(){
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigBean.class);
		Object demoFactoryBean = context.getBean("&demoFactoryBean");
		assertTrue(demoFactoryBean instanceof DemoFactoryBean);
		Object factoryBean = context.getBean("demoFactoryBean");
		assertTrue(factoryBean instanceof DemoBean);
	}
}
