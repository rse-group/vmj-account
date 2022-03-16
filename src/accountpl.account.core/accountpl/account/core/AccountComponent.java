package accountpl.account.core;

public abstract class AccountComponent implements Account{
	public int balance;
	public int overdraft_limit;

	public AccountComponent() {

	} 

	public abstract Boolean update(int x);
}
