package accountpl.account.creditworthiness;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.Account;
import accountpl.account.core.AccountComponent;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import accountpl.account.core.AccountDecorator;
import accountpl.account.core.AccountComponent;

@Entity(name="account_creditworthiness")
@Table(name="account_creditworthiness")
public class AccountImpl extends AccountDecorator {

	public double interest_rate;
	
	public AccountImpl() {
		super();
		this.objectName = AccountImpl.class.getName();
	}
	
	public AccountImpl(AccountComponent record, double interest_rate) {
		super(record, AccountImpl.class.getName());
		this.interest_rate = interest_rate;
		this.objectName = AccountImpl.class.getName();
	}
	
	public AccountImpl(int id, AccountComponent record, double interest_rate) {
		super(id, record);
		this.interest_rate = interest_rate;
		this.objectName = AccountImpl.class.getName();
	}

	public double getInterest_rate() {
		return this.interest_rate;
	}

	public void setInterest_rate(double interest_rate) {
		this.interest_rate = interest_rate;
	}


    @Override
    public String toString() {
        return "{" +
            " id_account='" + getId_account() + "'" +
            " interest_rate='" + getInterest_rate() + "'" +
            ", record='" + getRecord() + "'" +
            "}";
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> accountMap = record.toHashMap();
        accountMap.put("id_account", id_account);
        accountMap.put("interest_rate", getInterest_rate());
        return accountMap;
    }
}

