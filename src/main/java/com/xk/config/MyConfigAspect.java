package com.xk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 1.导入aop模块
 * 2.定义日志切面类
 *   定义通知类型:
 *      --前置通知  目标方法执行之前执行
 *      --后置通知  目标方法执行之后执行
 *      --返回通知  目标方法正常返回之后执行
 *      --异常通知  目标方法出异常之后执行
 *      --环绕通知  动态代理，手动推进目标方法执行 joinPoint.proceed
 *
 * bean实例化
 * populate bean 属性
 *  初始化
 *      --调用实现了aware接口的方法
 *      --调用beanPostprocessor的postProcessBeforeInitialization
 *      --调用初始化方法(InitializingBean接口，init方法)
 *      --调用beanPostprocessor的postProcessAfterInitialization
 *
 * getBean --> doGetBean  -->getSingleton
 * createBean
 *  (BeanPostProcessor是在对象初始化创建前后调用，
 *  InstantiationAwareBeanPostProcessor在bean创建之前被调用)
 *   后置处理器先尝试返回代理对象
 *   创建bean
 *
 * @author kai.xu
 * @create 2020-12-30 9:11
 */
@EnableAspectJAutoProxy
@Configuration
public class MyConfigAspect {
}
