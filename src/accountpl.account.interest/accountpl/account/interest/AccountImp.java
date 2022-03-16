package accountpl.account.interest;

import accountpl.account.core.AccountImp;
import accountpl.account.core.AccountImp;
import accountpl.account.core.AccountImp;

public class AccountImp extends AccountImp {

	public int interest_rate;
	
	public AccountImp(AccountImp record, int interest_rate, AccountImpl account) {
		super(record);
		this.interest_rate = interest_rate;
	}


	public int calculateInterest() {
		// TODO: implement this method
	}
}
