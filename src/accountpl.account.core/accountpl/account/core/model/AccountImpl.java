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

@Entity
@Table(name="financialreport_impl")
public class AccountImpl extends AccountComponent {

    public int overdraft_limit = 0;

    public AccountImpl(String id) {
        super(id);
    }

    public AccountImpl() {

    }

    public String getId() {
        return this.id_account;
    }

    public void setId(int id) {
        this.id_account = id;
    }

    public boolean update(int x) {
		// TODO: implement this method
		System.out.println("Add amount: "+x);
		int newBalance = balance + x;
		if (newBalance < overdraft_limit) {
			System.out.println("Update is failed");
			return false;
		}
		balance = newBalance;
		return true;
	}

    @Route(url="update")
    public HashMap<String,Object> update(VMJExchange vmjExchange){
        HashMap<String, Object> newBalance = new HashMap<>();
        newBalance.put("balance", "Financial Report Core");
        return newBalance;
    }
}
