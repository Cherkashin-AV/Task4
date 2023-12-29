package ru.vtb.javaCourse.Task4.LogTransformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.vtb.javaCourse.Task4.Converters.Convertable;
import ru.vtb.javaCourse.Task4.Converters.DateConverter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
public class LogTransformBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Convertable && bean.getClass().isAnnotationPresent(LogTransformation.class)){
            LogTransformation logTransformation =  bean.getClass().getAnnotation(LogTransformation.class);
            return getProxy(bean, logTransformation.showDetail());
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private  Object getProxy(Object obj, Boolean showDetail){
        ClassLoader classLoader = obj.getClass().getClassLoader();
        Class[] interfaces = {Convertable.class};
        Object a = Proxy.newProxyInstance(classLoader, interfaces, new LogTransformInvocationHandler(obj, showDetail));
        return a;
    }
}

class LogTransformInvocationHandler implements InvocationHandler {
    Object object;
    Boolean showDetail;
    static final Logger log =
            LoggerFactory.getLogger(DateConverter.class);

    public LogTransformInvocationHandler(Object object, Boolean showDetail) {
        this.object = object;
        this.showDetail = showDetail;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.warn(String.format("Запуск метода proxy-объекта. Класс: %s, метод: %s",object.getClass(), method.getName()));
        if (showDetail && args != null){
            log.warn("Аргументы: ");
            Parameter[] parametrs = method.getParameters();
            for (int i = 0; i < args.length; i++) {
                log.warn(String.format("\t%s: %s"
                        , parametrs[i].getName()
                        , argToString(args[i])
                        )
                );
            }
            log.warn("==========");
        }
        Object result = method.invoke(object, args);
        log.warn(String.format("Метод выполнен. Класс: %s, метод: %s", object.getClass(), method.getName()));
        if (showDetail && result != null){
            log.warn(String.format("\t Результат: %s", argToString(result)));
        }
        return result;
    }

    private String argToString(Object arg){
        return arg instanceof Collection ? "Коллекция [" + ((Collection)arg).size() + " элемента]" : arg.toString();
    }
}