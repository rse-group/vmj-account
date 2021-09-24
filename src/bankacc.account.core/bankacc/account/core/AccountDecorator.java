package bankacc.account.core;

import bankacc.account.core.AccountComponent;

public abstract class AccountDecorator extends AccountComponent{
    public AccountComponent account;
    
    public AccountDecorator(AccountComponent account){
        this.account = account;
    }


}