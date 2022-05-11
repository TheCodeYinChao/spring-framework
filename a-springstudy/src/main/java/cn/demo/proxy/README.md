# Sring aop

## uml

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9tbWJpei5xcGljLmNuL3N6X21tYml6X3BuZy94aWNFSmhXbEswNkFKeHU2eldSYnM3aWM4WGQwbVpDbFdSV0pRNkNBeHVsN2licW10V25lWnZ5SmZndnowNExvTHZBYmN3MFZmcEdVblhCVGpwOUhoeFo3dy82NDA?x-oss-process=image/format,png)

### 自动方式（右边）



### 手动方式（左边）

![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9tbWJpei5xcGljLmNuL3N6X21tYml6X3BuZy94aWNFSmhXbEswNkFKeHU2eldSYnM3aWM4WGQwbVpDbFdST050TkVib1lTYnUyN3ZuaWFMaWNHQko2RVZEV2FjNkhWTjRjdWIzZW9rOE0yYU93NzdEYWlhN2pnLzY0MA?x-oss-process=image/format,png)

**ProxyFactory方式**
这种是硬编码的方式，**<u>可以脱离spring直接使用，用到的比较多，自动化方式创建代理中都是依靠ProxyFactory来实现的</u>**，所以这种方式的原理大家一定要了解，上篇文章中已经有介绍过了，不清楚的可以去看一下：[Spring系列第32篇：AOP核心源码、原理详解](https://mp.weixin.qq.com/s?__biz=MzA5MTkxMDQ4MQ%3D%3D&idx=1&mid=2648934930&scene=21&sn=4030960657cc72006122ef8b6f0de889#wechat_redirect)

**AspectJProxyFactory方式**
AspectJ是一个面向切面的框架，是目前最好用，最方便的AOP框架，Spring将其集成进来了，通过Aspectj提供的一些功能实现aop代理非常方便，下篇文章将详解。

**ProxyFactoryBean方式**
Spring环境中给指定的bean创建代理的一种方式，本文主要介绍这个。

**<u>ProxyFactoryBean</u>**
这个类实现了一个接口FactoryBean，FactoryBean不清楚的可以看一下：[Spring系列第5篇：创建bean实例这些方式你们都知道？](https://blog.csdn.net/likun557/article/details/104284840)

ProxyFactoryBean就是通过FactoryBean的方式来给指定的bean创建一个代理对象。

创建代理，有3个信息比较关键：

**需要增强的功能**，这个放在通知（Advice）中实现

**目标对象（target）**：表示你需要给哪个对象进行增强

**代理对象（proxy）**：将增强的功能和目标对象组合在一起，然后形成的一个代理对象，通过代理对象来访问目标对象，起到对目标对象增强的效果。

使用ProxyFactoryBean也是围绕着3部分来的，ProxyFactoryBean使用的步骤：

```text
1.创建ProxyFactoryBean对象
2.通过ProxyFactoryBean.setTargetName设置目标对象的bean名称，目标对象是spring容器中的一个bean
3.通过ProxyFactoryBean。setInterceptorNames添加需要增强的通知
		
4.将ProxyFactoryBean注册到Spring容器，假设名称为proxyBean
5.从Spring查找名称为proxyBean的bean，这个bean就是生成好的代理对象
```



### **ProxyFactoryBean**

`interceptorNames`用来指定拦截器的bean名称列表，常用的2种方式。

- 批量的方式

- 非批量的方式

  

**批量的方式**
使用方法

proxyFactoryBean.setInterceptorNames("需要匹配的bean名称*");
需要匹配的bean名称后面跟上一个*，可以用来批量的匹配，如：interceptor*，此时spring会从容器中找到下面2中类型的所有bean，bean名称以interceptor开头的将作为增强器

```
org.springframework.aop.Advisor
org.aopalliance.intercept.Interceptor
```


这个地方使用的时候需要注意，批量的方式注册的时候，如果增强器的类型不是上面2种类型的，比如下面3种类型通知，我们需要将其包装为Advisor才可以，而MethodInterceptor是Interceptor类型的，可以不用包装为Advisor类型的。

```java
MethodBeforeAdvice（方法前置通知）
AfterReturningAdvice（方法后置通知）
ThrowsAdvice（异常通知）
```

案例cn.demo.proxy.proxyFactoryBeanbatch。

**非批量的方式**
用法

非批量的方式，需要注册多个增强器，需明确的指定多个增强器的bean名称，多个增强器按照参数中指定的顺序执行，如

```java
proxyFactoryBean.setInterceptorNames("advice1","advice2");
```

advice1、advice2对应的bean类型必须为下面列表中指定的类型，类型这块比匹配的方式范围广一些

```java
MethodBeforeAdvice（方法前置通知）
AfterReturningAdvice（方法后置通知）
ThrowsAdvice（异常通知）
org.aopalliance.intercept.MethodInterceptor（环绕通知）
org.springframework.aop.Advisor（顾问）
```

案例cn.demo.proxy.proxyFactoryBeanmanual。