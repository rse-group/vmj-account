package accountpl.account.overdraft;

import accountpl.account.core.AccountImp;
import accountpl.account.core.AccountImp;
import accountpl.account.core.AccountImp;

public class AccountImp extends AccountImp {

	public int overdraft_limit;
	
	public AccountImp(AccountImp record, int overdraft_limit, AccountImpl account) {
		super(record);
		this.overdraft_limit = overdraft_limit;
	}


}
