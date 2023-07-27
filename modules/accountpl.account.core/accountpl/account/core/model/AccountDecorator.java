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
	
    @OneToOne(cascade=CascadeType.ALL)
	protected AccountComponent record;
		
	public AccountDecorator (AccountComponent record, String objectName) {
		this.record = record;
        Random r = new Random();
		this.id_account = Math.abs(r.nextInt());
		this.record.objectName = objectName;
	}

	public AccountDecorator(int id_account, AccountComponent record) {
        this.id_account= id_account;
        this.record = record;
    }

    public AccountDecorator() {
        super();
        this.record = new AccountImpl();
        Random r = new Random();
		this.id_account = Math.abs(r.nextInt());
    }

	public int getBalance() {
		return this.record.getBalance();
	}
	public void setBalance(int balance) {
		this.record.setBalance(balance);
	}
	
	public int getId_account() {
		return this.record.getId_account();
	}
	public void setId_account(int id_account) {
		this.record.setId_account(id_account);
	}

    public AccountComponent getRecord() {
        return this.record;
    }

    public void setRecord(AccountComponent record) {
        this.record = record;
    }
    
	public boolean update(int x) {
		return record.update(x);
	}

	public HashMap<String, Object> toHashMap() {
        return this.record.toHashMap();
    }
}

