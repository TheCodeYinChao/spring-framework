package cn.demo.proxy.proxyFactoryBeanbatch;

import cn.demo.proxy.Service1;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

public class MainConfig2 {
	//注册目标对象
	@Bean
	public Service1 service1() {
		return new Service1();
	}

	//定义一个增强器：interceptor1，内部是一个前置通知，需要将其包装为Advisor类型的
	@Bean
	public Advisor interceptor1() {
		MethodBeforeAdvice advice = new MethodBeforeAdvice() {
			@Override
			public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
				System.out.println("准备调用：" + method);
			}
		};
		DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
		advisor.setAdvice(advice);
		return advisor;
	}

	//定义一个增强器：interceptor2
	@Bean
	public MethodInterceptor interceptor2() {
		MethodInterceptor methodInterceptor = new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				long starTime = System.nanoTime();
				Object result = invocation.proceed();
				long endTime = System.nanoTime();
				System.out.println(invocation.getMethod() + ",耗时(纳秒)：" + (endTime - starTime));
				return result;
			}
		};
		return methodInterceptor;
	}

	//注册ProxyFactoryBean
	@Bean
	public ProxyFactoryBean service1Proxy() {
		//1.创建ProxyFactoryBean
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		//2.设置目标对象的bean名称
		proxyFactoryBean.setTargetName("service1");
		//3.设置拦截器的bean名称列表，此处批量注册
		proxyFactoryBean.setInterceptorNames("interceptor*"); //@1
		return proxyFactoryBean;
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig2.class);
		//获取代理对象，代理对象bean的名称为注册ProxyFactoryBean的名称，即：service1Proxy
		Service1 bean = context.getBean("service1Proxy", Service1.class);
		System.out.println("----------------------");
		//调用代理的方法
		bean.m1();
		System.out.println("----------------------");
		//调用代理的方法
		bean.m2();
	}
}
