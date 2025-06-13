package accountpl.account.dailylimit;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity(name="account_dailylimit")
@Table(name="account_dailylimit")
public class AccountImpl extends AccountDecorator {

	protected int dailyLimit;
	protected int withdraw;
	public AccountImpl() {
		super();
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
