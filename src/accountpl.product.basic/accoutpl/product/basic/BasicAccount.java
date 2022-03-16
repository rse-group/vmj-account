package accountpl.product.basic;
import accountpl.account.core.*;
import accountpl.account.AccountFactory;


public class BasicAccount {

    public static void main (String[] args)
    {
        Account first = AccountFactory.createAccount("accountpl.account.core.AccountImpl");
        first.update(50000);
        first.update(10000);
        

        Account second = AccountFactory.createAccount("accountpl.account.core.AccountImpl");
        second.update(10000);
        second.update(-30000);

    }
    
}
