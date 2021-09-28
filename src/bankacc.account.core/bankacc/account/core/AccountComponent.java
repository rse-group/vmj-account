package bankacc.account.core;

public abstract class AccountComponent implements Account {
    public static int OVERDRAFT_LIMIT = 0;

	//@ invariant balance >= OVERDRAFT_LIMIT;
	public static int balance;
    protected int id;

    	
	/*@
	 @ ensures balance == 0;
	 @*/
	public AccountComponent() {
	}

    public AccountComponent(int id, int balance) {
        this.id = id;
        this.balance = balance;
        System.out.println("\nAccount "+id+" is successfully created. \nInitial balance: "+balance);
    }

    public abstract int withdraw(int x);
    public abstract int deposit(int x);
    /*@
	 @ ensures !\result ==> balance == \old(balance);
	 @ ensures \result ==> balance == \old(balance) + x; 
	 @*/
    public abstract boolean update(int x);

   
}
