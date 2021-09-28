package bankacc.account.core;

public abstract class AccountComponent implements Account {
    public static int OVERDRAFT_LIMIT = 0;

	//@ invariant balance >= OVERDRAFT_LIMIT;
	public static int balance = 0;
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

    public int withdraw(int x){
        if (update(x)){
            System.out.print("Withdraw: "+x);
            balance = balance-x;
            System.out.println(" | Balance: "+balance);
        }
        else 
            System.out.println("Withdraw "+x+" failed, balance less than 0");

        return balance;
    }

    public int deposit(int x){
        System.out.print("Deposit: "+x);
        balance = balance+x;
        System.out.println(" | Balance: "+balance);
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
