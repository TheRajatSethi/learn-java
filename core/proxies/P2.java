package proxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class P2 {

    /**
     * Dynamic Proxy
     */
    static class AuditAdvice implements InvocationHandler{
        Object target;
        public AuditAdvice(Object target){
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Starting Log .... ");
            return method.invoke(target, args);
        }
    }

    static class Service implements IService{
        public void doSomething(){
            System.out.println("Doing something.");
        }
    }

    static interface IService{
        void doSomething();
    }

    /**
     * Demo
     */
    public static void main(String[] args) {

        Service s = new Service();
        ClassLoader cl = P2.class.getClassLoader();
        /*
        Proxy.newProxyInstance is a static method which dynamically generates the class on the fly which implements
        the interfaces in the array passed in e.g. new Class[]{IService.class}.
         */
        IService proxiedService = (IService) Proxy.newProxyInstance(cl, new Class[]{IService.class}, new AuditAdvice(s));
        proxiedService.doSomething();
    }

}
