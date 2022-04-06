package cn.demo;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		HelloService bean = context.getBean(HelloService.class);
		bean.hello();
	}
}
