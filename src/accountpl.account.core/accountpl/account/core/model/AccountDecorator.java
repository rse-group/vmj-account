package accountpl.account.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;
//add other required packages

@MappedSuperclass
public abstract class AccountDecorator extends AccountComponent{
	protected AccountComponent record;
		
	public AccountDecorator (AccountComponent record) {
		this.record = record;
	}

	public int getBalance() {
		return record.getBalance();
	}
	public void setBalance(int balance) {
		record.setBalance(balance);
	}
	public int getOverdraft_limit() {
		return record.getOverdraft_limit();
	}
	public void setOverdraft_limit(int overdraft_limit) {
		record.setOverdraft_limit(overdraft_limit);
	}
	public int getId_account() {
		return record.getId_account();
	}
	public void setId_account(int id_account) {
		record.setId_account(id_account);
	}

	public boolean update(int x) {
		return record.update(x);
	}

	public HashMap<String, Object> toHashMap() {
        return this.record.toHashMap();
    }
}

