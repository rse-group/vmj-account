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
		return record.saveAccount(vmjExchange);
	}

    public Account createAccount(VMJExchange vmjExchange){
		return record.createAccount(vmjExchange);
	}

	public Account createAccount(VMJExchange vmjExchange, int id){
		return record.createAccount(vmjExchange, id);
	}

    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange){
		return record.updateAccount(vmjExchange);
	}

    public HashMap<String, Object> getAccount(VMJExchange vmjExchange){
		return record.getAccount(vmjExchange);
	}

    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange){
		return record.getAllAccount(vmjExchange);
	}
    
    public List<HashMap<String,Object>> getAllAccountOptions(VMJExchange vmjExchange){
		return record.getAllAccountOptions(vmjExchange);
	}
    
    public List<HashMap<String,Object>> getAllAccountIdOptions(VMJExchange vmjExchange){
		return record.getAllAccountIdOptions(vmjExchange);
	}


    public List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> accountList){
		return record.transformAccountListToHashMap(accountList);
	}
    
    public List<HashMap<String,Object>> transformAccountListToIdOptions(List<Account> accountList){
		return record.transformAccountListToIdOptions(accountList);
	}
    
    public List<HashMap<String,Object>> transformAccountListToOptions(List<Account> accountList){
		return record.transformAccountListToOptions(accountList);
	}

    public List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange){
		return record.deleteAccount(vmjExchange);
	}
    
    public HashMap<String, Object> updateBalance(VMJExchange vmjExchange){
		return record.updateBalance(vmjExchange);
	}
    
    public HashMap<String, Object> withdrawAccount(VMJExchange vmjExchange){
    	return record.transferAccount(vmjExchange);
    }
}
