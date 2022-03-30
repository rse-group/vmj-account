package prices.auth.vmj;

import com.sun.net.httpserver.HttpExchange;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import prices.auth.core.AuthExchange;

public class VMJAuthExchange implements AuthExchange {
    private HttpExchange request;

    public VMJAuthExchange(HttpExchange request) {
        this.request = request;
    }

    public Map<String,List<String>> getRequestHeaders() {
        return this.request.getRequestHeaders();
    }

    public String getAuthToken() {
        //return this.request.getRequestHeaders().getFirst("Authorization");
        String query = this.request.getRequestURI().getQuery();
        if (query == null || query.isEmpty()) {
            return "";
        }
        String token = this.queryToMap(query).get("token");
        if (token == null) {
            return "";
        }
        return token;
    }

    public List<String> getCookies() {
        return this.request.getRequestHeaders().get("Cookie");
    }

    private Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
