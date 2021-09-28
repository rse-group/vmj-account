package bankacc.account.core;

import bankacc.account.core.AccountComponent;

public abstract class AccountDecorator extends AccountComponent{
    public AccountComponent account;
    
    public AccountDecorator(AccountComponent account){
        this.account = account;
    }

    public int deposit(int x){
        return  account.deposit(x);
    }

    public int withdraw(int x){
        return  account.withdraw(x);
    }

    public boolean update(int x){
        return  account.update(x);
    }



}