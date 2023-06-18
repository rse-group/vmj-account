package accountpl.account.overdraft;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import accountpl.account.core.AccountResourceDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountResourceComponent;
import accountpl.account.core.AccountDecorator;
import accountpl.account.AccountFactory;

import prices.auth.vmj.annotations.Restricted;

public class AccountResourceImpl extends AccountResourceDecorator {
    public AccountResourceImpl (AccountResourceComponent record) {
    	super(record);
    }

    // @Restriced(permission = "")
    @Route(url="call/overdraft/save")
    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Account account  = createAccount(vmjExchange);
		accountRepository.saveObject(account);
		return getAllAccount(vmjExchange);
	}

    public Account createAccount(VMJExchange vmjExchange){
		String limitStr = (String) vmjExchange.getRequestBodyForm("overdraft_limit");
		int overdraft_limit = Integer.parseInt(limitStr);
		
		Account account = record.createAccount(vmjExchange);
		Account deco = AccountFactory.createAccount("accountpl.account.overdraft.AccountImpl", account, overdraft_limit);
			return deco;
	}
    
    public Account createAccount(VMJExchange vmjExchange, int id){
		String limitStr = (String) vmjExchange.getRequestBodyForm("overdraft_limit");
		int overdraft_limit = Integer.parseInt(limitStr);
		
        Account savedAccount = accountRepository.getObject(id);
        int recordAccountId = (((AccountDecorator) savedAccount).getRecord()).getId_account();

		Account account = record.createAccount(vmjExchange, recordAccountId);
		Account deco = AccountFactory.createAccount("accountpl.account.overdraft.AccountImpl", id, account, overdraft_limit);
			return deco;
	}

    // @Restriced(permission = "")
    @Route(url="call/overdraft/updateAccount")
    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
        String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
        int id = Integer.parseInt(idStr);
		Account account = accountRepository.getObject(id);
		account = createAccount(vmjExchange, id);
		accountRepository.updateObject(account);

		account = accountRepository.getObject(id);
        return account.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/overdraft/detail")
    public HashMap<String, Object> getAccount(VMJExchange vmjExchange){
		return record.getAccount(vmjExchange);
	}

	// @Restriced(permission = "")
    @Route(url="call/overdraft/list")
    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange){
		List<Account> accountList = accountRepository.getAllObject("account_overdraft");
		System.out.println("tes list call");
		System.out.println(accountList);
		return transformAccountListToHashMap(accountList);
	}

	// @Restriced(permission = "")
    @Route(url="call/overdraft/delete")
    public List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		accountRepository.deleteObject(id);
		return getAllAccount(vmjExchange);
	}

}

