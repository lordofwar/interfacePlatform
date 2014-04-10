/**************************************************************************
 * $RCSfile: Query.java,v $  $Revision: 1.18 $  $Date: 2010/07/02 06:15:56 $
 *
 * $Log: Query.java,v $
 * Revision 1.18  2010/07/02 06:15:56  liuding
 * *** empty log message ***
 *
 * Revision 1.17  2010/05/15 08:51:53  liuding
 * *** empty log message ***
 *
 * Revision 1.16  2010/03/18 08:16:19  liuding
 * *** empty log message ***
 *
 * Revision 1.15  2010/03/17 09:10:11  liuding
 * *** empty log message ***
 *
 * Revision 1.14  2009/12/29 03:39:46  liuding
 * *** empty log message ***
 *
 * Revision 1.13  2009/12/25 03:39:50  liuding
 * *** empty log message ***
 *
 * Revision 1.12  2009/11/07 03:45:36  richie
 * *** empty log message ***
 *
 * Revision 1.11  2009/10/17 17:47:00  liuding
 * *** empty log message ***
 *
 * Revision 1.10  2009/07/30 05:44:00  richie
 * *** empty log message ***
 *
 * Revision 1.9  2009/07/30 05:26:34  richie
 * *** empty log message ***
 *
 * Revision 1.8  2009/07/30 02:29:54  richie
 * *** empty log message ***
 *
 * Revision 1.7  2009/07/30 02:29:37  richie
 * *** empty log message ***
 *
 * Revision 1.6  2009/06/08 11:38:27  richie
 * *** empty log message ***
 *
 * Revision 1.5  2009/05/20 02:37:15  richie
 * *** empty log message ***
 *
 * Revision 1.4  2009/05/14 06:35:38  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/04/13 05:11:46  richie
 * *** empty log message ***
 *
 * Revision 1.2  2009/03/03 06:25:12  richie
 * *** empty log message ***
 *
 * Revision 1.1  2009/02/26 06:16:46  richie
 * *** empty log message ***
 *
 * Revision 1.3  2009/01/04 01:21:04  richie
 * *** empty log message ***
 *
 **************************************************************************/
package com.gxlu.meta.engine.persister.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.hibernate.dialect.Dialect;

import com.gxlu.meta.tools.MetaConsts;

/**
 * @author K
 */
public class Query {

	public Query(Dialect dialect) {
		this.dialect = dialect;
		joins = new ArrayList<Join>();
		conditions = new ArrayList<String>();
		params = new ArrayList<Object>();
		tableAlias = new HashMap<String, String>();
		columnAlias = new StringBuffer();
		prefix = new StringBuffer();
		conditionBuffer = new StringBuffer();
		whereBuffer = new StringBuffer();
	}

	protected Dialect getDialect() {
		return dialect;
	}

	public void addCondition(String condition, Object[] values) {
		checkAndPrefix(whereBuffer);
		whereBuffer.append(condition);
		if (values != null) {
			for (Object object : values) {
				params.add(object);
			}
		}
	}

	public void simpleMiddleTableJoinTable(String oppositeTableName, String tableNameAlias, String oppositeKeyColumn,
			String baseTableNameAlias, String oppositeKey, String dbColumn, String value) {
		checkAndPrefix(conditionBuffer);
		prefix.append(", ");
		prefix.append(oppositeTableName);
		prefix.append(" " + tableNameAlias);

		conditionBuffer.append(" " + tableNameAlias);
		conditionBuffer.append(".");
		conditionBuffer.append(oppositeKeyColumn);
		conditionBuffer.append("=");
		conditionBuffer.append(" ");
		conditionBuffer.append(baseTableNameAlias);
		conditionBuffer.append(".");
		conditionBuffer.append(oppositeKey);

		columnAlias.append(",");
		columnAlias.append(" ").append(tableNameAlias).append(".");
		columnAlias.append(dbColumn);
		columnAlias.append(" ").append(tableNameAlias).append("_");
		columnAlias.append("OTHERID");
		columnList.append(",");
		columnList.append(" ").append(tableNameAlias).append(".");
		columnList.append(dbColumn);

		checkAndPrefix(whereBuffer);
		whereBuffer.append(tableNameAlias + "." + dbColumn + " IN (" + value + ") ");
	}

	public String addJoinTable(String baseTableName, String baseTableNameAlias, String baseTableKeyColumn,
			String oppositeTableName, String oppositeKeyColumn, String joinTableName, String coreKey,
			String oppositeKey, int multiple, List<String> columnNames, boolean needFecth, String tableNameAlias) {

		checkAndPrefix(conditionBuffer);

		if (joinTableName.equals(baseTableName) && joinTableName.equals(oppositeTableName)) {
			if (joinTableName.equals(baseTableName) && multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_N2ONE) {
				if (prefix.indexOf(oppositeTableName + " " + tableNameAlias) == -1) {
					prefix.append(" ,");
					prefix.append(oppositeTableName);
					prefix.append(" " + tableNameAlias);
				}

				// conditionBuffer.append(" OPPT");
				// conditionBuffer.append(point);
				conditionBuffer.append(" " + tableNameAlias);
				conditionBuffer.append(".");
				conditionBuffer.append(oppositeKeyColumn);
				checkInnerJoin(needFecth);
				conditionBuffer.append("=");
				conditionBuffer.append(" ");
				conditionBuffer.append(baseTableNameAlias);
				conditionBuffer.append(".");
				conditionBuffer.append(oppositeKey);
			} else if (joinTableName.equals(oppositeTableName)
					&& multiple == MetaConsts.VALUE_RELATIONSPEC_MULTIPLE_ONE2N) {
				if (prefix.indexOf(oppositeTableName + " " + tableNameAlias) == -1) {
					prefix.append(" ,");
					prefix.append(oppositeTableName);
					prefix.append(" " + tableNameAlias);
				}

				// conditionBuffer.append(" OPPT");
				// conditionBuffer.append(point);
				conditionBuffer.append(" " + tableNameAlias);

				conditionBuffer.append(".");
				conditionBuffer.append(coreKey);
				checkInnerJoin(needFecth);
				conditionBuffer.append("=");
				conditionBuffer.append(" ");
				conditionBuffer.append(baseTableNameAlias);
				conditionBuffer.append(".");
				conditionBuffer.append(baseTableKeyColumn);
				needDistinct = true;
			}
		} else if (joinTableName.equals(baseTableName)) {
			if (prefix.indexOf(oppositeTableName + " " + tableNameAlias) == -1) {
				prefix.append(" ,");
				prefix.append(oppositeTableName);
				prefix.append(" " + tableNameAlias);
			}

			// conditionBuffer.append(" OPPT");
			// conditionBuffer.append(point);
			conditionBuffer.append(" " + tableNameAlias);
			conditionBuffer.append(".");
			conditionBuffer.append(oppositeKeyColumn);
			checkInnerJoin(needFecth);
			conditionBuffer.append("=");
			conditionBuffer.append(" ");
			conditionBuffer.append(baseTableNameAlias);
			conditionBuffer.append(".");
			conditionBuffer.append(oppositeKey);
		} else if (joinTableName.equals(oppositeTableName)) {
			// 卫星表也会用到这个逻辑 有可能主表没加进来
			if (prefix.indexOf(baseTableName + " " + baseTableNameAlias) == -1) {
				prefix.append(" ," + baseTableName + " " + baseTableNameAlias);
			}

			if (prefix.indexOf(oppositeTableName + " " + tableNameAlias) == -1) {
				prefix.append(" ,");
				prefix.append(oppositeTableName);
				prefix.append(" " + tableNameAlias);
			}

			// conditionBuffer.append(" OPPT");
			// conditionBuffer.append(point);
			conditionBuffer.append(" " + tableNameAlias);
			conditionBuffer.append(".");
			conditionBuffer.append(coreKey);
			checkInnerJoin(needFecth);
			conditionBuffer.append("=");
			conditionBuffer.append(" ");
			conditionBuffer.append(baseTableNameAlias);
			conditionBuffer.append(".");
			conditionBuffer.append(baseTableKeyColumn);
			if (!coreKey.equals("ENTITYID")) {
				needDistinct = true;
			}
		} else {
			// 中间表关联的情况
			prefix.append(" ,");
			prefix.append(oppositeTableName);
			// prefix.append(" OPPT");
			// prefix.append(point);
			prefix.append(" " + tableNameAlias);
			prefix.append(" ,");
			prefix.append(joinTableName);
			prefix.append(" JOINT");
			// TODO 重构 4是OPPT长度
			prefix.append(tableNameAlias.substring(DefaultNamingMetaObjectParseStrategy.elseTableAlias.length()));

			// conditionBuffer.append(" OPPT");
			// conditionBuffer.append(point);
			conditionBuffer.append(" " + tableNameAlias);
			conditionBuffer.append(".");
			conditionBuffer.append(oppositeKeyColumn);
			checkInnerJoin(needFecth);
			conditionBuffer.append("=");
			conditionBuffer.append(" JOINT");
			// conditionBuffer.append(point);
			conditionBuffer.append(tableNameAlias.substring(DefaultNamingMetaObjectParseStrategy.elseTableAlias
					.length()));
			conditionBuffer.append(".");
			conditionBuffer.append(oppositeKey);

			conditionBuffer.append(" AND");

			conditionBuffer.append(" ");
			conditionBuffer.append(baseTableNameAlias);
			conditionBuffer.append(".");
			conditionBuffer.append(baseTableKeyColumn);
			conditionBuffer.append("=");
			conditionBuffer.append(" JOINT");
			conditionBuffer.append(tableNameAlias.substring(DefaultNamingMetaObjectParseStrategy.elseTableAlias
					.length()));
			// conditionBuffer.append(point);
			conditionBuffer.append(".");
			conditionBuffer.append(coreKey);
			checkInnerJoin(needFecth);

			needDistinct = true;
		}
		addColumnAlias(tableNameAlias, columnNames, columnAlias, true);

		String tableName = DefaultNamingMetaObjectParseStrategy.elseTableAlias + point;
		// addConditions(expressions, tableName);
		point++;

		return tableName;
	}

	private void checkInnerJoin(boolean needFecth) {
		if (needFecth) {
			conditionBuffer.append(" (+) ");
		}
	}

	// public String addJoinTable(String baseTableName, String
	// baseTableNameAlias, String baseTableKeyColumn, String oppositeTableName,
	// String oppositeKeyColumn, String joinTableName, String coreKey, String
	// oppositeKey, int multiple, String columnName, boolean needFecth) {
	// List<String> lists = new ArrayList<String>();
	// lists.add(columnName);
	// return addJoinTable(baseTableName, baseTableNameAlias,
	// baseTableKeyColumn,oppositeTableName, oppositeKeyColumn, joinTableName,
	// coreKey, oppositeKey, multiple, lists, needFecth);
	// }

	public String setCoreTable(String coreTableName, String coreKeyColumn, List<String> coreColumnNames) {

		this.coreTableName = coreTableName;
		this.coreKeyColumn = coreKeyColumn;
		this.coreColumnNames = coreColumnNames;

		// columnAlias.append("SELECT ");

		addColumnAlias(DefaultNamingMetaObjectParseStrategy.coreTableAlias, coreColumnNames, columnAlias, false);

		prefix.append(" FROM ");
		prefix.append(coreTableName);
		prefix.append(" " + DefaultNamingMetaObjectParseStrategy.coreTableAlias);

		String tableName = "CORET";
		// addConditions(expressions, tableName);

		return DefaultNamingMetaObjectParseStrategy.coreTableAlias;
	}

	private void checkAndPrefix(StringBuffer buffer) {
		if (buffer.length() > 0) {
			buffer.append(" AND ");
		}
	}

	private String makeUpReplace(String tableName, String columnName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("_");
		buffer.append(tableName).append(".").append(columnName);
		String replace = buffer.toString();
		return replace;
	}

	public Object[] toQueryParams() {
		return params.toArray();
	}

	public List<String> toOrderStatementString(Vector<String> orderColumns) {
		List<String> orders = new ArrayList<String>();
		for (String columnWithTable : orderColumns) {
			String[] column = columnWithTable.split(".");
			String tableName = column[0];
			String alias = tableAlias.get(tableName);
			orders.add(alias + "." + column[1]);
		}

		return orders;
	}

	public int getJoinSequence() {
		return joinSequence;
	}

	public void setJoinSequence(int joinSequence) {
		this.joinSequence = joinSequence;
	}

	public String getTableAlias(String tableName) {

		return tableAlias.get(tableName);
	}

	public String toQueryStatementString(String[] orders) {

		if (conditionBuffer.length() > 0) {
			conditionBuffer.insert(0, " WHERE ");
			if (whereBuffer.length() > 0) {
				conditionBuffer.append(" AND ");
			}
		} else if (whereBuffer.length() > 0) {
			whereBuffer.insert(0, " WHERE ");
		}
		String columns = columnAlias.toString();
		if (orders != null) {
			for (String order : orders) {
				if (!columns.contains(order)) {
					columnAlias.append(", ").append(order).append(" ").append(order.replace(".", "_"));
				}
			}
		}

		String groupBy = "";
		if (needDistinct) {
			// columnAlias.insert(0, "SELECT DISTINCT ");
			groupBy = " GROUP BY " + columnList.toString();
			columnAlias.insert(0, "SELECT ");
		} else {
			columnAlias.insert(0, "SELECT ");
		}

		String finalString = columnAlias.toString() + prefix.toString() + conditionBuffer.toString()
				+ whereBuffer.toString() + groupBy;
		finalString = finalString.replaceAll("AND AND", "AND");
		finalString = finalString.replaceAll("AND  AND", "AND");

		return finalString;
	}

	private boolean addColumnAlias(String tableName, List<String> columnNames, StringBuffer columnAlias,
			boolean needPrintComma) {
		for (String column : columnNames) {
			if (needPrintComma) {
				columnAlias.append(",");
				columnList.append(",");
			}
			needPrintComma = true;
			columnAlias.append(" ").append(tableName).append(".");
			columnAlias.append(column);

			// oracle 别名不能超过30
			columnAlias.append(" ").append(tableName).append("_");
			if (tableName.length() + column.length() >= 30) {
				columnAlias.append(column.substring(0, 29 - tableName.length()));
			} else {
				columnAlias.append(column);
			}

			columnList.append(" ").append(tableName).append(".");
			columnList.append(column);
		}
		return needPrintComma;
	}

	private Dialect dialect;
	private String coreTableName;
	private String coreKeyColumn;
	private List<Join> joins;
	private List<String> conditions;
	private List<Object> params;
	private Map<String, String> tableAlias;
	private List<String> coreColumnNames;
	private int joinSequence = 0;
	StringBuffer columnAlias;

	private StringBuffer columnList = new StringBuffer();

	StringBuffer prefix;
	StringBuffer whereBuffer;
	StringBuffer conditionBuffer;
	private int point = 1;
	private boolean needDistinct = false;

	private class Join {

		public Join(int joinSequence, String oppositeTableName, String oppositeKeyColumn, String joinTableName,
				String coreKey, String oppositeKey, List<String> columnNames) {
			this.oppositeTableName = oppositeTableName;
			this.oppositeKeyColumn = oppositeKeyColumn;
			this.joinTableName = joinTableName;
			this.coreKey = coreKey;
			this.oppositeKey = oppositeKey;
			this.columnNames = columnNames;
			this.joinSequence = joinSequence;
		}

		public Join(int joinSequence, String oppositeTableName, String oppositeKeyColumn, String joinTableName,
				String coreKey, String oppositeKey, String columnName) {
			this.oppositeTableName = oppositeTableName;
			this.oppositeKeyColumn = oppositeKeyColumn;
			this.joinTableName = joinTableName;
			this.coreKey = coreKey;
			this.oppositeKey = oppositeKey;
			columnNames = new ArrayList<String>();
			columnNames.add(columnName);
			this.joinSequence = joinSequence;
		}

		public String getOppositeTableName() {
			return oppositeTableName;
		}

		public void setOppositeTableName(String oppositeTableName) {
			this.oppositeTableName = oppositeTableName;
		}

		public String getOppositeKeyColumn() {
			return oppositeKeyColumn;
		}

		public void setOppositeKeyColumn(String oppositeKeyColumn) {
			this.oppositeKeyColumn = oppositeKeyColumn;
		}

		public String getJoinTableName() {
			return joinTableName;
		}

		public void setJoinTableName(String joinTableName) {
			this.joinTableName = joinTableName;
		}

		public String getCoreKey() {
			return coreKey;
		}

		public void setCoreKey(String coreKey) {
			this.coreKey = coreKey;
		}

		public List<String> getColumnNames() {
			return columnNames;
		}

		public int getJoinSequence() {
			return joinSequence;
		}

		public void setJoinSequence(int joinSequence) {
			this.joinSequence = joinSequence;
		}

		public void setColumnNames(List<String> columnNames) {
			this.columnNames = columnNames;
		}

		public String getOppositeKey() {
			return oppositeKey;
		}

		public void setOppositeKey(String oppositeKey) {
			this.oppositeKey = oppositeKey;
		}

		private String oppositeTableName;
		private String oppositeKeyColumn;
		private String joinTableName;
		private String coreKey;
		private String oppositeKey;
		private List<String> columnNames;
		private int joinSequence;
	}
}
