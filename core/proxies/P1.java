package proxies;

public class P1 {

    interface IService{
        void doSomething();
    }


    static class Service implements IService{
        @Override
        public void doSomething() {
            System.out.println("Doing Something...");
        }
    }

    static class ServiceProxy extends Service{
        @Override
        public void doSomething() {
            System.out.println("Do something in proxy before call to actual method");
            super.doSomething();
            System.out.println("Do something in proxy after call to actual method");
        }
    }

    static class CustomIocContainer{

//        public CustomIocContainer() {
//            Service s = new Service();
//            ServiceProxy sp = new ServiceProxy();
//        }
//
//        Object getInstanceOf(String className){
//
//        }

    }


    /**
     * Demo
     */
    public static void main(String[] args) {
    }
}
