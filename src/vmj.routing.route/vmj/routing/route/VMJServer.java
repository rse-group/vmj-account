package vmj.routing.route;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

import com.google.gson.*;

import java.util.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;

import vmj.object.mapper.VMJDatabaseUtil;
import prices.auth.core.exceptions.AuthException;
import prices.auth.vmj.annotations.*;
import prices.auth.vmj.enums.CRUDMethod;

public class VMJServer {
    private VMJDatabaseUtil vmjDBUtil;
    private HttpServer server = null;
    private static VMJServer vmjServerInstance = null;
    private String hostName;
    private int serverPort;

    private VMJServer(String hostName, int serverPort) {
        vmjDBUtil = new VMJDatabaseUtil();
        try {
            server = HttpServer.create(new InetSocketAddress(hostName, serverPort), 0);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (Error e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        this.hostName = hostName;
        this.serverPort = serverPort;
    }

    public static VMJServer getInstance(String hostName, int serverPort) {
        if (vmjServerInstance == null) {
            vmjServerInstance = new VMJServer(hostName, serverPort);
        }
        return vmjServerInstance;
    }

    public static VMJServer getInstance() {
        if (vmjServerInstance == null) {
            return null;
        }
        return vmjServerInstance;
    }

    public void startServerGeneric() throws IOException {
        // server.createContext("/api/hello", (exchange -> {
        // String respText = "Hello!";
        // exchange.getResponseHeaders().set("Content-Type", "application/json");
        // exchange.sendResponseHeaders(200, respText.getBytes().length);
        // OutputStream output = exchange.getResponseBody();
        // output.write(respText.getBytes());
        // output.flush();
        // exchange.close();
        // }));

        // System.out.println("http://" + hostName + ":" + serverPort + "/api/hello");

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void createURL(String endpoint, Object object, Method method) {
        server.createContext(("/" + endpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                try {
                    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                    VMJExchange vmjExchange = new VMJExchange(exchange);

                    if (AuthHandler.authorizeMethodInvocation(method, object, vmjExchange)) {
                        if (method.getReturnType().equals("void")) {
                            method.invoke(object, vmjExchange);
                        } else {
                            Object hasil = method.invoke(object, vmjExchange);

                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        String jsonOutput = gson.toJson(hasil);
                        sendABSSuccessResponse(exchange, jsonOutput);
                        }
                    } else {
                        sendABSFailedResponse(exchange, 403, "\"You are not allowed to access.\"");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendABSFailedResponse(exchange, 500, "\"" + e.getMessage() + "\"");
                } catch (Error e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendABSFailedResponse(exchange, 500, "\"" + e.getMessage() + "\"");
                }
            }
        });
        this.registerPermCheck(endpoint, method);
        System.out.println("http://" + hostName + ":" + serverPort + "/" + endpoint);
    }

    public static void sendABSSuccessResponse(HttpExchange exchange, String message) throws IOException {
        sendSuccessResponse(exchange, "{\"data\": " + message + "}");
    }

    public static void sendABSFailedResponse(HttpExchange exchange, int errorCode, String message) throws IOException {
        sendFailedResponse(exchange, errorCode, "{\"data\": " + message + "}");
    }

    public static void sendSuccessResponse(HttpExchange exchange, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, message.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(message.getBytes());
        output.flush();
        exchange.close();
    }

    public static void sendFailedResponse(HttpExchange exchange, int errorCode, String message) throws IOException {
        String sanitizedMessage = (message == null ? "null" : message);
        System.out.println("Error: " + sanitizedMessage);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(errorCode, sanitizedMessage.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(sanitizedMessage.getBytes());
        output.flush();
        exchange.close();
    }

    public void createTableCRUDEndpoint(String theEndpoint, String tableName, String className, ArrayList<String> tableColumns) {
        server.createContext(("/crud/" + theEndpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                try {
                    Class clazz = Class.forName(className);
                    VMJExchange vmjExchange = new VMJExchange(exchange);
                    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                    String uriPath = exchange.getRequestURI().getPath();
                    String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);

                    for (String string : uriParts) {
                        System.out.println(string);
                    }

                    if (uriParts.length == 2) {
                        /**
                         * POST new data
                         */
                        if (exchange.getRequestMethod().equals("POST")) {
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.POST, vmjExchange)) {
                                String respText = "Inserting a data...";
                                vmjDBUtil.insertDataToATable(tableName, vmjExchange.getPOSTBodyForm());

                                sendSuccessResponse(exchange, respText);
                            } else {
                                sendFailedResponse(exchange, 403, "You are not allowed to access.");
                            }
                        } else if (exchange.getRequestMethod().equals("GET")) {
                            /**
                             * all datas in a table
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.LIST, vmjExchange)) {
                                String respText = "Querying all datas...";
                                String allQueryString = vmjDBUtil.createSelectAllQueryFromATable(tableName);

                                List<HashMap<String, Object>> queriedList = vmjDBUtil.hitDatabaseForQueryATable(allQueryString,
                                        tableColumns);

                                Gson gson = new Gson();
                                respText = gson.toJson(queriedList);

                                sendSuccessResponse(exchange, respText);
                            } else {
                                sendFailedResponse(exchange, 403, "You are not allowed to access.");
                            }
                        }
                    } else if (uriParts.length == 3) {
                        /**
                         * based on ID
                         */
                        if (exchange.getRequestMethod().equals("PUT")) {
                            /**
                             * UPDATE
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.PUT, vmjExchange)) {
                                String respText = "Updating a data...";
                                vmjDBUtil.updateDataById(tableName, uriParts[2], vmjExchange.getPOSTBodyForm());
                                respText = "berhasil update data";
                                sendSuccessResponse(exchange, respText);
                            } else {
                                sendFailedResponse(exchange, 403, "You are not allowed to access.");
                            }
                        } else if (exchange.getRequestMethod().equals("DELETE")) {
                            /**
                             * DELETE
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.DELETE, vmjExchange)) {
                                String respText = "Deleting a data...";
                                vmjDBUtil.deleteRowById(tableName, uriParts[2]);
                                respText = "berhasil hapus data";
                                sendSuccessResponse(exchange, respText);
                            } else {
                                sendFailedResponse(exchange, 403, "You are not allowed to access.");
                            }
                        } else {
                            /**
                             * GET
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.GET, vmjExchange)) {
                                String respText = "Querying a data...";

                                HashMap<String, Object> queriedData = vmjDBUtil.getDataById(tableName, tableColumns,
                                        uriParts[2]);

                                Gson gson = new Gson();
                                respText = gson.toJson(queriedData);

                                sendSuccessResponse(exchange, respText);
                            } else {
                                sendFailedResponse(exchange, 403, "You are not allowed to access.");
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendFailedResponse(exchange, 500, e.getMessage());
                } catch (Error e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendFailedResponse(exchange, 500, e.getMessage());
                }
            }
        });
        this.registerPermCheck(theEndpoint, className, "crud/");
        System.out.println("http://" + hostName + ":" + serverPort + "/crud/" + theEndpoint);
    }

    public void createABSCRUDEndpoint(String theEndpoint, String tableName, String className, ArrayList<String> tableColumns) {
        server.createContext(("/call/" + theEndpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                try {
                    Class clazz = Class.forName(className);
                    VMJExchange vmjExchange = new VMJExchange(exchange);
                    exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                    String uriPath = exchange.getRequestURI().getPath();
                    String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);
                    for (String string : uriParts) {
                        System.out.println(string);
                    }
                    if (uriParts.length == 3) {
                        /**
                         * POST new data
                         */
                        if (exchange.getRequestMethod().equals("GET") && uriParts[2].equals("save")) {
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.POST, vmjExchange)) {
                                String getString = exchange.getRequestURI().getQuery();
                                System.out.println(getString);
                                HashMap<String, Object> hashInput = getInputAsDict(getString);
                                vmjDBUtil.insertDataToATable(tableName, hashInput);

                                String respText = selectAllFromATable(tableName, tableColumns);
                                sendABSSuccessResponse(exchange, respText);
                            } else {
                                sendABSFailedResponse(exchange, 403, "\"You are not allowed to access.\"");
                            }
                        } else if (exchange.getRequestMethod().equals("GET") && uriParts[2].equals("list")) {
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.LIST, vmjExchange)) {
                                String respText = selectAllFromATable(tableName, tableColumns);
                                sendABSSuccessResponse(exchange, respText);
                            } else {
                                sendABSFailedResponse(exchange, 403, "\"You are not allowed to access.\"");
                            }
                        /**
                         * based on ID
                        */
                        } else if (exchange.getRequestMethod().equals("GET") && uriParts[2].equals("detail")) {
                            /**
                             * GET
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.GET, vmjExchange)) {
                                String getString = exchange.getRequestURI().getQuery();
                                HashMap<String, Object> hashInput = getInputAsDict(getString);
                                String id = (String) hashInput.get("id");
                                String respText = getDataById(tableName, tableColumns, id);
                                sendABSSuccessResponse(exchange, respText);
                            } else {
                                sendABSFailedResponse(exchange, 403, "\"You are not allowed to access.\"");
                            }
                        } else if (exchange.getRequestMethod().equals("GET") && uriParts[2].equals("delete")) {
                            /**
                             * DELETE
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.DELETE, vmjExchange)) {
                                String getString = exchange.getRequestURI().getQuery();
                                HashMap<String, Object> hashInput = getInputAsDict(getString);
                                vmjDBUtil.deleteRowById(tableName,(String) hashInput.get("id"));
                                String respText = selectAllFromATable(tableName, tableColumns);
                                sendABSSuccessResponse(exchange, respText);
                            } else {
                                sendABSFailedResponse(exchange, 403, "\"You are not allowed to access.\"");
                            }
                        } else if (exchange.getRequestMethod().equals("GET") && uriParts[2].equals("update")) {
                            /**
                             * UPDATE
                             */
                            if (AuthHandler.authorizeCRUD(clazz, CRUDMethod.PUT, vmjExchange)) {
                                String getString = exchange.getRequestURI().getQuery();
                                HashMap<String, Object> hashInput = getInputAsDict(getString);
                                String id = (String) hashInput.get("id");
                                hashInput.remove("id");
                                hashInput.remove("token");
                                System.out.println(getString);
                                vmjDBUtil.updateDataById(tableName, id, hashInput);
                                String respText = getDataById(tableName, tableColumns, id);
                                sendABSSuccessResponse(exchange, respText);
                            } else {
                                sendABSFailedResponse(exchange, 403, "\"You are not allowed to access.\"");
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendFailedResponse(exchange, 500, "\"" + e.getMessage() + "\"");
                } catch (Error e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendFailedResponse(exchange, 500, "\"" + e.getMessage() + "\"");
                }
            }
        });
        this.registerPermCheck(theEndpoint, className, "call/");
        System.out.println("http://" + hostName + ":" + serverPort + "/call/" + theEndpoint);
    }

    private void registerPermCheck(String endpoint, String className, String prefix) {
        server.createContext(("/permcheck/" + prefix + endpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                try {
                    Class clazz = Class.forName(className);
                    Map<String, List<String>> perms = new HashMap<>();
                    CRUDMethod[] methods = {CRUDMethod.GET, CRUDMethod.LIST, CRUDMethod.POST, CRUDMethod.PUT, CRUDMethod.DELETE};
                    for (CRUDMethod method : methods) {
                        List<String> eligiblePerms = new ArrayList<>();
                        for (RestrictCRUD perm : AuthHandler.chooseRelevantPermission(clazz, method)) {
                            eligiblePerms.add(perm.permissionName());
                        }
                        if (!eligiblePerms.isEmpty())
                            perms.put(method.toString(), eligiblePerms);
                    }
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    String jsonOutput = gson.toJson(perms);
                    sendABSSuccessResponse(exchange, jsonOutput);
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                    sendFailedResponse(exchange, 500, "\"" + e.getMessage() + "\"");
                }
            }
        });
    }

    private void registerPermCheck(String endpoint, Method method) {
        server.createContext(("/permcheck/" + endpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                List<String> perms = new ArrayList<>();
                for (Restricted perm : AuthHandler.chooseRelevantPermission(method)) {
                    perms.add(perm.permissionName());
                }
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String jsonOutput = gson.toJson(perms);
                sendABSSuccessResponse(exchange, jsonOutput);
            }
        });
    }
    private String getDataById(String tableName, ArrayList<String> tableColumns, String id) {
        HashMap<String, Object> queriedData = vmjDBUtil.getDataById(tableName, tableColumns,
                                id);

        Gson gson = new Gson();
        String respText = gson.toJson(queriedData);
        return respText;
    }

    private String selectAllFromATable(String tableName, ArrayList<String> tableColumns) {
        String allQueryString = vmjDBUtil.createSelectAllQueryFromATable(tableName);

        List<HashMap<String, Object>> queriedList = vmjDBUtil.hitDatabaseForQueryATable(allQueryString,
                tableColumns);

        Gson gson = new Gson();
        String respText = gson.toJson(queriedList);
        return respText;
    }

    public String getStringFromInputStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while (sc.hasNext()) {
            try {
                sb.append(URLDecoder.decode(sc.nextLine(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public HashMap<String, Object> getInputAsDict(String toBeSplitted) {
        String[] splitted = toBeSplitted.split("&");
        HashMap<String, Object> mapInput = new HashMap<>();
        for (String string : splitted) {
            String[] splitStrings = string.split("=");
            mapInput.put(splitStrings[0], splitStrings[1]);
        }
        return mapInput;
    }

    @Deprecated
    public void createTableCRUDEndpoint(String theEndpoint, String tableName, ArrayList<String> tableColumns) {
        server.createContext(("/crud/" + theEndpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                String uriPath = exchange.getRequestURI().getPath();
                String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);
                for (String string : uriParts) {
                    System.out.println(string);
                }
                if (uriParts.length == 2) {
                    /**
                     * POST new data
                     */
                    if (exchange.getRequestMethod().equals("POST")) {
                        String respText = "Inserting a data...";
                        String getString = getStringFromInputStream(exchange.getRequestBody());
                        System.out.println(getString);
                        HashMap<String, Object> hashInput = getInputAsDict(getString);
                        vmjDBUtil.insertDataToATable(tableName, hashInput);

                        sendSuccessResponse(exchange, respText);
                    } else if (exchange.getRequestMethod().equals("GET")) {
                        /**
                         * all datas in a table
                         */
                        String respText = "Querying all datas...";
                        String allQueryString = vmjDBUtil.createSelectAllQueryFromATable(tableName);

                        List<HashMap<String, Object>> queriedList = vmjDBUtil.hitDatabaseForQueryATable(allQueryString,
                                tableColumns);

                        Gson gson = new Gson();
                        respText = gson.toJson(queriedList);

                        sendSuccessResponse(exchange, respText);
                    }
                } else if (uriParts.length == 3) {
                    /**
                     * based on ID
                     */
                    if (exchange.getRequestMethod().equals("PUT")) {
                        /**
                         * UPDATE
                         */
                        String respText = "Updating a data...";
                        String getString = getStringFromInputStream(exchange.getRequestBody());
                        System.out.println(getString);
                        HashMap<String, Object> hashInput = getInputAsDict(getString);
                        vmjDBUtil.updateDataById(tableName, uriParts[2], hashInput);
                        respText = "berhasil update data";
                        sendSuccessResponse(exchange, respText);
                    } else if (exchange.getRequestMethod().equals("DELETE")) {
                        /**
                         * DELETE
                         */
                        String respText = "Deleting a data...";
                        vmjDBUtil.deleteRowById(tableName, uriParts[2]);
                        respText = "berhasil hapus data";
                        sendSuccessResponse(exchange, respText);
                    } else {
                        /**
                         * GET
                         */
                        String respText = "Querying a data...";

                        HashMap<String, Object> queriedData = vmjDBUtil.getDataById(tableName, tableColumns,
                                uriParts[2]);

                        Gson gson = new Gson();
                        respText = gson.toJson(queriedData);

                        sendSuccessResponse(exchange, respText);
                    }
                }
            }

        });

        System.out.println("http://" + hostName + ":" + serverPort + "/crud/" + theEndpoint);
    }

    @Deprecated
    public void createCRUDEndpoints(String theEndpoint, String tableName, String parentTableName,
            Boolean isStandAloneTable, ArrayList<String> tableColumns, ArrayList<String> parentTableColumns,
            String foreignKeyColumnName) {
        // System.out.println("http://localhost:8000" + theEndpoint);
        server.createContext(("/crud/" + theEndpoint), new HttpHandler() {
            @Override
            public void handle(final HttpExchange exchange) throws IOException {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                String uriPath = exchange.getRequestURI().getPath();
                String[] uriParts = Arrays.copyOfRange(uriPath.split("/"), 1, uriPath.split("/").length);
                for (String string : uriParts) {
                    System.out.println(string);
                }
                if (uriParts.length == 2) {
                    /**
                     * POST new data
                     */
                    if (exchange.getRequestMethod().equals("POST")) {
                        if (parentTableName != null) {
                            String respText = "Inserting a data...";
                            String getString = getStringFromInputStream(exchange.getRequestBody());
                            System.out.println(getString);
                            HashMap<String, Object> hashInput = getInputAsDict(getString);

                            /**
                             * for parent table
                             */
                            HashMap<String, Object> hashInputParentTable = new HashMap<>();
                            for (String parentTableColumn : parentTableColumns) {
                                hashInputParentTable.put(parentTableColumn, hashInput.get(parentTableColumn));
                            }
                            int parentId = vmjDBUtil.insertDataToATable(parentTableName, hashInputParentTable);

                            /**
                             * for the table
                             */
                            HashMap<String, Object> hashInputTable = new HashMap<>();
                            for (String tableColumn : tableColumns) {
                                hashInputTable.put(tableColumn, hashInput.get(tableColumn));
                            }
                            hashInputTable.put(foreignKeyColumnName, parentId);

                            vmjDBUtil.insertDataToATable(tableName, hashInputTable);

                            sendSuccessResponse(exchange, respText);

                        } else if (isStandAloneTable) {
                            String respText = "Inserting a data...";
                            String getString = getStringFromInputStream(exchange.getRequestBody());
                            System.out.println(getString);
                            HashMap<String, Object> hashInput = getInputAsDict(getString);
                            vmjDBUtil.insertDataToATable(tableName, hashInput);

                            sendSuccessResponse(exchange, respText);
                        } else {
                            String respText = "Belum terhandle";
                            System.out.println("belum terhandle");
                            sendSuccessResponse(exchange, respText);
                        }
                    }

                    else if (exchange.getRequestMethod().equals("GET")) {
                        /**
                         * all datas in a table
                         */
                        String respText = "Querying all datas...";
                        String allQueryString = vmjDBUtil.createSelectAllQueryFromATable(tableName);

                        List<HashMap<String, Object>> queriedList = vmjDBUtil.hitDatabaseForQueryATable(allQueryString,
                                tableColumns);

                        Gson gson = new Gson();
                        respText = gson.toJson(queriedList);

                        sendSuccessResponse(exchange, respText);
                    }

                } else if (uriParts.length == 3) {
                    /**
                     * based on ID
                     */
                    if (exchange.getRequestMethod().equals("PUT")) {
                        /**
                         * UPDATE
                         */
                        String respText = "Updating a data...";
                        String getString = getStringFromInputStream(exchange.getRequestBody());
                        System.out.println(getString);
                        HashMap<String, Object> hashInput = getInputAsDict(getString);
                        vmjDBUtil.updateDataById(tableName, uriParts[2], hashInput);
                        respText = "berhasil update data";
                        sendSuccessResponse(exchange, respText);
                    } else if (exchange.getRequestMethod().equals("DELETE")) {
                        /**
                         * DELETE
                         */
                        String respText = "Deleting a data...";
                        vmjDBUtil.deleteRowById(tableName, uriParts[2]);
                        respText = "berhasil hapus data";
                        sendSuccessResponse(exchange, respText);
                    } else {
                        /**
                         * GET
                         */
                        String respText = "Querying a data...";

                        HashMap<String, Object> queriedData = vmjDBUtil.getDataById(tableName, tableColumns, uriParts[2]);

                        Gson gson = new Gson();
                        respText = gson.toJson(queriedData);

                        sendSuccessResponse(exchange, respText);
                    }
                }
            }
        });

        System.out.println("http://" + hostName + ":" + serverPort + "/crud/" + theEndpoint);
    }
}
