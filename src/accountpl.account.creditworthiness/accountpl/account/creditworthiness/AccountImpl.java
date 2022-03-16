package accountpl.account.creditworthiness;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	
	public AccountImpl(AccountComponent record, ) {
		super(record);
	}


	public Boolean credit(int amount) {
		// TODO: implement this method
	}
}
