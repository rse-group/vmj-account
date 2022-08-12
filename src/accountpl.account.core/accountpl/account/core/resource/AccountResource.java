package accountpl.account.core;
import java.util.*;

import vmj.routing.route.VMJExchange;

public interface AccountResource {
    List<HashMap<String,Object>> saveAccount(VMJExchange vmjExchange);
    HashMap<String, Object> updateAccount(VMJExchange vmjExchange);
    HashMap<String, Object> getAccount(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllAccount(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteAccount(VMJExchange vmjExchange);
}
