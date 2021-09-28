package bankacc.account.overdraft;

import bankacc.account.core.AccountDecorator;
import bankacc.account.core.AccountComponent;


public class AccountImpl extends AccountDecorator{
    public AccountComponent account;
    public int OVERDRAFT_LIMIT = -5000;

    public AccountImpl(AccountComponent account){
        super(account);
    }
    public int withdraw(int x){
        if (update(x)){
            System.out.print("Withdraw: "+x);
            balance = balance-x;
            System.out.println(" | Balance: "+balance);
        }
        else 
            System.out.println("Withdraw "+x+" failed, balance less than overdraft limit: "+OVERDRAFT_LIMIT);
        return balance;
    }   

    public int deposit(int x){
        return super.deposit(x);
    }
    public boolean update(int x) {
		int newBalance = balance - x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		else
		    return true;
	}
}
