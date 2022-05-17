package cn.demo.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class DemoFactoryBean implements FactoryBean {

	@Override
	public Object getObject() throws Exception {
		return new DemoBean() {
			@Override
			public void test() {
				System.out.println("我是一个 通过FactoryBean获取的类");
			}
		};
	}

	@Override
	public Class<?> getObjectType() {
		return DemoBean.class;
	}

	@Override
	public boolean isSingleton() {
		return FactoryBean.super.isSingleton();
	}
}
