package vmj.routing.route;

import java.lang.reflect.Method;

public class Router {

    public static void route(Object object) {
        Class<?> objClass = object.getClass();
        Method[] classMethods = objClass.getMethods();

        for (Method method : classMethods) {
            if (method.isAnnotationPresent(Route.class)) {
                handleMethod(method, object);
            }
        }
    }

    public static void handleMethod(Method method, Object object) {
        VMJServer vmjServer = VMJServer.getInstance();
        Route annotation = method.getAnnotation(Route.class);

        vmjServer.createURL(annotation.url(), object, method);
    }

    public static void bindMethod(String methodName, Object object) {
        Class<?> objClass = object.getClass();
        Method method = null;

        Class[] cArg = new Class[1];
        cArg[0] = VMJExchange.class;

        try {
            method = objClass.getMethod(methodName, cArg);
        } catch (Exception e) {
            // TODO: handle exception
            System.exit(30);
        }

        if (method.isAnnotationPresent(Route.class)) {
            handleMethod(method, object);
        }
    }

}