package accountpl.product.basicaccount;

import java.util.ArrayList;

import vmj.routing.route.VMJServer;
import vmj.routing.route.Router;
import vmj.hibernate.integrator.HibernateUtil;
import org.hibernate.cfg.Configuration;

import accountpl.account.AccountResourceFactory;
import accountpl.account.core.AccountResource;

import prices.auth.vmj.model.UserResourceFactory;
import prices.auth.vmj.model.RoleResourceFactory;
import prices.auth.vmj.model.core.UserResource;
import prices.auth.vmj.model.core.RoleResource;


public class BasicAccount {

	public static void main(String[] args) {
		activateServer("localhost", 7776);
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(accountpl.account.core.Account.class);
		configuration.addAnnotatedClass(accountpl.account.core.AccountComponent.class);
		configuration.addAnnotatedClass(accountpl.account.core.AccountDecorator.class);
		configuration.addAnnotatedClass(accountpl.account.core.AccountImpl.class);
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

	public static void createObjectsAndBindEndPoints() {
		System.out.println("== CREATING OBJECTS AND BINDING ENDPOINTS ==");
		AccountResource account = AccountResourceFactory
			.createAccountResource(
			"accountpl.account.core.AccountResourceImpl"
			);
		
		UserResource userCore = UserResourceFactory
				.createUserResource("prices.auth.vmj.model.core.UserResourceImpl");
		UserResource userPassworded = UserResourceFactory
				.createUserResource("prices.auth.vmj.model.passworded.UserPasswordedResourceDecorator", userCore);
		RoleResource roleCore = RoleResourceFactory
				.createRoleResource("prices.auth.vmj.model.core.RoleResourceImpl");


		System.out.println("account endpoints binding");
		Router.route(account);
		
		
		Router.route(userPassworded);
		Router.route(roleCore);
		System.out.println();
	}

}