package accountpl.account.core;

import java.util.*;

import accountpl.account.core.AccountResourceComponent;
import vmj.hibernate.integrator.DaoUtil;
import vmj.routing.route.VMJExchange;

public abstract class AccountResourceDecorator extends AccountResourceComponent {
    protected AccountResourceComponent account;

    public AccountResourceDecorator(AccountResourceComponent account){
        this.account=account;
    }

    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange) {
        return account.saveAccount(vmjExchange);
    }

    public Account createAccount(VMJExchange vmjExchange) {
        return account.createAccount(vmjExchange);
    }

    // public Account createAccount(VMJExchange vmjExchange, String id) {
    //     return account.createAccount(vmjExchange, id);
    // }

    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange) {
        return account.updateAccount(vmjExchange);
    }

    public HashMap<String, Object> getAccount(VMJExchange vmjExchange) {
        return account.getAccount(vmjExchange);
    }

    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange) {
        return account.getAllAccount(vmjExchange);
    }
    
    public List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange) {
        return account.deleteAccount(vmjExchange);
    }

    public List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> accountList) {
        return account.transformAccountListToHashMap(accountList);
    }

    
}
