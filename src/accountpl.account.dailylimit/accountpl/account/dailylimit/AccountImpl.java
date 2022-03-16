package accountpl.account.dailylimit;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	public int dailyLimit;
	public int withdraw;
	
	public AccountImpl(AccountComponent record, int dailyLimit, int withdraw) {
		super(record);
		this.dailyLimit = dailyLimit;
		this.withdraw = withdraw;
	}


	public Boolean update(int x) {
		// TODO: implement this method
	}
}
