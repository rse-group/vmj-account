package accountpl.account.overdraft;

import java.text.NumberFormat;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;
import accountpl.account.core.AccountResourceDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountResourceComponent;
import accountpl.account.core.AccountDecorator;
import accountpl.account.AccountFactory;

import vmj.auth.annotations.Restricted;

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
		return transformAccountListToHashMap(accountList);
	}
    
    // @Restriced(permission = "")
    @Route(url="call/overdraft/select-options")
    public List<HashMap<String,Object>> getAllOverdraftOptions(VMJExchange vmjExchange){
		List<Account> accountList = accountRepository.getAllObject("account_overdraft");
		return transformAccountListToOptions(accountList);
	}
	
	public List<HashMap<String, Object>> transformAccountListToHashMap(List<Account> accList) {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < accList.size(); i++) {
            resultList.add(accList.get(i).toHashMap());
        }

        return resultList;
    }
	
	 public List<HashMap<String, Object>> transformAccountListToOptions(List<Account> accountList) {
		List<HashMap<String, Object>> optionsList = new ArrayList<>();
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
		
		for (Account account : accountList) {
			HashMap<String, Object> option = new HashMap<>();
			HashMap<String, Object> accountMap = account.toHashMap();
			
			Object idAccount = accountMap.get("id_account");
			Object balance = accountMap.get("balance");
			Object overdraft_limit = accountMap.get("overdraft_limit");
			 
			String formattedBalance = currencyFormatter.format(balance);
			String formattedOverdraftLimit = currencyFormatter.format(overdraft_limit);

			StringBuilder nameBuilder = new StringBuilder();
			nameBuilder.append("Account ID: ").append(idAccount)
						.append(", Balance: ").append(formattedBalance)
						.append(", Overdraft Limit: ").append(formattedOverdraftLimit);

			option.put("id", idAccount);
			option.put("name", nameBuilder.toString());

			optionsList.add(option);
		}
		
		return optionsList;
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
    
    @Route(url="call/overdraft/withdraw")
    public HashMap<String,Object> withdrawAccount(VMJExchange vmjExchange){
    	if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
    	
    	String idSourceStr = (String) vmjExchange.getRequestBodyForm("id_account");
    	int idSource = Integer.parseInt(idSourceStr);
    	
    	String idTargetStr = (String) vmjExchange.getRequestBodyForm("id_account_target");
    	int idTarget = Integer.parseInt(idTargetStr);
    	
    	Account accountSource = accountRepository.getObject(idSource);
    	Account accountTarget = accountRepository.getObject(idTarget);
    	
    	int balanceSource = accountSource.getBalance();
    	int balanceTarget = accountTarget.getBalance();
    	
    	int limit = ((accountpl.account.overdraft.AccountImpl)accountSource).getOverdraft_limit();
    	
    	String amountStr = (String)vmjExchange.getRequestBodyForm("amount");
    	
    	int amount = Integer.parseInt(amountStr);
    	
        int newBalanceSource = balanceSource - amount;

        if (newBalanceSource < -limit) {
            System.out.println("Withdraw failed: Exceeds overdraft limit");
            throw new BadRequestException("Withdraw failed: Amount exceeds overdraft limit",400,4000);
        } else {
            accountSource.setBalance(newBalanceSource);
            accountTarget.setBalance(balanceTarget + amount);
            System.out.println("Withdraw success");
        }
    	
    	accountRepository.updateObject(accountSource);
    	accountRepository.updateObject(accountTarget);
    	accountTarget = accountRepository.getObject(idTarget);
        return accountTarget.toHashMap();
    }

}

