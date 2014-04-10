package com.gxlu.meta.engine.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**

 * SqlRowSetOracleResultSetExtractor

 */

public class SqlRowSetOracleResultSetExtractor implements ResultSetExtractor {
  public Object extractData(ResultSet rs) throws SQLException {
    return createSqlRowSet(rs);
  }

  /**
   * Create a SqlRowSet that wraps the given ResultSet,
   * representing its data in a disconnected fashion.
   * <p>This implementation creates a Spring ResultSetWrappingSqlRowSet
   * instance that wraps a standard JDBC CachedRowSet instance.
   * Can be overridden to use a different implementation.
   * @param rs the original ResultSet (connected)
   * @return the disconnected SqlRowSet
   * @throws SQLException if thrown by JDBC methods
   * @see #newCachedRowSet
   * @see org.springframework.jdbc.support.rowset.ResultSetW rappingSqlRowSet
   */
  protected SqlRowSet createSqlRowSet(ResultSet rs) throws SQLException {
//	  System.out.println("time:"+rs.getTimestamp("CORET_INSTALLDATE"));
    CachedRowSet rowSet = newCachedRowSet();
    rowSet.populate(rs);
    return new ResultSetWrappingSqlRowSet(rowSet);
  }

  /**
   * Create a new CachedRowSet instance, to be populated by
   * the <code>createSqlRowSet</code> implementation.
   * <p>This implementation creates a new instance of
   * Oracle's <code>oracle.jdbc.rowset.OracleCachedRowSet</code> class,
   * which is their implementation of the Java 1.5 CachedRowSet interface.
   * @return a new CachedRowSet instance
   * @throws SQLException if thrown by JDBC methods
   * @see #createSqlRowSet
   * @see oracle.jdbc.rowset.OracleCachedRowSet
   */
  protected CachedRowSet newCachedRowSet() throws SQLException {
    return new OracleCachedRowSet();
  }
}
