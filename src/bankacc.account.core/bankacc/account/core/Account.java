package bankacc.account.core;

public interface Account {
    int withdraw(int x);
    int deposit(int x);
    boolean update(int x);
}
