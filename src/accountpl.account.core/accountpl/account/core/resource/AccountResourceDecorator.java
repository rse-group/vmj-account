package accountpl.account.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class AccountResourceDecorator extends AccountResourceComponent{
	protected AccountResourceComponent record;

    public AccountResourceDecorator(AccountResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange){
		return record.saveAccount(VMJExchange vmjExchange)
	}

    public Account createAccount(VMJExchange vmjExchange){
		return record.createAccount(VMJExchange vmjExchange)
	}

    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange){
		return record.updateAccount(VMJExchange vmjExchange)
	}

    public HashMap<String, Object> getAccount(VMJExchange vmjExchange){
		return record.getAccount(VMJExchange vmjExchange)
	}

    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange){
		return record.getAllAccount(VMJExchange vmjExchange)
	}

    public List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> AccountList){
		return record.transformAccountListToHashMap(List<Account> AccountList)
	}

    public List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange){
		return record.deleteAccount(VMJExchange vmjExchange)
	}

	public Boolean update(int x) {
		return record.update(x);
	}
}
