package accountpl.account.dailylimit;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

public class AccountImpl extends AccountDecorator {

	protected int dailyLimit;
	protected int withdraw;
	public AccountImpl(AccountComponent record, int dailyLimit, int withdraw) {
		super(record);
		this.dailyLimit = dailyLimit;
		this.withdraw = withdraw;
	}

	public int getDailyLimit() {
		return this.dailyLimit;
	}

	public void setDailyLimit(int dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	public int getWithdraw() {
		return this.withdraw;
	}

	public void setWithdraw(int withdraw) {
		this.withdraw = withdraw;
	}

}
