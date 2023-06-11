package accountpl.account.overdraft;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	protected int overdraft_limit;
	public AccountImpl(AccountComponent record, int overdraft_limit) {
		super(record);
		this.overdraft_limit = overdraft_limit;
	}

	public int getOverdraft_limit() {
		return this.overdraft_limit;
	}

	public void setOverdraft_limit(int overdraft_limit) {
		this.overdraft_limit = overdraft_limit;
	}

}
