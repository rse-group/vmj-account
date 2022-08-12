package accountpl.account.interestestimation;

import accountpl.account.core.AccountResourceDecorator;
import accountpl.account.core.AccountImpl;
import accountpl.account.core.AccountResourceComponent;

public class AccountResourceImpl extends AccountResourceDecorator {
    public AccountResourceImpl (AccountResourceComponent record) {
        // to do implement the method
    }

    // @Restriced(permission = "")
    @Route(url="call/interestestimation/save")
    public List<HashMap<String,Object>> save(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		  = create(vmjExchange);
		Repository.saveObject();
		return getAll(vmjExchange);
	}

    public  create(VMJExchange vmjExchange){
		AccountImpl account = (AccountImpl) vmjExchange.getRequestBodyForm("account);
		
		  = record.create(vmjExchange);
		 deco = Factory.create("accountpl.interestestimation.core.AccountImpl", );
			return deco;
	}

    // @Restriced(permission = "")
    @Route(url="call/interestestimation/update")
    public HashMap<String, Object> update(VMJExchange vmjExchange){
		// to do implement the method
	}

	// @Restriced(permission = "")
    @Route(url="call/interestestimation/detail")
    public HashMap<String, Object> get(VMJExchange vmjExchange){
		return record.get(vmjExchange);
	}

	// @Restriced(permission = "")
    @Route(url="call/interestestimation/list")
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
    @Route(url="call/interestestimation/delete")
    public List<HashMap<String,Object>> delete(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		String idStr = (String) vmjExchange.getRequestBodyForm("");
		int id = Integer.parseInt(idStr);
		Repository.deleteObject(id);
		return getAll(vmjExchange);
	}

	protected int estimatedInterest(int daysLeft) {
		// TODO: implement this method
	}
}

