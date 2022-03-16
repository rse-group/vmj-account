package accountpl.account.interest;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	public int interest_rate;
	
	public AccountImpl(AccountComponent record, int interest_rate) {
		super(record);
		this.interest_rate = interest_rate;
	}


	public int calculateInterest() {
		// TODO: implement this method
	}
}
