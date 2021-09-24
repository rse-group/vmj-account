package bankacc.account.overdraft;

import bankacc.account.core.AccountDecorator;
import bankacc.account.core.AccountComponent;


public class AccountImpl extends AccountDecorator{
    final static int OVERDRAFT_LIMIT = -5000;

    public AccountImpl(AccountComponent account){
        super(account);
    }
    
}
