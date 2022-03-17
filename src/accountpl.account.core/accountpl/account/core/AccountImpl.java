package accountpl.account.core;

public class AccountImpl extends AccountComponent {
	public int balance = 0;
	public int overdraft_limit = 0;

	public AccountImpl(int id) {
		super(id);
		System.out.println("\n=== Basic Account: "+id);
	}

	public Boolean update(int x) {
		// TODO: implement this method
		System.out.println("Add amount: "+x);
		int newBalance = balance + x;
		if (newBalance < overdraft_limit) {
			System.out.println("Update is failed");
			return false;
		}
		balance = newBalance;
		return true;
	}
}

