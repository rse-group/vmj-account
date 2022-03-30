package accountpl.account.core;
import java.util.*;
import vmj.routing.route.VMJExchange;

public interface Account {
    HashMap<String,Object> update(VMJExchange vmjExchange);
    boolean update(int x);
    HashMap<String, Object> toHashMap();
    HashMap<String,Object> getBalance(VMJExchange vmjExchange);
}