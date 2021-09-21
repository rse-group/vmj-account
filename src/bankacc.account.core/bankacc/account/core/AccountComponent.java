package bankacc.account.core;

public abstract class AccountComponent implements Account {
protected int balance;
protected int id;

public AccountComponent(int id, int balance)
{
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

}
