package accountpl.account.core;

public abstract class AccountDecorator extends AccountComponent{
	public AccountComponent record;
		
	public AccountDecorator (AccountComponent record) {
		this.record = record;
	}

	public boolean update(int x) {
		return record.update(x);
	}
}

