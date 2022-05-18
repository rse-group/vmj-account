package accountpl.account.core;
import java.util.*;

import vmj.hibernate.integrator.DaoUtil;
import vmj.routing.route.VMJExchange;

public abstract class AccountResourceComponent implements AccountResource {
    protected DaoUtil<Account> accountDao;

    public AccountResourceComponent(){
        this.accountDao = new DaoUtil<Account>(accountpl.account.core.AccountComponent.class);
    }

    public abstract Account createAccount(VMJExchange vmjexchage);
   // public abstract Account createAccount(VMJExchange vmjexchage, String id);
    public abstract List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> updateAccount(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> accountList);

    
}
