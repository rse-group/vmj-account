package accountpl.account.core;

public abstract class AccountComponent implements Account{
	public int balance;
	public int overdraft_limit;
	public int id_account;

	public AccountComponent() {
		
	}
	
	public AccountComponent(int id) {
		this.id_account = id;
	} 
	
	public abstract boolean update(int x);
}
