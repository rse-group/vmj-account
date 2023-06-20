package accountpl.account.interest;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import prices.auth.vmj.annotations.Restricted;

import accountpl.account.core.AccountResourceDecorator;
import accountpl.account.core.AccountResourceComponent;
import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.AccountFactory;


public class AccountResourceImpl extends AccountResourceDecorator {
    public AccountResourceImpl (AccountResourceComponent record) {
        super(record);
    }

    // @Restriced(permission = "")
    @Route(url="call/interest/save")
    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Account account = createAccount(vmjExchange);
		accountRepository.saveObject(account);
		System.out.println(account);
		return getAllAccount(vmjExchange);
	}

    public Account createAccount(VMJExchange vmjExchange){
		String interest_rateStr = (String) vmjExchange.getRequestBodyForm("interest_rate");
        int interest_rate = Integer.parseInt(interest_rateStr);
		Account account = record.createAccount(vmjExchange);
		Account deco = AccountFactory.createAccount("accountpl.account.interest.AccountImpl", account, interest_rate);
			return deco;
	}

    
    public Account createAccount(VMJExchange vmjExchange, int id) {
		String interest_rateStr = (String) vmjExchange.getRequestBodyForm("interest_rate");
        int interest_rate = Integer.parseInt(interest_rateStr);
        Account savedAccount = accountRepository.getObject(id);
        int recordAccountId = (((AccountDecorator) savedAccount).getRecord()).getId_account();
        Account account = record.createAccount(vmjExchange, recordAccountId);
		Account deco = AccountFactory.createAccount("accountpl.account.interest.AccountImpl", id, account, interest_rate);
		return deco;
    }

    // @Restriced(permission = "")
    @Route(url="call/interest/update")
    public HashMap<String, Object> update(VMJExchange vmjExchange){
        String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
        int id = Integer.parseInt(idStr);
        Account account = accountRepository.getObject(id);
        account = createAccount(vmjExchange, id);
        accountRepository.updateObject(account);
        account = accountRepository.getObject(id);
        return account.toHashMap();
	}

	// @Restriced(permission = "")
    @Route(url="call/interest/detail")
    public HashMap<String, Object> get(VMJExchange vmjExchange){
		return record.getAccount(vmjExchange);
	}

	// @Restriced(permission = "")
    @Route(url="call/interest/list")
    public List<HashMap<String,Object>> getAll(VMJExchange vmjExchange){
		List<Account> accountList = accountRepository.getAllObject("account_interest");
		System.out.println("cek interest list");
		System.out.println(accountList);
		
		return transformAccountListToHashMap(accountList);
	}

	// @Restriced(permission = "")
    @Route(url="call/interest/delete")
    public List<HashMap<String,Object>> delete(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
		int id = Integer.parseInt(idStr);
		accountRepository.deleteObject(id);
		return getAllAccount(vmjExchange);
	}

	protected int calculateInterest() {	
		return 5/100;
	}
}



