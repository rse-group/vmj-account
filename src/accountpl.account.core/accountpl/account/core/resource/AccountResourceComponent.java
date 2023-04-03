package accountpl.account.core;
import java.util.*;

import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;
//add other required packages

public abstract class AccountResourceComponent implements AccountResource{
	protected RepositoryUtil<Account> accountRepository;

    public AccountResourceComponent(){
        this.accountRepository = new RepositoryUtil<Account>(accountpl.account.core.AccountComponent.class);
    }	

    public abstract List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange);
    public abstract Account createAccount(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> updateAccount(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> AccountList);
    public abstract List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange);

	public abstract boolean update(int x);
}