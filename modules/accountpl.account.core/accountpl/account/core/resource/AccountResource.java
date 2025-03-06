package accountpl.account.core;
import java.util.*;

import vmj.routing.route.VMJExchange;

public interface AccountResource {
    List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange);
    HashMap<String, Object> updateAccount(VMJExchange vmjExchange);
    HashMap<String, Object> getAccount(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllAccountOptions(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange);
    HashMap<String, Object> updateBalance(VMJExchange vmjExchange);
    HashMap<String, Object> transferAccount(VMJExchange vmjExchange);
}
