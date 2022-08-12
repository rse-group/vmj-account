package accountpl.account.interest;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	protected int interest_rate;
	public AccountImpl(AccountComponent record, int interest_rate) {
		super(record);
		this.interest_rate = interest_rate;
	}

	public int getInterest_rate() {
		return this.interest_rate;
	}

	public void setInterest_rate(int interest_rate) {
		this.interest_rate = interest_rate;
	}

}
