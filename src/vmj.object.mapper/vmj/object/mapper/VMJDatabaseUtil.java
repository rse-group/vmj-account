package vmj.object.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class VMJDatabaseUtil {
    private VMJDatabaseLoader dbLoader;

    public VMJDatabaseUtil() {
        this.dbLoader = new VMJDatabaseLoader();
    }

    public int insertDataToATable(String tableName, HashMap<String, Object> valuesToBeChanged) {
        String sqlString = createStringForInsertQuery(tableName, valuesToBeChanged);

        Connection conn = null;
        PreparedStatement pstmt = null;

        int lastInsertId = -1;

        try {
            conn = DriverManager.getConnection(this.dbLoader.getDatabaseUrl());

            try {
                pstmt = conn.prepareStatement(sqlString);
                pstmt = this.setValueForInsertQuery(pstmt, valuesToBeChanged);

                pstmt.execute();

            } catch (SQLException e) {
                // TODO: handle exception
                throw new Error("Problem", e);
            } finally {
                if (pstmt != null) {
                    lastInsertId = pstmt.getGeneratedKeys().getInt(1);
                    pstmt.close();
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return lastInsertId;
    }

    public String createSelectAllQueryFromATable(String tableName) {
        String sqlString = "SELECT * FROM " + tableName;
        return sqlString;
    }

    public List<HashMap<String, Object>> hitDatabaseForQueryATable(String sqlString, ArrayList<String> requiredFields) {
        List<HashMap<String, Object>> resultList = null;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(this.dbLoader.getDatabaseUrl());

            try {
                pstmt = conn.prepareStatement(sqlString);

                resultList = this.hitDatabaseForQueryATable(pstmt, requiredFields);

            } catch (SQLException e) {
                // TODO: handle exception
                throw new Error("Problem", e);
            } finally {
                if (pstmt != null) {
                    pstmt.close();
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return resultList;
    }

    public List<HashMap<String, Object>> hitDatabaseForQueryATable(PreparedStatement pstmt, ArrayList<String> requiredFields) throws SQLException {
        ArrayList<HashMap<String, Object>> resultList = new ArrayList<>();

        ResultSet resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            HashMap<String, Object> hashResult = new HashMap<>();
            for (String requiredField : requiredFields) {
                hashResult.put(requiredField, resultSet.getObject(requiredField));
            }
            resultList.add(hashResult);
        }

        return resultList;
    }

    public void updateDataById(String tableName, String idString, HashMap<String, Object> valuesToBeChanged) {
        String sql = "UPDATE " + tableName + " SET ";
        for (Map.Entry<String, Object> entryData : valuesToBeChanged.entrySet()) {
            sql += entryData.getKey() + "=";
            if (entryData.getValue() instanceof String) {
                sql += "'" + entryData.getValue() + "'";
            } else {
                sql += entryData.getValue();
            }
            sql += ",";
        }
        int sqlStrLength = sql.length();
        sql = sql.substring(0, sqlStrLength - 1) + " WHERE id = " + idString + ";";
        System.out.println(sql);
        hitDatabase(sql);
    }

    public void deleteRowById(String tableName, String id) {
        String sql = "DELETE FROM " + tableName + " WHERE id=" + id;
        hitDatabase(sql);
    }

    public HashMap<String, Object> getDataById(String tableName, ArrayList<String> requiredFields, String idString) {
        String sql = "SELECT * from " + tableName + " WHERE id=" + idString + ";";
        return hitDatabaseForQuery(sql, requiredFields);
    }

    public String createStringForInsertQuery(String tableName, HashMap<String, Object> toBeAddedData) {

        String afterTableName = "(";
        String values = " VALUES (";

        for (Map.Entry<String, Object> entryData : toBeAddedData.entrySet()) {
            if (! entryData.getKey().equals("token")) {
                afterTableName += entryData.getKey() + ",";
                values += "?,";
            }
        }

        int afterTableLength = afterTableName.length();
        int valuesLength = values.length();

        afterTableName = afterTableName.substring(0, afterTableLength - 1) + ")";
        values = values.substring(0, valuesLength - 1) + ")";

        String sql = "INSERT INTO " + tableName + " " + afterTableName + values + ";";
        System.out.println(sql);
        return sql;
    }

    public PreparedStatement setValueForInsertQuery(PreparedStatement pstmt, HashMap<String, Object> valuesToBeChanged) throws SQLException {
        int idx = 1;
        for (Map.Entry<String, Object> entryData : valuesToBeChanged.entrySet()) {
            if (! entryData.getKey().equals("token")) {
                if (entryData.getValue() instanceof String) {
                    System.out.println("idx : " + idx + " , value : " + entryData.getValue().toString());
                    pstmt.setString(idx, entryData.getValue().toString());
                } else {
                    throw new Error("Error insert unidentified data type");
                }
                idx++;
            }
        }

        return pstmt;
    }

    public int hitDatabaseGetLastInsertId(String sqlString) {
        Connection conn = null;
        Statement stmt = null;

        int lastInsertId = -1;

        try {
            conn = DriverManager.getConnection(this.dbLoader.getDatabaseUrl());

            try {
                stmt = conn.createStatement();
                stmt.execute(sqlString);
            } catch (SQLException e) {
                // TODO: handle exception
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    lastInsertId = stmt.getGeneratedKeys().getInt(1);
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return lastInsertId;
    }

    public void hitDatabase(String sqlString) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(this.dbLoader.getDatabaseUrl());

            try {
                stmt = conn.createStatement();
                stmt.execute(sqlString);
            } catch (SQLException e) {
                // TODO: handle exception
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public HashMap<String, Object> hitDatabaseForQuery(String sqlString, ArrayList<String> requiredFields) {
        Connection conn = null;
        Statement stmt = null;
        HashMap<String, Object> hashResult = new HashMap<>();

        try {
            conn = DriverManager.getConnection(this.dbLoader.getDatabaseUrl());

            try {
                stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sqlString);

                for (String requiredField : requiredFields) {
                    hashResult.put(requiredField, resultSet.getObject(requiredField));
                }

            } catch (SQLException e) {
                // TODO: handle exception
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return hashResult;
    }

    public void createTableFromSQL(String createTableSQL) {
        hitDatabase(createTableSQL);
    }

    public ArrayList<Object> queryForAColumn(String sqlString, String columnName) {
        ArrayList<Object> resultList = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(this.dbLoader.getDatabaseUrl());

            try {
                stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(sqlString);

                while (resultSet.next()) {
                    resultList.add(resultSet.getObject(columnName));
                }

            } catch (SQLException e) {
                // TODO: handle exception
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        return resultList;
    }

}
