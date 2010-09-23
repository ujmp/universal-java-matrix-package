package org.ujmp.jdbc;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.collections.AbstractMap;
import org.ujmp.core.interfaces.Erasable;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

public abstract class JDBCSQLMap<K, V> extends AbstractMap<K, V> implements Closeable,Erasable {
  

  private static final long serialVersionUID = 9193486303304692838L;

  private transient Connection connection = null;
  
  private transient Class dbDriver = null;

  private transient StringBuilder sb = new StringBuilder();

  private final String url;

  private final String dbName;

  private final String dbTable;

  private final String username;

  private String password = null;

  private final String tableName;

  private final String columnForKeys;

  private final String columnForValues;

  private String typeOfKey;

  private String typeOfValue;

  private boolean neverUsed = true;

  private boolean tableDidNotExist = false;
  
  private transient DatabaseMetaData dbFeatures;
  
  private transient PreparedStatement preparedInsertStatement = null;
  private transient PreparedStatement preparedUpdateStatement = null;
  private transient PreparedStatement preparedSelectStatement = null;
  private transient PreparedStatement preparedDeleteStatement = null;

  // TODO Test with hsqldb and PostGreSQL
  public JDBCSQLMap(String serverName,String portNumber, String databaseName, String username, String password, String tableName,
      String columnForKeys, String columnForValues) throws ClassNotFoundException,SQLException {
    this("jdbc:mysql://"+ serverName + ":" + portNumber, databaseName ,username, password, tableName, columnForKeys, columnForValues);

  }

  public JDBCSQLMap(String url,String dbName,  String username, String password, String tableName,
      String columnForKeys, String columnForValues) throws ClassNotFoundException,SQLException {

    this.url = url;
    this.username = username;
    this.password = password;
    this.tableName = tableName;
    this.dbName = dbName;
    this.columnForKeys = columnForKeys;
    this.columnForValues = columnForValues;

    sb.setLength(0);
    sb.append("`").append(dbName).append("`.").append("`").append(tableName).append("`");
    dbTable = sb.toString();

    if (url.startsWith("jdbc:hsqldb:")) {
      dbDriver = Class.forName("org.hsqldb.jdbcDriver");
    } else if (url.startsWith("jdbc:mysql:")) {
      dbDriver = Class.forName("com.mysql.jdbc.Driver");
    } else if (url.startsWith("jdbc:postgresql:")) {
      dbDriver = Class.forName("org.postgresql.Driver");
    } else if (url.startsWith("jdbc:derby:")) {
      dbDriver = Class.forName("org.apache.derby.jdbc.Driver40");
    }
    Statement statement = getConnection().createStatement();
    statement.execute("USE `"+ dbName + "`;");
    statement.close();
    createTableIfNotExists();
    
  }

  public JDBCSQLMap() throws ClassNotFoundException, IOException, SQLException {
    this("jdbc:hsqldb:"+ File.createTempFile("hsqldbtemp", "").getAbsolutePath()+ ";shutdown=true", "" , "SA", "", "tmpMapTable", "keyColumn", "valueColumn");
  }

  private void createTableIfNotExists() throws SQLException {
    // TODO: add primary key
    Statement statement = getConnection().createStatement();

    sb.setLength(0);
    sb.append("CREATE TABLE ").append(dbTable).append("( ").append(dbTable).append(".`").append(columnForKeys).append("` REAL").append(" UNIQUE NOT NULL, ")
    .append(dbTable).append(".`").append(columnForValues).append("` REAL").append("")
    .append(" );");
    System.out.println(sb.toString());
    try{
      statement.execute(sb.toString());
    }catch (java.sql.SQLException e) {   
      if(e.getMessage().equals("Table '"+tableName+"' already exists")){
        System.out.println("Warning: table already there");
      }
      else{
        e.printStackTrace();
      }
      // TODO: handle exception
    }
    //    if(K instanceof Integer)


  }

  public synchronized Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {

      connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }
    
    return connection;
  }
  
  public void printDBFeatures() throws SQLException{
    
  dbFeatures = getConnection().getMetaData();

  
  System.out.println("dfghfdhdfghfghfghhhfhdfg" + dbFeatures.getURL());
  
  ResultSet rsColumns = null;

  rsColumns = dbFeatures.getColumns(null, null, "test", null);
  
  System.out.println(rsColumns.getObject(4));
  
  while (rsColumns.next()) {
    String columnName = rsColumns.getString("COLUMN_NAME");
    System.out.println("column name=" + columnName);
    String columnType = rsColumns.getString("TYPE_NAME");
    System.out.println("type:" + columnType);
    int size = rsColumns.getInt("COLUMN_SIZE");
    System.out.println("size:" + size);
    int nullable = rsColumns.getInt("NULLABLE");
    if (nullable == DatabaseMetaData.columnNullable) {
      System.out.println("nullable true");
    } else {
      System.out.println("nullable false");
    }
    int position = rsColumns.getInt("ORDINAL_POSITION");
    System.out.println("position:" + position);
  }

  System.out.println(getConnection().getCatalog());
  }

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void erase() throws IOException {
    try {
      Statement st = getConnection().createStatement();
      st.execute("DROP TABLE " + dbTable + ";");
    } catch (SQLException e) {
      throw new IOException(e.toString());
    }
    close();

    // maybe it was just a temporary database?
    if (url.contains("hsqldbtemp")) {
      String[] s = url.split(":");
      if (s.length > 2) {
        String file = "";
        for (int i = 2; i < s.length; i++) {
          file += s[i];
          if (i < s.length - 1) {
            file += ":";
          }
        }
        s = file.split(";");
        file = s[0];

        File file1 = new File(file);
        if (file1.exists()) {
          file1.delete();
        }
        File file2 = new File(file + ".log");
        if (file2.exists()) {
          file2.delete();
        }
        File file3 = new File(file + ".properties");
        if (file3.exists()) {
          file3.delete();
        }
        File file4 = new File(file + ".script");
        if (file4.exists()) {
          file4.delete();
        }
      }
    }
  }

  public synchronized void close() throws IOException {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException e) {
      throw new IOException(e.toString());
    }
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub

  }

  @SuppressWarnings("unchecked")
  @Override
  public V get(Object key) {
    Object o = null;
    System.out.println("GET: " + key.toString());
    try {
      Statement statement = getConnection().createStatement();
      sb.setLength(0);
      sb.append("SELECT ").append(dbTable).append(".`").append(columnForValues)
      .append("` FROM ").append(dbTable)
      .append(" WHERE ").append(dbTable).append(".`").append(columnForKeys).append("` = '").append(key.toString()).append("';");
      System.out.println("\t" + sb.toString());
      ResultSet rs = statement.executeQuery(sb.toString());
      if(rs.first()){
        o = rs.getObject(columnForValues);
      }
      System.out.println(o);
      statement.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return o != null ?  (V) o : null;
  }

  public Set<K> keySet() {
    // TODO Auto-generated method stub
    return null;
  }

  public V put(K key, V value) throws UnsupportedOperationException{
    // TODO Auto-generated method stub
    if(!neverUsed)
    {
      sb.setLength(0);
      sb.append("INSERT INTO ");
      sb.append(dbTable);
      sb.append(" ( `");
      sb.append(columnForKeys);
      sb.append("`, `");
      sb.append(columnForValues);
      sb.append("` ) VALUES ( '");
      sb.append(((K)key));
      sb.append("', '");
      sb.append(((V)value));
      sb.append("' );");

      //          if (useExtendedSQL) {
      //            sb.append(" on duplicate key update ");
      //            sb.append(columnForValue);
      //            sb.append("=?");
      //          }
      
      try {
        Statement statement = getConnection().createStatement();
        statement.execute(sb.toString());
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
        if(key instanceof String){
          if(e.getMessage().startsWith("Data truncation: Data too long for column '" + columnForKeys +"'")){
            if(setColumnSize(columnForKeys, typeOfKey, key))
              put(key, value);
          }
          else{
            System.out.println("Warning: Data Truncation Key");
          }
        }
        if(value instanceof String){
          if(e.getMessage().startsWith("Data truncation: Data too long for column '" + columnForValues +"'")){
            if(setColumnSize(columnForValues, typeOfValue, value))
              put(key, value);
          }
          else{
            System.out.println("Warning: Data Truncation Value");
          }
        }
        else{
          e.printStackTrace();
        }
      }



    }
    // neverUsed
    else{
      typeOfKey = getType(key);
      typeOfValue = getType(value);
      neverUsed = false;
      setColumnSize(columnForKeys, typeOfKey, key);
      setColumnSize(columnForValues, typeOfValue, value);
      sb.setLength(0);
      if(tableDidNotExist){
        try {
          Statement statement = getConnection().createStatement();
          sb.append("CREATE INDEX `ix1` ON ").append(dbTable).append(" ( `").append(columnForKeys).append("` ); ");
          System.out.println(sb.toString());
          statement.execute(sb.toString());
          statement.close();
        } catch (SQLException e) {

          e.printStackTrace();

          throw new UnsupportedOperationException(e.getMessage(), e.getCause());
          // TODO Auto-generated catch block

        }
      }
      put(key, value);
    }
    return null;
  }

  private boolean setColumnSize(String colName, String colType, Object o){
    try {
      Statement statement = getConnection().createStatement();
      sb.setLength(0);
      sb.append("ALTER TABLE ").append(dbTable)
      .append(" MODIFY COLUMN ").append(dbTable).append(".`").append(colName).append("` ").append(colType);
      Integer length = getSize(o);
      if(length != null){
        sb.append("(").append(length).append(")");
      }
      sb.append(";");
      System.out.println(sb.toString());
      statement.execute(sb.toString());
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;

  }

  private String getType(Object o) {
    try {
      if(dbDriver == Class.forName("org.hsqldb.jdbcDriver")){
        if(o instanceof String){
          return "VARCHAR";
        }
        
      } 
      else if (dbDriver == Class.forName("com.mysql.jdbc.Driver")) {
        if(o instanceof String){
          return "VARCHAR";
        }
        
      } 
      else if (dbDriver == Class.forName("org.postgresql.Driver")) {
        if(o instanceof String){
          return "VARCHAR";
        }
        
      } 
      else if (dbDriver == Class.forName("org.apache.derby.jdbc.Driver40")) {
        if(o instanceof String){
          return "VARCHAR";
        }

      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Integer getSize(Object o) {
    if(o instanceof String){
      return ((String)o).length();
    }
    return null;
  }

  @Override
  public V remove(Object key) {
    V value = get(key);
    System.out.println("REMOVE: " + key.toString());
    if(preparedDeleteStatement == null)
    {
      prepareDeleteStatement();
    }

    try {
      preparedDeleteStatement.setObject(1, key);
      System.out.println("\t"+preparedDeleteStatement.toString());
      preparedDeleteStatement.executeUpdate();
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return value;
  }

  private void prepareDeleteStatement() {
    sb.setLength(0);
    sb.append("DELETE FROM ");
    sb.append(dbTable);
    sb.append(" WHERE ");
    sb.append("( ").append(dbTable).append(".`").append(columnForKeys).append("`").append(" = ").append("?").append(" ); ");
    PreparedStatement ps;
    try {
      preparedDeleteStatement = getConnection().prepareStatement(sb.toString());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  @Override
  public int size() {
    int ret = 0;
    try {
      Statement statement = getConnection().createStatement();
      sb.setLength(0);
      sb.append("SELECT DISTINCT `").append(columnForKeys).append("` FROM ").append(dbTable).append(";");
      ResultSet rs = statement.executeQuery(sb.toString());
      rs.last();
      ret = rs.getRow();
      statement.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return ret;
  }
}
