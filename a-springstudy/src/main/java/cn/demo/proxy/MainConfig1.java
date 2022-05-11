package cn.demo.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

@Configuration
public class MainConfig1 {
	//注册目标对象
	@Bean
	public Service1 service1() {
		return new Service1();
	}

	//注册一个前置通知
	@Bean
	public MethodBeforeAdvice beforeAdvice() {
		MethodBeforeAdvice advice = new MethodBeforeAdvice() {
			@Override
			public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
				System.out.println("准备调用：" + method);
			}
		};
		return advice;
	}

	//注册一个后置通知
	@Bean
	public MethodInterceptor costTimeInterceptor() {
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
		//3.设置拦截器的bean名称列表，此处2个（advice1和advice2)
			//1 批量的方式proxyFactoryBean.setInterceptorNames("interceptor*");//则会从spring找匹配上interceptor的东西
			//2 非批量的方式
		/*需要匹配的bean名称后面跟上一个*，可以用来批量的匹配，如：interceptor*，此时spring会从容器中找到下面2中类型的所有bean，bean名称以interceptor开头的将作为增强器
		org.springframework.aop.Advisor
		org.aopalliance.intercept.Interceptor
		这个地方使用的时候需要注意，批量的方式注册的时候，如果增强器的类型不是上面2种类型的，比如下面3种类型通知，我们需要将其包装为Advisor才可以，而MethodInterceptor是Interceptor类型的，可以不用包装为Advisor类型的。
		MethodBeforeAdvice（方法前置通知）
		AfterReturningAdvice（方法后置通知）
		ThrowsAdvice（异常通知）

	  	@Bean
		public Advisor interceptor1() {
			MethodBeforeAdvice advice = new MethodBeforeAdvice() {
				@Override
				public void before(Method method, Object[] args, @Nullable Object target) throws Throwable {
					System.out.println("准备调用：" + method);
				}
		};
		//包装前置通知让其变为spring中能被识别的Advisor或Interceptor类型###################
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(advice);
        return advisor;
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

		*/
		proxyFactoryBean.setInterceptorNames("beforeAdvice", "costTimeInterceptor");
		return proxyFactoryBean;
	}
}
