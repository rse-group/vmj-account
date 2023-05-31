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

		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id_account = Integer.parseInt(idStr);
		
		//to do: fix association attributes
		
		Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance, id_account);
			return account;
	}

	public Account createAccount(VMJExchange vmjExchange, int id){
		String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
		int balance = Integer.parseInt(balanceStr);

		//to do: fix association attributes
		
		Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance);
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
		//to do: fix association attributes

		accountRepository.updateObject(account);

		account = accountRepository.getObject(id);
        return account.toHashMap();
		
	}

	// @Restriced(permission = "")
    @Route(url="call/account/detail")
    public HashMap<String, Object> getAccount(VMJExchange vmjExchange){
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
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		accountRepository.deleteObject(id);
		return getAllAccount(vmjExchange);
	}

	public boolean update(int x) {
		return true;
	}
}
