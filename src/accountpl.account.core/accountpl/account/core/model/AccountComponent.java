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

	public int balance;
	public int overdraft_limit;
	
	@Id
	public String id_account;
	 
	public String getIdAccount() {
		return this.id_account;
	}

	public void setIdAccount(String id_account) {
		this.id_account = id_account;
	}

	public int getBalance(){
		return this.overdraft_limit;
	}

	public void setBalance(int balance){
		this.balance=balance;
	}


	public AccountComponent() {
		
	}
	
	public AccountComponent(String id) {
		this.id_account = id;
	} 
	
	public abstract boolean update(int x);
	public abstract HashMap<String,Object> update(VMJExchange vmjExchange);

	@Override
    public String toString() {
        return "{" +
            " id='" + getIdAccount() + "'" +
            ", balance='" + getBalance() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> accountMap = new HashMap<String,Object>();
        accountMap.put("id", getIdAccount());
        accountMap.put("balance", getBalance());
        return accountMap;
    }

    @Route(url="getBalance")
    public HashMap<String, Object> getBalance(VMJExchange vmjExchange) {
        return null;
    }

}
