package accountpl.account.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name="AccountImpl")
@Table(name="account_impl")
public class AccountImpl extends AccountComponent {
    
    protected int balance;
	protected int overdraft_limit;

    public AccountImpl(String id) {
        super(id);
    }

    public AccountImpl() {

    }

    public String getId() {
        return this.id_account;
    }

    public void setId(String id) {
        this.id_account = id;
    }

    public int getBalance(){
		return this.balance;
	}

	public void setOverdraftLimit(int x){
		this.overdraft_limit = 0;
	}

    public int getOverdraftLimit(){
		return 0;
	}

	public void setBalance(int balance){
		this.balance=balance;
	}


    public boolean update(int x) {
		System.out.println("Add amount: "+x);
		int newBalance = balance + x;
		if (newBalance < overdraft_limit) {
			System.out.println("Update is failed");
			return false;
		}
		balance = newBalance;
		return true;
	}

    // @Route(url="update")
    // public HashMap<String,Object> update(VMJExchange vmjExchange){
    //     HashMap<String, Object> newBalance = new HashMap<>();
    //     newBalance.put("balance", "Financial Report Core");
    //     return newBalance;
    // }
}
