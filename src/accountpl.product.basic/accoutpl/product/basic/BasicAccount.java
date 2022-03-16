package accountpl.product.basic;
import accountpl.account.core.*;
import accountpl.account.AccountFactory;


public class BasicAccount {

    public static void main (String[] args)
    {
    	Account first = AccountFactory.createAccount("accountpl.account.core.AccountImpl");
    	System.out.println("- First Account");
        first.update(50000);
        first.update(10000);
		System.out.println("Current balance: "+((accountpl.account.core.AccountImpl)(first)).balance);
        

        Account second = AccountFactory.createAccount("accountpl.account.core.AccountImpl");
    	System.out.println("- Second Account");
        second.update(10000);
        second.update(-30000);
        System.out.println("Current balance: "+((accountpl.account.core.AccountImpl)(second)).balance);
        

    }
    
}
