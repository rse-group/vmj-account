package bankacc.account.core;

public abstract class AccountComponent implements Account {
    final static int OVERDRAFT_LIMIT = 0;

	//@ invariant balance >= OVERDRAFT_LIMIT;
	int balance = 0;
    protected int id;

    	
	/*@
	 @ ensures balance == 0;
	 @*/
	public AccountComponent() {
	}

    public AccountComponent(int id, int balance) {
        this.id = id;
        this.balance = balance;
        System.out.println("Account "+id+" is successfully created. \nInitial balance: "+balance);
    }

    public int withdraw(int x){
        System.out.println("Withdraw: "+x);
        balance = balance-x;
        return balance;
    }

    public int deposit(int x){
        System.out.println("Deposit: "+x);
        balance = balance+x;
        return balance;
    }

    /*@
	 @ ensures !\result ==> balance == \old(balance);
	 @ ensures \result ==> balance == \old(balance) + x; 
	 @*/
	public boolean update(int x) {
		int newBalance = balance - x;
		if (newBalance < OVERDRAFT_LIMIT)
			return false;
		else
		    return true;
	}

}
