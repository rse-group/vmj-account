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
	protected int balance;
	protected int overdraft_limit;

	public AccountImpl(int balance, int overdraft_limit, int id_account) {
		this.balance = balance;
		this.overdraft_limit = overdraft_limit;
		this.id_account = id_account;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getOverdraft_limit() {
		return this.overdraft_limit;
	}

	public void setOverdraft_limit(int overdraft_limit) {
		this.overdraft_limit = overdraft_limit;
	}

	public boolean update(int x) {
		// TODO: implement this method
	}

}

