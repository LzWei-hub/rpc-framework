package com.ciwei.spring;

import com.ciwei.annotation.RpcReference;
import com.ciwei.annotation.RpcService;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.enums.RpcRequestTransportEnum;
import com.ciwei.extension.ExtensionLoader;
import com.ciwei.factory.SingletonFactory;
import com.ciwei.provider.ServiceProvider;
import com.ciwei.provider.impl.ZkServiceProviderImpl;
import com.ciwei.proxy.RpcClientProxy;
import com.ciwei.remoting.transport.RpcRequestTransport;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * 在创建 bean 之前调用此方法，以查看该类是否带有 Comments
 * Create by LzWei on 2025/4/16
 */
@Slf4j
public class SpringBeanPostProcessor implements BeanPostProcessor {
    // 服务提供者，用于发布服务
    private final ServiceProvider serviceProvider;
    // RPC请求传输实现，用于客户端发送请求
    private final RpcRequestTransport rpcClient;

    public SpringBeanPostProcessor() {
        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
        this.rpcClient = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).getExtension(RpcRequestTransportEnum.NETTY.getName());
    }

    /**
     * 在bean初始化之前进行处理
     * 主要处理带有RpcService注解的bean，构建RpcServiceConfig并发布服务
     *
     * @param bean      要处理的bean实例
     * @param beanName  bean名称
     * @return          处理后的bean实例
     * @throws BeansException  处理过程中可能抛出的异常
     */
    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 检查bean类是否带有RpcService注解
        if (bean.getClass().isAnnotationPresent(RpcService.class)) {
            log.info("[{}] is annotated with  [{}]", bean.getClass().getName(), RpcService.class.getCanonicalName());
            // 获取 RpcService 注解
            RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
            // 构建 RpcServiceProperties
            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group(rpcService.group())
                    .version(rpcService.version())
                    .service(bean).build();
            // 发布服务
            serviceProvider.publishService(rpcServiceConfig);
        }
        return bean;
    }

    /**
     * 在bean初始化之后进行处理
     * 主要处理带有RpcReference注解的字段，创建代理并注入
     *
     * @param bean      要处理的bean实例
     * @param beanName  bean名称
     * @return          处理后的bean实例
     * @throws BeansException  处理过程中可能抛出的异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 检查字段是否带有RpcReference注解
            RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                // 构建 RpcServiceConfig
                RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                        .group(rpcReference.group())
                        .version(rpcReference.version()).build();
                // 创建RpcClientProxy实例
                RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceConfig);
                // 获取代理对象
                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                // 设置字段可访问
                declaredField.setAccessible(true);
                try {
                    // 将代理对象注入字段
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}
