package accountpl.product.dailylimit;
import accountpl.account.core.*;
import accountpl.account.AccountFactory;


public class DailyLimitAccount {

    public static void main (String[] args) {
    	Account first = AccountFactory.createAccount("accountpl.account.dailylimit.AccountImpl",AccountFactory.createAccount("accountpl.account.core.AccountImpl",1));
        first.update(50000);
        first.update(10000);
		System.out.println("Current balance: "+((accountpl.account.dailylimit.AccountImpl)(first)).balance);
        

        Account second = AccountFactory.createAccount("accountpl.account.core.AccountImpl",2);
        second.update(10000);
        second.update(-11000);
        System.out.println("Current balance: "+((accountpl.account.core.AccountImpl)(second)).balance);
        
        Account third = AccountFactory.createAccount("accountpl.account.core.AccountImpl",3);
        Account third_over = AccountFactory.createAccount("accountpl.account.dailylimit.AccountImpl", third);
        third_over.update(10000);
        third_over.update(5000);
        third_over.update(-500);
        third_over.update(-11000);
        System.out.println("Current balance: "+((accountpl.account.dailylimit.AccountImpl)(third_over)).balance);
    }
    
}
