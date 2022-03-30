package vmj.routing.route;
import java.util.*;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import prices.auth.core.AuthPayload;


public class VMJExchange {
    private HttpExchange exchange;
    private AuthPayload authPayload;
    private String postBodyString;

    public VMJExchange(HttpExchange exchange) {
        this.exchange = exchange;
        this.postBodyString = getStringFromInputStream(exchange.getRequestBody());
    }

    public HttpExchange getHttpExchange() {
        return this.exchange;
    }

    public AuthPayload getAuthPayload() {
        return this.authPayload;
    }

    public void setAuthPayload(AuthPayload authPayload) {
        this.authPayload = authPayload;
    }

    public HashMap<String,Object> getPOSTBodyForm() {
        return getInputAsDict(this.postBodyString);
    }

    public Object getPOSTBodyForm(String key) {
        HashMap<String,Object> inputMap = getInputAsDict(this.postBodyString);
        if (!inputMap.containsKey(key)) {
            return null;
        }
        return inputMap.get(key);
    }

    /*  Karena sebenarnya POST, PUT dan DELETE bisa menggunakan method yang sama,
        mungkin bisa mengganti nama method saja. Alternatifnya membuat method baru
        dengan isi yang kurang lebih sama. */
    public Object getRequestBodyForm(String key) {
        HashMap<String,Object> inputMap = getJSONInputAsDict(this.postBodyString);
        if (!inputMap.containsKey(key)) {
            return null;
        }
        return inputMap.get(key);
    }

    public String getGETParam(String key) {
        Map<String,String> inputParamsMap = queryToMap();
        if (!inputParamsMap.containsKey(key)) {
            return null;
        }
        return inputParamsMap.get(key);
    }

    public String getStringFromInputStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()){
            try {
                sb.append(URLDecoder.decode(sc.nextLine(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public HashMap<String,Object> getInputAsDict(String toBeSplitted) {
        String[] splitted = toBeSplitted.split("&");
        HashMap<String,Object> mapInput = new HashMap<>();
        for (String string : splitted) {
            String[] splitStrings = string.split("=");
            mapInput.put(splitStrings[0], splitStrings[1]);
        }
        return mapInput;
    }

    public HashMap<String,Object> getJSONInputAsDict(String jsonString) {
        Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        HashMap<String, Object> mapInput = new Gson().fromJson(jsonString, type);

        return mapInput;
    } 


    /**
     * source : https://stackoverflow.com/questions/11640025/how-to-obtain-the-query-string-in-a-get-with-java-httpserver-httpexchange
     */
    public Map<String, String> queryToMap() {
        String query = this.exchange.getRequestURI().getQuery();
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
