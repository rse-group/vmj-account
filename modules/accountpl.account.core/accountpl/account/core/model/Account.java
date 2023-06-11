package accountpl.account.core;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface Account {
	public boolean update(int x);
	public int getBalance();
	public void setBalance(int balance);
	public int getOverdraft_limit();
	public void setOverdraft_limit(int overdraft_limit);
	HashMap<String, Object> toHashMap();
}
