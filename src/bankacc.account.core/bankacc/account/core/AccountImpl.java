package bankacc.account.core;
import bankacc.account.core.Account;
import bankacc.account.core.AccountComponent;


public class AccountImpl extends AccountComponent {

    public AccountImpl(int id, int balance)
    {
        super(id, balance);
    }

    public int withdraw(int x){
        int a = super.withdraw(x);
        return a;
    }

    public int deposit(int x){
        int a = super.deposit(x);
        return a;
    }

}
