package accountpl.product.basicaccount;
import accountpl.account.core.AccountController;
import accountpl.account.AccountControllerFactory;

import vmj.routing.route.VMJServer;
import vmj.routing.route.Router;

import vmj.hibernate.integrator.HibernateUtil;
import org.hibernate.cfg.Configuration;

import vmj.object.mapper.VMJDatabaseMapper;

import prices.auth.vmj.model.UserControllerFactory;
import prices.auth.vmj.model.RoleControllerFactory;
import prices.auth.vmj.model.core.UserController;
import prices.auth.vmj.model.core.RoleController;


public class BasicAccount {

    public static void main (String[] args){
        activateServer("localhost", 7776);

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(accountpl.account.core.AccountComponent.class);
		configuration.addAnnotatedClass(accountpl.account.core.AccountImpl.class);
		//configuration.addAnnotatedClass(accountpl.account.core.AccountDecorator.class);
		configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserComponent.class);
		configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserImpl.class);
		configuration.addAnnotatedClass(prices.auth.vmj.model.passworded.UserPasswordedImpl.class);
		configuration.addAnnotatedClass(prices.auth.vmj.model.core.RoleComponent.class);
		configuration.addAnnotatedClass(prices.auth.vmj.model.core.RoleImpl.class);
		configuration.addAnnotatedClass(prices.auth.vmj.model.core.UserRoleImpl.class);

        configuration.buildMappings();
		HibernateUtil.buildSessionFactory(configuration);

        createObjectsAndBindEndPoints();

    }

    public static void activateServer(String hostName, int portNumber) {
		VMJServer vmjServer = VMJServer.getInstance(hostName, portNumber);
		try {
			vmjServer.startServerGeneric();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

    public static void generateTables() {
		try {
			System.out.println("== GENERATING TABLES ==");
			VMJDatabaseMapper.generateTable("prices.auth.vmj.model.passworded.UserImpl", false);
			VMJDatabaseMapper.generateTable("prices.auth.vmj.model.core.RoleImpl", false);
			VMJDatabaseMapper.generateTable("prices.auth.vmj.model.core.UserRoleImpl", false);
			System.out.println();
		} catch (Exception e) {
			System.out.println("Skipping generate tables...");
		} catch (Error e) {
			System.out.println("Skipping generate tables...");
		}
	}

    public static void generateCRUDEndpoints() {
		System.out.println("== CRUD ENDPOINTS ==");
		VMJServer vmjServer = VMJServer.getInstance();

		/**
		 * AUTH BASE MODELS
		 */
		vmjServer.createABSCRUDEndpoint("users", "auth_user", "prices.auth.vmj.model.core.UserImpl",
				VMJDatabaseMapper.getTableColumnsNames("prices.auth.vmj.model.core.UserImpl", false));
		vmjServer.createABSCRUDEndpoint("users", "auth_user_passworded", "prices.auth.vmj.model.passworded.UserImpl",
				VMJDatabaseMapper.getTableColumnsNames("prices.auth.vmj.model.passworded.UserImpl", true));
		vmjServer.createABSCRUDEndpoint("roles", "auth_role", "prices.auth.vmj.model.core.RoleImpl",
				VMJDatabaseMapper.getTableColumnsNames("prices.auth.vmj.model.core.RoleImpl", false));
		vmjServer.createABSCRUDEndpoint("user-roles", "auth_user_role", "prices.auth.vmj.model.core.UserRoleImpl",
				VMJDatabaseMapper.getTableColumnsNames("prices.auth.vmj.model.core.UserRoleImpl", false));

		System.out.println();

	}

	public static void createObjectsAndBindEndPoints() {
		AccountController accountCore = AccountControllerFactory
				.createAccountController("accountpl.account.core.AccountControllerImpl");
    
		UserController userCore = UserControllerFactory
				.createUserController("prices.auth.vmj.model.core.UserControllerImpl");
		UserController userPassworded = UserControllerFactory
				.createUserController("prices.auth.vmj.model.passworded.UserPasswordedControllerDecorator", userCore);
		RoleController roleCore = RoleControllerFactory
				.createRoleController("prices.auth.vmj.model.core.RoleControllerImpl");

		System.out.println();

		//endpoint binding
		System.out.println("endpoints binding");
		Router.route(accountCore);
		// Router.route(userCore);
		Router.route(userPassworded);
		Router.route(roleCore);
		System.out.println();
	}


    
}
