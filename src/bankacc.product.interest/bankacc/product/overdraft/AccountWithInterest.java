package bankacc.product.interest;
import bankacc.account.core.*;
import bankacc.account.AccountFactory;


public class AccountWithInterest {

    public static void main (String[] args)
    {
        Account first = AccountFactory.createAccount("bankacc.account.interest.AccountImpl", AccountFactory.createAccount("bankacc.account.core.AccountImpl", 1, 100000));
        first.deposit(50000);
        first.withdraw(10000);
        

        Account second = AccountFactory.createAccount("bankacc.account.interest.AccountImpl", AccountFactory.createAccount("bankacc.account.core.AccountImpl", 2, 10000));
        second.deposit(10000);
        second.withdraw(5000);
        second.withdraw(20000);
        second.withdraw(5000);
        int interest = ((bankacc.account.interest.AccountImpl)second).calculateInterest();
        int newbalance= ((bankacc.account.interest.AccountImpl)second).balance +=interest;
        System.out.println("Balance with interest: " +newbalance);
    }
    
}
