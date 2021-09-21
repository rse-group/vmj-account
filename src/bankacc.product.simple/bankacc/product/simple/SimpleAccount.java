package bankacc.product.simple;
import bankacc.account.core.*;


public class SimpleAccount {

    public static void main (String[] args)
    {
        Account first = new AccountImpl (1, 100000);
        System.out.println("- Balance: "+first.deposit(50000));
        System.out.println("- Balance: "+first.withdraw(10000));
        

        Account second = new AccountImpl (2, 10000);
        System.out.println("- Balance: "+second.deposit(10000));
        System.out.println("- Balance: "+second.withdraw(30000));

    }
    
}
