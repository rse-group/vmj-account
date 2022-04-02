package accountpl.account.core;

import accountpl.account.core.AccountControllerComponent;

import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import accountpl.account.AccountFactory;

import prices.auth.vmj.annotations.Restricted;


public class AccountControllerImpl extends AccountControllerComponent{

   // @Restricted(permissionName="ModifyAccountImpl")
    @Route(url="call/account/save")
    public List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange) {
        Account account = createAccount(vmjExchange);
        accountDao.saveObject(account);
        System.out.println(account);
        return getAllAccount(vmjExchange);
    }

    public Account createAccount(VMJExchange vmjExchange) {
        String id = (String) vmjExchange.getRequestBodyForm("id_account");
        Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", id);
        return account;
    }

    // public Account createAccount(VMJExchange vmjExchange, String id) {
    //     String idStr = (String) vmjExchange.getRequestBodyForm("id");
    //     Account account = AccountFactory.createAccount("accountpl.account.core.AccountImpl", idStr);
    //     return account;
    // }


   // @Restricted(permissionName="ModifyAccountImpl")
    @Route(url="call/account/update")
    public HashMap<String, Object> updateAccount(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        Account account = accountDao.getObject(id);
        String balanceStr = (String) vmjExchange.getRequestBodyForm("balance");
        account.setBalance(Integer.parseInt(balanceStr));
        accountDao.updateObject(account);
        return account.toHashMap();
    }

    @Route(url="call/account/detail")
    public HashMap<String, Object> getAccount(VMJExchange vmjExchange) {
        String idStr = vmjExchange.getGETParam("id");
        int id = Integer.parseInt(idStr);
        Account account = accountDao.getObject(id);
        System.out.println(account);
        try {
            return account.toHashMap();
        } catch (NullPointerException e) {
            HashMap<String, Object> blank = new HashMap<>();
            blank.put("error", "Missing GET Params");
            return blank;
        }
    }

    @Route(url="call/account/list")
    public List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange) {
        List<Account> accountList = accountDao.getAllObject("AccountImpl");
        return transformAccountListToHashMap(accountList);
    }

    // TODO: bisa dimasukin ke kelas util
    public List<HashMap<String,Object>> transformAccountListToHashMap(List<Account> accountList) {
        List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < accountList.size(); i++) {
            resultList.add(accountList.get(i).toHashMap());
        }

        return resultList;
    }

   // @Restricted(permissionName="ModifyAccountImpl")
    @Route(url="call/account/delete")
    public List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange) {
        String idStr = (String) vmjExchange.getRequestBodyForm("id");
        int id = Integer.parseInt(idStr);
        accountDao.deleteObject(id);
        return getAllAccount(vmjExchange);
    }
    
}
