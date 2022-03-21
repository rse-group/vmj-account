package accountpl.account.dailylimit;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	public int dailyLimit=-1000;
	public int withdraw=0;
	
	public AccountImpl(AccountComponent record) {
		super(record);
		System.out.println("Account: "+record.id_account+ " is restricted with daily limit");
	}


	public boolean update(int x) {
		int newWithdraw = withdraw;
		if (x < 0)  {
			newWithdraw += x;
			if (newWithdraw < dailyLimit) {
				System.out.println("Withdraw "+x+ " is over the limit");
				return false;
			}
		}
		if (!record.update(x))
			return false;
		withdraw = newWithdraw;
		balance = balance+x;
		return true;
	}
}
