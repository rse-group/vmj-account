package accountpl.account.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import accountpl.account.AccountFactory;
import prices.auth.vmj.annotations.Restricted;
//add other required packages

public class AccountResourceImpl extends AccountResourceComponent{

	// @Restriced(permission = "")
    @Route(url="call/account/save")
    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Account account = createAccount(vmjExchange);
		accountRepository.saveObject(account);
		return getAllAccount(vmjExchange);
	}

    public Account createAccount(VMJExchange vmjExchange){
		String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
		int balance = Integer.parseInt(balanceStr);

		String overdraft_limitStr = (String) vmjExchange.getRequestBodyForm("overdraft_limit");
		int overdraft_limit = Integer.parseInt(overdraft_limitStr);

		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id_account = Integer.parseInt(idStr);
		
		//to do: fix association attributes
		
		Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance, overdraft_limit, id_account);
			return account;
	}

	public Account createAccount(VMJExchange vmjExchange, int id){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
		int balance = Integer.parseInt(balanceStr);

		String overdraft_limitStr = (String) vmjExchange.getRequestBodyForm("overdraft_limit");
		int overdraft_limit = Integer.parseInt(overdraft_limitStr);

		//to do: fix association attributes
		
		Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance, overdraft_limit);
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
		Account account = accountRepository.getObject(id);

		String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
		account.setBalance(Integer.parseInt(balanceStr));
		String overdraftlimitStr = (String) vmjExchange.getRequestBodyForm("overdraft_limit");
		account.setOverdraft_limit(Integer.parseInt(overdraftlimitStr));
		
		//to do: fix association attributes

		accountRepository.updateObject(account);

		account = accountRepository.getObject(id);
        return account.toHashMap();
		
	}

	// @Restriced(permission = "")
    @Route(url="call/account/detail")
    public HashMap<String, Object> getAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		String idStr = vmjExchange.getGETParam("id_account"); 
		int id = Integer.parseInt(idStr);
		Account account = accountRepository.getObject(id);
		return account.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/account/list")
    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange){
		List<Account> accountList = accountRepository.getAllObject("account_impl");
		return transformAccountListToHashMap(accountList);
	}

    public List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> accountList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < accountList.size(); i++) {
            resultList.add(accountList.get(i).toHashMap());
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
		accountRepository.deleteObject(id);
		return getAllAccount(vmjExchange);
	}

    @Route(url="call/account/balanceupdate")
	public List<HashMap<String,Object>> updateBalance(VMJExchange vmjExchange) {
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		Account account = accountRepository.getObject(id);
		
    	String amountStr = (String) vmjExchange.getRequestBodyForm("amount");
    	int amount = Integer.parseInt(amountStr);
    	account.update(amount);
    	
    	int balance = account.getBalance();
		account.setBalance(balance+amount);
		
		accountRepository.updateObject(account);

		account = accountRepository.getObject(id);
        return account.toHashMap();
	}
}
