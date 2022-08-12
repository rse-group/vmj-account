package accountpl.account.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import accountpl.account.AccountFactory;
import prices.auth.vmj.annotations.Restricted;
//add other required packages

public class AccountResourceImpl extends AccountResourceComponent{
	protected AccountResourceComponent record;

	// @Restriced(permission = "")
    @Route(url="call/account/save")
    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Account account = createAccount(vmjExchange);
		AccountRepository.saveObject(account);
		return getAllAccount(vmjExchange);
	}

    public Account createAccount(VMJExchange vmjExchange){
		int balance = (int) vmjExchange.getRequestBodyForm("balance");
		int overdraft_limit = (int) vmjExchange.getRequestBodyForm("overdraft_limit");
		int id_account = (int) vmjExchange.getRequestBodyForm("id_account");
		
		//to do: fix association attributes
		
		Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance, overdraft_limit, id_account);
			return account;
	}

    // @Restriced(permission = "")
    @Route(url="call/account/update")
    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		
		Account account = AccountRepository.getObject(id);
		account.setBalance((String) vmjExchange.getRequestBodyForm("balance");
		account.setOverdraft_limit((String) vmjExchange.getRequestBodyForm("overdraft_limit");
		
		//to do: fix association attributes
		
	}

	// @Restriced(permission = "")
    @Route(url="call/account/detail")
    public HashMap<String, Object> getAccount(VMJExchange vmjExchange){
		String idStr = vmjExchange.getGETParam("id_account"); 
		int id = Integer.parseInt(idStr);
		Account account = AccountRepository.getObject(id);
		return account.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/account/list")
    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange){
		List<Account> AccountList = AccountRepository.getAllObject("account_impl");
		return transformAccountListToHashMap(AccountList);
	}

    public List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> AccountList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < AccountList.size(); i++) {
            resultList.add(AccountList.get(i).toHashMap());
        }

        return resultList;
	}

	// @Restriced(permission = "")
    @Route(url="call/account/delete")
    public List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		AccountRepository.deleteObject(id);
		return getAllAccount(vmjExchange);
	}

	public Boolean update(int x) {
		// TODO: implement this method
	}
}
