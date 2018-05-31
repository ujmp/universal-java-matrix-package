package org.ujmp.jdbc;

import org.ujmp.core.mapmatrix.AbstractMapMatrix;
import org.ujmp.jdbc.table.JDBCTable;

import java.sql.*;
import java.util.*;

public class JDBCDataset extends AbstractMapMatrix<Map<String, Object>, Map<String, Object>> {

    private final String url;
    private final String username;
    private final String password;
    private String tableName;
    private final Set<String> columnsForKeys;
    private boolean tableExists = false;

    private transient Connection connection = null;

    protected transient Map<Set<String>, PreparedStatement> insertStatements = null;


    public JDBCDataset(String url, String tableName, String userName, String password, String... columnsForKeys) {
        this.url = url;
        this.username = userName;
        this.password = password;
        this.tableName = tableName;
        this.columnsForKeys = new TreeSet<String>(Arrays.asList(columnsForKeys));

        if (columnsForKeys.length == 0) {
            throw new RuntimeException("no key columns defined");
        }

        // if no table name is defined, create one
        if (this.tableName == null) {
            this.tableName = "temp_table_" + System.currentTimeMillis();
        }
    }

    @Override
    protected void clearMap() {

    }

    @Override
    protected Map<String, Object> removeFromMap(Object key) {
        return null;
    }


    public Map<String, Object> put(Object key, Map<String, Object> values) {
        if (columnsForKeys.size() == 1) {
            Map<String, Object> keys = new TreeMap<>();
            keys.put(columnsForKeys.iterator().next(), key);
            return putIntoMap(keys, values);
        } else {
            throw new RuntimeException("more than one key column");
        }
    }


    @Override
    protected Map<String, Object> putIntoMap(Map<String, Object> keys, Map<String, Object> values) {
        try {
            Map<String, Object> fieldsAndValues = new TreeMap<>();
            fieldsAndValues.putAll(keys);
            fieldsAndValues.putAll(values);
            Set<String> keySet = fieldsAndValues.keySet();
            if (insertStatements == null) {
                insertStatements = new HashMap<Set<String>, PreparedStatement>();
            }
            PreparedStatement insertStatement = insertStatements.get(keySet);
            if (insertStatement == null || insertStatement.isClosed()) {
                initDatabase(fieldsAndValues);
                StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO " + tableName + " (");
                int i = 0;
                for (String key : keySet) {
                    sql.append(key);
                    if (i++ < keySet.size() - 1) {
                        sql.append(", ");
                    }
                }
                sql.append(") VALUES (");
                for (; i > 0; i--) {
                    sql.append("?");
                    if (i > 1) {
                        sql.append(", ");
                    }
                }
                sql.append(")");
                insertStatement = getConnection().prepareStatement(sql.toString());
                insertStatements.put(keySet, insertStatement);
            }
            int i = 1;
            for (Entry<String, Object> entry : fieldsAndValues.entrySet()) {
                insertStatement.setString(i++, "" + entry.getValue());
            }
            int count = insertStatement.executeUpdate();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean tableExists() throws SQLException {
        if (!tableExists) {
            DatabaseMetaData dbm = getConnection().getMetaData();
            ResultSet tables = dbm.getTables(null, null, "%", null);
            while (tables.next()) {
                if (tableName.equalsIgnoreCase(tables.getString(3))) {
                    tableExists = true;
                }
            }
            tables.close();
        }
        return tableExists;
    }


    private void initDatabase(Map<String, Object> fieldsAndValues) throws SQLException {
        // check if table already exists and create if necessary
        if (!tableExists()) {
            Set<String> valueColumns = new TreeSet<String>();
            for (String field : fieldsAndValues.keySet()) {
                if (!columnsForKeys.contains(field)) {
                    valueColumns.add(field);
                }
            }

            StringBuilder sql = new StringBuilder();

            sql.append("CREATE TABLE ");
            sql.append(this.tableName);
            sql.append(" (");
            for (String keyColumn : columnsForKeys) {
                sql.append(keyColumn);
                sql.append(" VARCHAR(255), ");
            }
            for (String valueColumn : valueColumns) {
                sql.append(valueColumn);
                sql.append(" TEXT, ");
            }
            sql.append("PRIMARY KEY (");
            int i = 0;
            for (String keyColumn : columnsForKeys) {
                sql.append(keyColumn);
                if (i++ < columnsForKeys.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append("))");

            PreparedStatement statement;
            statement = getConnection().prepareStatement(sql.toString());
            statement.execute();
            statement.close();
        } else {
            // if no key column is defined, assume that it is the primary key
            // column
            // if (this.keyColumn == null) {
            // ResultSet rs = dbm.getPrimaryKeys(null, null, this.tableName);
            // if (rs.next()) {
            // this.keyColumn = rs.getString("COLUMN_NAME");
            // } else {
            // throw new RuntimeException("table must have a primary key");
            // }
            // if (rs.next()) {
            // throw new
            // RuntimeException("table must not have more than one primary key");
            // }
            // rs.close();
            // }
        }
    }


    protected synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public Map<String, Object> get(Object key) {
        return null;
    }

    @Override
    public Set<Map<String, Object>> keySet() {
        return null;
    }


    public static JDBCDataset connectToMySQL(String serverName, int port, String databaseName, String tableName,
                                             String userName, String password, String keyColumns) throws SQLException {
        return new JDBCDataset("jdbc:mysql://" + serverName + ":" + port + "/" + databaseName
                + "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull", tableName, userName,
                password, keyColumns);
    }
}
