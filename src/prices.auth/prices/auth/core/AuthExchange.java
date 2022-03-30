package prices.auth.core;
import java.util.List;
import java.util.Map;

public interface AuthExchange {
    Map<String,List<String>> getRequestHeaders();
    String getAuthToken();
    List<String> getCookies();
}
