package bankacc.product.overdraft;
import bankacc.account.core.*;
import bankacc.account.AccountFactory;


public class OverdraftAccount {

    public static void main (String[] args)
    {
        Account first = AccountFactory.createAccount("bankacc.account.overdraft.AccountImpl", AccountFactory.createAccount("bankacc.account.core.AccountImpl", 1, 100000));
        System.out.println("- Balance: "+first.deposit(50000));
        System.out.println("- Balance: "+first.withdraw(10000));
        

        Account second = AccountFactory.createAccount("bankacc.account.overdraft.AccountImpl", AccountFactory.createAccount("bankacc.account.core.AccountImpl", 2, 10000));
        System.out.println("- Balance: "+second.deposit(10000));
        System.out.println("- Balance: "+second.withdraw(30000));

    }
    
}
