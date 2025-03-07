package accountpl.account.core;

import java.util.*;
import java.text.NumberFormat;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;
import accountpl.account.AccountFactory;
import vmj.auth.annotations.Restricted;

//add other required packages
public class AccountResourceImpl extends AccountResourceComponent {

    // @Restriced(permission = "")
    @Route(url = "call/account/save")
    public List<HashMap<String, Object>> saveAccount(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        Account account = createAccount(vmjExchange);
        accountRepository.saveObject(account);
        return getAllAccount(vmjExchange);
    }

    public Account createAccount(VMJExchange vmjExchange) {
        String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
        int balance = Integer.parseInt(balanceStr);

        String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
        int id_account = Integer.parseInt(idStr);

        Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance, id_account);
        return account;
    }

    public Account createAccount(VMJExchange vmjExchange, int id) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
        int balance = Integer.parseInt(balanceStr);

        Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", balance);
        return account;
    }

    // @Restriced(permission = "")
    @Route(url = "call/account/update")
    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
        int id = Integer.parseInt(idStr);
        Account account = accountRepository.getObject(id);

        String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
        account.setBalance(Integer.parseInt(balanceStr));

        accountRepository.updateObject(account);

        account = accountRepository.getObject(id);
        return account.toHashMap();

    }

    // @Restriced(permission = "")
    @Route(url = "call/account/detail")
    public HashMap<String, Object> getAccount(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = vmjExchange.getGETParam("id_account");
        int id = Integer.parseInt(idStr);
        Account account = accountRepository.getObject(id);
        return account.toHashMap();
    }

    // @Restriced(permission = "")
    @Route(url = "call/account/list")
    public List<HashMap<String, Object>> getAllAccount(VMJExchange vmjExchange) {
        List<Account> accountList = accountRepository.getAllObject("account_impl", AccountImpl.class.getName());
        return transformAccountListToHashMap(accountList);
    }

    // @Restriced(permission = "")
    @Route(url = "call/account/select-options")
    public List<HashMap<String, Object>> getAllAccountOptions(VMJExchange vmjExchange) {
        List<Account> accountList = accountRepository.getAllObject("account_impl", AccountImpl.class.getName());
        return transformAccountListToOptions(accountList);
    }
    
    @Route(url = "call/account/select-options-id")
    public List<HashMap<String, Object>> getAllAccountIdOptions(VMJExchange vmjExchange) {
        List<Account> accountList = accountRepository.getAllObject("account_impl", AccountImpl.class.getName());
        return transformAccountListToIdOptions(accountList);
    }

    public List<HashMap<String, Object>> transformAccountListToHashMap(List<Account> accountList) {
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < accountList.size(); i++) {
            resultList.add(accountList.get(i).toHashMap());
        }

        return resultList;
    }
    
    public List<HashMap<String, Object>> transformAccountListToIdOptions(List<Account> accountList) {
        List<HashMap<String, Object>> optionsList = new ArrayList<>();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Account account : accountList) {
            HashMap<String, Object> option = new HashMap<>();
            HashMap<String, Object> accountMap = account.toHashMap();

            Object idAccount = accountMap.get("id_account");

            StringBuilder nameBuilder = new StringBuilder();
            nameBuilder.append("Account ID: ").append(idAccount);

            option.put("id", idAccount);
            option.put("name", nameBuilder.toString());

            optionsList.add(option);
        }

        return optionsList;
    }


    public List<HashMap<String, Object>> transformAccountListToOptions(List<Account> accountList) {
        List<HashMap<String, Object>> optionsList = new ArrayList<>();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        for (Account account : accountList) {
            HashMap<String, Object> option = new HashMap<>();
            HashMap<String, Object> accountMap = account.toHashMap();

            Object idAccount = accountMap.get("id_account");
            Object balance = accountMap.get("balance");

            String formattedBalance = currencyFormatter.format(balance);

            StringBuilder nameBuilder = new StringBuilder();
            nameBuilder.append("Account ID: ").append(idAccount)
                    .append(", Balance: ").append(formattedBalance);

            option.put("id", idAccount);
            option.put("name", nameBuilder.toString());

            optionsList.add(option);
        }

        return optionsList;
    }

    // @Restriced(permission = "")
    @Route(url = "call/account/delete")
    public List<HashMap<String, Object>> deleteAccount(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
        int id = Integer.parseInt(idStr);
        accountRepository.deleteObject(id);
        return getAllAccount(vmjExchange);
    }

    @Route(url = "call/account/balanceupdate")
    public HashMap<String, Object> updateBalance(VMJExchange vmjExchange) {
        if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
            return null;
        }
        String idStr = (String) vmjExchange.getRequestBodyForm("id_account");
        int id = Integer.parseInt(idStr);
        Account account = accountRepository.getObject(id);
        int nowbalance = account.getBalance();

        String amountStr = (String) vmjExchange.getRequestBodyForm("amount");
        int amount = Integer.parseInt(amountStr);

        int total = nowbalance + amount;
        if (total < 0) {
            System.out.println("Transaction Failed"); 
        }else {
            System.out.println("Transaction Success");
            account.setBalance(total);
        }

        accountRepository.updateObject(account);
        account = accountRepository.getObject(id);
        return account.toHashMap();
    }

    @Route(url = "call/account/transfer")
    public HashMap<String, Object> transferAccount(VMJExchange vmjExchange) {
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

        String amountStr = (String) vmjExchange.getRequestBodyForm("amount");

        int amount = Integer.parseInt(amountStr);

        if (amount > balanceSource) {
            System.out.println("Transfer failed, amount exceded your balance");
            throw new BadRequestException("Transfer failed, amount exceded your balance", 400, 4000);
        } else {
            System.out.println("Transfer success");
            accountSource.setBalance(balanceSource - amount);
            accountTarget.setBalance(balanceTarget + amount);
        }

        accountRepository.updateObject(accountSource);
        accountRepository.updateObject(accountTarget);
        accountTarget = accountRepository.getObject(idTarget);
        return accountTarget.toHashMap();
    }

}
