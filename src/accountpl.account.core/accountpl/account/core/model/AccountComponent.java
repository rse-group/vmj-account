package accountpl.account.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity(name="account_comp")
@Table(name="account_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountComponent implements Account{	
	
	@Id
	public String id_account;
	 
	public String getIdAccount() {
		return this.id_account;
	}

	public void setIdAccount(String id_account) {
		this.id_account = id_account;
	}


	public AccountComponent() {
	}
	
	public AccountComponent(String id) {
		this.id_account = id;
	} 
	
	public abstract int getBalance();
	public abstract void setBalance (int x);
	public abstract int getOverdraftLimit();
	public abstract void setOverdraftLimit (int x);
	public abstract boolean update(int x);
	// public abstract HashMap<String,Object> update(VMJExchange vmjExchange);

	@Override
    public String toString() {
        return "{" +
            " id='" + getIdAccount() + "'" +
            ", balance='" + getBalance() + "'" +
			", overdraft_limit='" + getOverdraftLimit() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> accountMap = new HashMap<String,Object>();
        accountMap.put("id", getIdAccount());
        accountMap.put("balance", getBalance());
		accountMap.put("overdraft_limit", getOverdraftLimit());
        return accountMap;
    }

    // @Route(url="getBalance")
    // public HashMap<String, Object> getBalance(VMJExchange vmjExchange) {
    //     return null;
    // }

}
