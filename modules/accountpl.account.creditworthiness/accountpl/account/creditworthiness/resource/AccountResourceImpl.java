package accountpl.account.creditworthiness;

import accountpl.account.core.AccountResourceDecorator;
import accountpl.account.core.AccountImpl;
import accountpl.account.core.AccountResourceComponent;
import accountpl.account.AccountFactory;
import accountpl.account.core.Account;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import prices.auth.vmj.annotations.Restricted;

public class AccountResourceImpl extends AccountResourceDecorator {
    public AccountResourceImpl (AccountResourceComponent record) {
        super(record);
    }

    // @Restriced(permission = "")
    @Route(url="call/creditworthiness/save")
    public List<HashMap<String,Object>> save(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Account account = create(vmjExchange);
		accountRepository.saveObject(account);
		return getAll(vmjExchange);
	}

    public Account create(VMJExchange vmjExchange){
    	String interest_rateStr = (String) vmjExchange.getRequestBodyForm("interest_rate");
		double interest_rate = Double.parseDouble(interest_rateStr);
		Account account = record.createAccount(vmjExchange);
		Account deco = AccountFactory.createAccount("accountpl.creditworthiness.core.AccountImpl", account, interest_rate);
		return deco;
	}

    // @Restriced(permission = "")
    @Route(url="call/creditworthiness/update")
    public HashMap<String, Object> update(VMJExchange vmjExchange){
		// to do implement the method
    	return new HashMap<>();
	}

	// @Restriced(permission = "")
    @Route(url="call/creditworthiness/detail")
    public HashMap<String, Object> get(VMJExchange vmjExchange){
		return record.getAccount(vmjExchange);
	}

	// @Restriced(permission = "")
    @Route(url="call/creditworthiness/list")
    public List<HashMap<String,Object>> getAll(VMJExchange vmjExchange){
		List<Account> accountList = accountRepository.getAllObject("_impl");
		return transformAccountListToHashMap(accountList);
	}

//    public List<HashMap<String,Object>> transformListToHashMap(List<> List){
//		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
//        for(int i = 0; i < List.size(); i++) {
//            resultList.add(List.get(i).toHashMap());
//        }
//
//        return resultList;
//	}

	// @Restriced(permission = "")
    @Route(url="call/creditworthiness/delete")
    public List<HashMap<String,Object>> delete(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		accountRepository.deleteObject(id);
		return getAll(vmjExchange);
	}

	protected boolean credit(int amount) {
		// TODO: implement this method
		
		return true;
	}
}

