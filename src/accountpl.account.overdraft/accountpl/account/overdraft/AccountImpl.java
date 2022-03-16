package accountpl.account.overdraft;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	public int overdraft_limit=-5000;
	
	public AccountImpl(AccountComponent record) {
		super(record);
		System.out.println("=== Overdraft Account ===");
	}

	public Boolean update(int x) {
		// TODO: implement this method
		System.out.println("Add amount: "+x);
		int newBalance = balance + x;
		if (newBalance < overdraft_limit) {
			System.out.println("Update is failed");
			return false;}
		balance = newBalance;
		return true;
	}

}
