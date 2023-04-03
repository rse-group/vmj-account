package accountpl.account.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="account_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountComponent implements Account{
	
	@Id
	protected int id_account; 

	public AccountComponent() {

	} 

	public int getId_account() {
		return this.id_account;
	}

	public void setId_account(int id_account) {
		this.id_account = id_account;
	}
 
	public abstract int getBalance();
	public abstract void setBalance(int balance);
	public abstract int getOverdraft_limit();
	public abstract void setOverdraft_limit(int overdraft_limit);
	public abstract boolean update(int x);

	
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> accountMap = new HashMap<String,Object>();
        accountMap.put("id_account", getId_account());
        accountMap.put("balance", getBalance());
        accountMap.put("overdraft_limit", getOverdraft_limit());
        return accountMap;
    }
}
