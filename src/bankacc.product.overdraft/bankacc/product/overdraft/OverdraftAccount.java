package bankacc.product.overdraft;
import bankacc.account.core.*;
import bankacc.account.AccountFactory;


public class OverdraftAccount {

    public static void main (String[] args)
    {
        Account first = AccountFactory.createAccount("bankacc.account.overdraft.AccountImpl", AccountFactory.createAccount("bankacc.account.core.AccountImpl", 1, 100000));
        first.deposit(50000);
        first.withdraw(10000);
        

        Account second = AccountFactory.createAccount("bankacc.account.overdraft.AccountImpl", AccountFactory.createAccount("bankacc.account.core.AccountImpl", 2, 10000));
        second.deposit(10000);
        second.withdraw(5000);
        second.withdraw(20000);
        second.withdraw(5000);
    }
    
}
