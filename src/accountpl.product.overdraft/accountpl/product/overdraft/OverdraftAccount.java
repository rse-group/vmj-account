package accountpl.product.overdraft;
import accountpl.account.core.*;
import accountpl.account.AccountFactory;


public class OverdraftAccount {

    public static void main (String[] args)
    {
    	Account first = AccountFactory.createAccount("accountpl.account.overdraft.AccountImpl",AccountFactory.createAccount("accountpl.account.core.AccountImpl"));
    	System.out.println("- First Account");
        first.update(50000);
        first.update(10000);
		System.out.println("Current balance: "+((accountpl.account.overdraft.AccountImpl)(first)).balance);
        

        Account second = AccountFactory.createAccount("accountpl.account.core.AccountImpl");
    	System.out.println("- Second Account");
        second.update(10000);
        second.update(-11000);
        System.out.println("Current balance: "+((accountpl.account.core.AccountImpl)(second)).balance);
        
        Account third = AccountFactory.createAccount("accountpl.account.overdraft.AccountImpl", second);
    	System.out.println("- Third Account");
        third.update(10000);
        third.update(-11000);
        System.out.println("Current balance: "+((accountpl.account.overdraft.AccountImpl)(third)).balance);
        

    }
    
}
