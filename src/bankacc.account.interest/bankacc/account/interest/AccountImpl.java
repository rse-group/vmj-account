package bankacc.account.interest;

import bankacc.account.core.AccountDecorator;
import bankacc.account.core.AccountComponent;


public class AccountImpl extends AccountDecorator{
    public AccountComponent account;
    final static int INTEREST_RATE = 2;

	int interest = 0;

    public AccountImpl(AccountComponent account){
        super(account);
    }

	/*@
	  @ ensures (balance >= 0 ==> \result >= 0) 
	  @   && (balance <= 0 ==> \result <= 0);
	  @*/
	/*@ pure @*/ public int calculateInterest() {
		return balance * INTEREST_RATE / 100;
	}

}
