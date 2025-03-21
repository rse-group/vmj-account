package accountpl.account.core;

import java.lang.Math;
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
import javax.persistence.OneToMany;


@Entity(name="account_impl")
@Table(name="account_impl")
public class AccountImpl extends AccountComponent {


	public AccountImpl(int balance, int id_account) {
		this.balance = balance;
		this.id_account = id_account;
		this.objectName = AccountImpl.class.getName();
	}
	
	public AccountImpl(int balance) {
		Random r = new Random();
		this.id_account = Math.abs(r.nextInt());
		this.balance = balance;
		this.objectName = AccountImpl.class.getName();
	}

	public AccountImpl()
	{
		Random r = new Random();
		this.id_account = Math.abs(r.nextInt());
        this.balance = 0;
        this.objectName = AccountImpl.class.getName();
	}


	public boolean update(int x) {
		int newBalance = balance + x;
		if (newBalance < 0)
			return false;
		balance = newBalance;
		return true;
	}

}

