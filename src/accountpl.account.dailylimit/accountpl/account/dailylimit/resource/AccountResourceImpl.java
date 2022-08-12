package accountpl.account.dailylimit;

import accountpl.account.core.AccountResourceDecorator;
import accountpl.account.core.AccountImpl;
import accountpl.account.core.AccountResourceComponent;

public class AccountResourceImpl extends AccountResourceDecorator {
    public AccountResourceImpl (AccountResourceComponent record) {
        // to do implement the method
    }

    // @Restriced(permission = "")
    @Route(url="call/dailylimit/save")
    public List<HashMap<String,Object>> save(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		  = create(vmjExchange);
		Repository.saveObject();
		return getAll(vmjExchange);
	}

    public  create(VMJExchange vmjExchange){
		int dailyLimit = (int) vmjExchange.getRequestBodyForm("dailyLimit);
		int withdraw = (int) vmjExchange.getRequestBodyForm("withdraw);
		AccountImpl account = (AccountImpl) vmjExchange.getRequestBodyForm("account);
		
		  = record.create(vmjExchange);
		 deco = Factory.create("accountpl.dailylimit.core.AccountImpl", dailyLimit, withdraw);
			return deco;
	}

    // @Restriced(permission = "")
    @Route(url="call/dailylimit/update")
    public HashMap<String, Object> update(VMJExchange vmjExchange){
		// to do implement the method
	}

	// @Restriced(permission = "")
    @Route(url="call/dailylimit/detail")
    public HashMap<String, Object> get(VMJExchange vmjExchange){
		return record.get(vmjExchange);
	}

	// @Restriced(permission = "")
    @Route(url="call/dailylimit/list")
    public List<HashMap<String,Object>> getAll(VMJExchange vmjExchange){
		List<> List = Repository.getAllObject("_impl");
		return transformListToHashMap(List);
	}

    public List<HashMap<String,Object>> transformListToHashMap(List<> List){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < List.size(); i++) {
            resultList.add(List.get(i).toHashMap());
        }

        return resultList;
	}

	// @Restriced(permission = "")
    @Route(url="call/dailylimit/delete")
    public List<HashMap<String,Object>> delete(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		String idStr = (String) vmjExchange.getRequestBodyForm("");
		int id = Integer.parseInt(idStr);
		Repository.deleteObject(id);
		return getAll(vmjExchange);
	}

	protected Boolean update(int x) {
		// TODO: implement this method
	}
}

