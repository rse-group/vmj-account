package vmj.routing.route;

import java.util.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import prices.auth.core.AuthPayload;
import prices.auth.core.exceptions.AuthException;
import prices.auth.vmj.VMJAuthFactory;
import prices.auth.vmj.VMJAuthorization;
import prices.auth.vmj.annotations.Restricted;
import prices.auth.vmj.annotations.Restrictions;
import prices.auth.vmj.annotations.RestrictCRUD;
import prices.auth.vmj.annotations.CRUDRestrictions;
import prices.auth.vmj.enums.CRUDMethod;

public class AuthHandler {
    public static boolean authorize(VMJExchange exchange, String permissionName) throws IOException {
        VMJAuthorization authObj = (VMJAuthorization) (new VMJAuthFactory().createAuth());
        authObj.setRequest(exchange.getHttpExchange());
        try {
            AuthPayload authPayload = authObj.authorize(permissionName);
            exchange.setAuthPayload(authPayload);
        } catch (AuthException ex) {
            System.out.println(ex);
            ex.printStackTrace();
            VMJServer.sendABSFailedResponse(exchange.getHttpExchange(), 401, "\"" + ex.getMessage() + "\"");
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            VMJServer.sendABSFailedResponse(exchange.getHttpExchange(), 500, "\"" + ex.getMessage() + "\"");
            return false;
        } catch (Error ex) {
            System.out.println(ex);
            ex.printStackTrace();
            VMJServer.sendABSFailedResponse(exchange.getHttpExchange(), 500, "\"" + ex.getMessage() + "\"");
            return false;
        }
        return true;
    }

    public static boolean isAdministrator(VMJExchange exchange) throws Exception {
        if (exchange.getAuthPayload() == null) {
            return false;
        }
        VMJAuthorization authObj = (VMJAuthorization) (new VMJAuthFactory().createAuth());
        boolean isAdmin = authObj.isAdministrator(exchange.getAuthPayload());
        System.out.println("Is currently logged in user an admin? " + Boolean.toString(isAdmin));
        return isAdmin;
    }

    public static boolean invokeObjectLevelPermission(String methodName, Object targetObject, VMJExchange exchange) throws Exception {
        if (!AuthHandler.isAdministrator(exchange) && !methodName.equals("")) {
            Method methodPermCheck = targetObject.getClass().getMethod(
                methodName, new Class[]{VMJExchange.class});
            if (!Modifier.isAbstract(methodPermCheck.getModifiers())) {
                return (Boolean) methodPermCheck.invoke(targetObject, exchange);
            } else {
                System.out.println("Custom permission method invocation bypassed because " +
                    "this CRUD endpoint is binded to an abstract class.");
            }
        }
        return true;
    }

    public static boolean invokeObjectLevelPermission(String methodName, Class clazz, VMJExchange exchange) throws Exception {
        if (!AuthHandler.isAdministrator(exchange) && !methodName.equals("")) {
            Method methodPermCheck = clazz.getMethod(
                methodName, new Class[]{VMJExchange.class});
            if (!Modifier.isAbstract(methodPermCheck.getModifiers())) {
                return (Boolean) methodPermCheck.invoke(null, exchange);
            } else {
                System.out.println("Custom permission method invocation bypassed because " +
                    "this CRUD endpoint is binded to an abstract class.");
            }
        }
        return true;
    }

    public static boolean authorizeMethodInvocation(Method targetMethod, Object object, VMJExchange exchange) throws Exception {
        boolean authorized = true;
        List<Restricted> perms = AuthHandler.chooseRelevantPermission(targetMethod);
        for (Restricted perm : perms) {
            authorized = (AuthHandler.authorize(exchange, perm.permissionName()) &&
                AuthHandler.invokeObjectLevelPermission(perm.customPermissionMethod(), object, exchange));
            if (authorized) return true;
        }
        return authorized;
    }

    public static List<RestrictCRUD> chooseRelevantPermission(Class clazz, CRUDMethod methodType) {
        System.out.println("Is Multi-Perms Annotation Present: " + Boolean.toString(clazz.isAnnotationPresent(CRUDRestrictions.class)));
        System.out.println("Is Single Perm Annotation Present: " + Boolean.toString(clazz.isAnnotationPresent(RestrictCRUD.class)));
        List<RestrictCRUD> result = new ArrayList<>();
        if (clazz.isAnnotationPresent(CRUDRestrictions.class)) {
            CRUDRestrictions permsRaw = (CRUDRestrictions) clazz.getAnnotation(CRUDRestrictions.class);
            for (RestrictCRUD permObj : permsRaw.restrictions()) {
                for (CRUDMethod restrictedMethodType : permObj.allowedMethods()) {
                    if (restrictedMethodType.equals(methodType)) {
                        result.add(permObj);
                        break;
                    }
                }
            }
        } else if (clazz.isAnnotationPresent(RestrictCRUD.class)) {
            RestrictCRUD permObj = (RestrictCRUD) clazz.getAnnotation(RestrictCRUD.class);
            for (CRUDMethod restrictedMethodType : permObj.allowedMethods()) {
                if (restrictedMethodType.equals(methodType)) {
                    result.add(permObj);
                    break;
                }
            }
        }
        return result;
    }

    public static List<Restricted> chooseRelevantPermission(Method targetMethod) {
        System.out.println("Is Multi-Perms Annotation Present: " + Boolean.toString(targetMethod.isAnnotationPresent(Restrictions.class)));
        System.out.println("Is Single Perm Annotation Present: " + Boolean.toString(targetMethod.isAnnotationPresent(Restricted.class)));
        List<Restricted> result = new ArrayList<>();
        if (targetMethod.isAnnotationPresent(Restrictions.class)) {
            Restrictions permsRaw = (Restrictions) targetMethod.getAnnotation(Restrictions.class);
            result.addAll(Arrays.asList(permsRaw.restrictions()));
        } else if (targetMethod.isAnnotationPresent(Restricted.class)) {
            Restricted permObj = (Restricted) targetMethod.getAnnotation(Restricted.class);
            result.add(permObj);
        }
        return result;
    }

    public static boolean authorizeCRUD(Class clazz, CRUDMethod methodType, VMJExchange exchange) throws Exception {
        boolean authorized = true;
        List<RestrictCRUD> perms = AuthHandler.chooseRelevantPermission(clazz, methodType);
        for (RestrictCRUD perm : perms) {
            System.out.println("Permission: " + perm.permissionName());
            authorized = AuthHandler.authorize(exchange, perm.permissionName()) &&
                AuthHandler.invokeObjectLevelPermission(perm.customPermissionMethod(), clazz, exchange);
            if (authorized) return true;
        }
        return authorized;
    }
}
