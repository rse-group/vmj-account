package accountpl.account.overdraft;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.AccountComponent;

@Entity(name="account_overdraft")
@Table(name="account_overdraft")
public class AccountImpl extends AccountDecorator {

	protected int overdraft_limit;
	public AccountImpl() {
		super();
	}
	
	public AccountImpl(AccountComponent record, int overdraft_limit) {
		super(record);
		this.overdraft_limit = overdraft_limit;
	}
	
	public AccountImpl(int id, AccountComponent record, int overdraft_limit) {
		super(id, record);
		this.overdraft_limit = overdraft_limit;
	}

	public int getOverdraft_limit() {
		return this.overdraft_limit;
	}

	public void setOverdraft_limit(int overdraft_limit) {
		this.overdraft_limit = overdraft_limit;
	}
	
    @Override
    public String toString() {
        return "{" +
            " id_account='" + getId_account() + "'" +
            " overdraft_limit='" + getOverdraft_limit() + "'" +
            ", record='" + getRecord() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> accountMap = record.toHashMap();
        accountMap.put("id_account", getId_account());
        accountMap.put("overdraft_limit", getOverdraft_limit());
        return accountMap;
    }

}
