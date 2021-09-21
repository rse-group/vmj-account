package bankacc.account.core;

public abstract class AccountComponent implements Account {
protected int balance;
protected int id;

public AccountComponent(int balance, int id)
{
    balance = this.balance;
    id = this.id;
}
public int withdraw(int x){
    balance = balance-x;
    return balance;
}

public int deposit(int x){
    balance = balance+x;
    return balance;
}

}
