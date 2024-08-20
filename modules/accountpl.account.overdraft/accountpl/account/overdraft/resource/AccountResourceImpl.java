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
    @Route(url="call/overdraft/update")
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
		System.out.println(accountList);
		return transformAccountListToHashMap(accountList);
	}
	
	public List<HashMap<String, Object>> transformAccountListToHashMap(List<Account> accList) {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < accList.size(); i++) {
            resultList.add(accList.get(i).toHashMap());
        }

        return resultList;
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
		System.out.println(accountRepository);
		return getAllAccount(vmjExchange);
	}
    
    @Route(url="call/overdraft/balanceupdate")
	public HashMap<String,Object> updateBalance(VMJExchange vmjExchange) {
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		Account account = accountRepository.getObject(id);
		int nowbalance = account.getBalance();
		int limit = ((accountpl.account.overdraft.AccountImpl)account).getOverdraft_limit();

		String amountStr = (String) vmjExchange.getRequestBodyForm("amount");
		int amount = Integer.parseInt(amountStr);
		
		int total = nowbalance + amount;
		if (total < (0-limit))
			System.out.println("Transaction Failed");
		else {
			System.out.println("Transaction Success");
			account.setBalance(total);
		}

		accountRepository.updateObject(account);
		account = accountRepository.getObject(id);
        return account.toHashMap();
	}

}

