package bankacc.account.core;

import bankacc.account.core.AccountComponent;

public abstract class AccountDecorator extends AccountComponent{
    
    public AccountDecorator(int id, int balance){
        super(id,balance);
    }


}