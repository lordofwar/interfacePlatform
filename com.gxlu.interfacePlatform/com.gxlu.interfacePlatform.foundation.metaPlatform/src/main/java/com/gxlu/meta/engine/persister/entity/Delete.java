/**************************************************************************
 * $RCSfile: Delete.java,v $  $Revision: 1.3 $  $Date: 2009/10/15 06:10:52 $
 *
 * $Log: Delete.java,v $
 * Revision 1.3  2009/10/15 06:10:52  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2009/01/04 01:21:04  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.engine.persister.entity;

import org.hibernate.util.StringHelper;

/**
 * An SQL <tt>DELETE</tt> statement
 *
 * @author lethe
 */
public class Delete {

	private String tableName;
	private String[] primaryKeyColumnNames;
	private String versionColumnName;
	private String where;

	private String comment;
	public Delete setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Delete setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public String toStatementString() {
		StringBuffer buf = new StringBuffer( tableName.length() + 10 );
		if ( comment!=null ) {
			buf.append( "/* " ).append(comment).append( " */ " );
		}
		buf.append( "DELETE FROM " ).append(tableName);
		if ( where != null || primaryKeyColumnNames != null || versionColumnName != null ) {
			buf.append( " WHERE " );
		}
		boolean conditionsAppended = false;
		if ( primaryKeyColumnNames != null ) {
			buf.append( StringHelper.join( "=? AND ", primaryKeyColumnNames ) ).append( "=?" );
			conditionsAppended = true;
		}
		if ( where!=null ) {
			if ( conditionsAppended ) {
				buf.append( " AND " );
			}
			buf.append( where );
			conditionsAppended = true;
		}
		if ( versionColumnName!=null ) {
			if ( conditionsAppended ) {
				buf.append( " AND " );
			}
			buf.append( versionColumnName ).append( "=?" );
		}
		return buf.toString();
	}

	public Delete setWhere(String where) {
		this.where=where;
		return this;
	}

	public Delete addWhereFragment(String fragment) {
		if ( where == null ) {
			where = fragment;
		}
		else {
			where += ( " AND " + fragment );
		}
		return this;
	}

	public Delete setPrimaryKeyColumnNames(String[] primaryKeyColumnNames) {
		this.primaryKeyColumnNames = primaryKeyColumnNames;
		return this;
	}

	public Delete setVersionColumnName(String versionColumnName) {
		this.versionColumnName = versionColumnName;
		return this;
	}

}
