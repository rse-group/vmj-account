package bankacc.account.core;
import bankacc.account.core.Account;
import bankacc.account.core.AccountComponent;


public class AccountImpl extends AccountComponent {

    public AccountImpl(int id, int balance) {
        super(id, balance);
    }

    public int withdraw(int x){
        if (update(x)){
            System.out.print("Withdraw: "+x);
            balance = balance-x;
            System.out.println(" | Balance: "+balance);
        }
        else 
            System.out.println("Withdraw "+x+" failed, balance less than 0");
        return balance;
    }

    public int deposit(int x){
        System.out.print("Deposit: "+x);
        balance = balance+x;
        System.out.println(" | Balance: "+balance);
        return balance;
    }

     
	public boolean update(int x) {
		int newBalance = balance - x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		else
		    return true;
	}
}
