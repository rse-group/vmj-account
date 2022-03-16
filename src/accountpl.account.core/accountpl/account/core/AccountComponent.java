package accountpl.account.core;

public abstract class AccountComponent implements Account{
	public int balance;
	public int overdraft_limit;

	public AccountComponent() {

	} 

	public AccountComponent(int balance, int overdraft_limit) {
		this.balance = balance;
		this.overdraft_limit = overdraft_limit;
	}

 
	public abstract Boolean update(int x);
}
