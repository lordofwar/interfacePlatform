package com.gxlu.international.common.utils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonTool {
	
	private static Log logger = LogFactory.getLog(CommonTool.class);
	
	/**
	 * 根据表字段的数据类型进行参数设置
	 * @param n 第n个参数
	 * @param m 参数类型，0为字符串，1为整型，2为日期, 3为浮点（0.1）,4为布尔，5为浮点（0.01）
	 * @param value 字符串值
	 * @param ps
	 * @throws SQLException 
	 */
	public static void setParmerter(int n, int m, String value, PreparedStatement prst) throws SQLException {
		if (value == null) {
			value = "";
		} else {
			value = value.trim();
		}
		switch(m) {
		case 0:
			if (value != null && !"".equals(value)) {
				prst.setString(n, value);
			} else {
				prst.setNull(n, Types.VARCHAR);
			}
			break;
		case 1:
			if (value != null && !"".equals(value)) {
				try {
					prst.setInt(n, Integer.parseInt(value));
				} catch (NumberFormatException e) {
					prst.setNull(n, Types.INTEGER);
					logger.error(e);
					e.printStackTrace();
				}
			} else {
				prst.setNull(n, Types.INTEGER);
			}
			break;
		case 2:
			java.sql.Date dateValue = toDate(value);
			if (dateValue != null) {
				prst.setDate(n, dateValue);
			} else {
				prst.setNull(n, Types.DATE);
			}
			break;
		case 3:
			if (value != null && !"".equals(value)) {
				try {
					float f = Float.parseFloat(value);
					BigDecimal bd = new BigDecimal(f);
					prst.setFloat(n, bd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
				} catch (NumberFormatException e) {
					prst.setNull(n, Types.FLOAT);
					logger.error(e);
					e.printStackTrace();
				}
			} else {
				prst.setNull(n, Types.FLOAT);
			}
			break;
		case 4:
			if (value != null && !"".equals(value)) {
				if ("true".equals(value.toLowerCase())) {
					prst.setInt(n, 0);
				} else {
					prst.setInt(n, 1);
				}
			} else {
				prst.setNull(n, Types.INTEGER);
			}
			break;
		case 5:
			if (value != null && !"".equals(value)) {
				try {
					float f = Float.parseFloat(value);
					BigDecimal bd = new BigDecimal(f);
					prst.setFloat(n, bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
				} catch (NumberFormatException e) {
					prst.setNull(n, Types.FLOAT);
					logger.error(e);
					e.printStackTrace();
				}
			} else {
				prst.setNull(n, Types.FLOAT);
			}
			break;
		}
	}
	
	/**
	 * 将字符串转化为日期类型
	 * @param value 供转化的日期字符串
	 * @return
	 */
	public static java.sql.Date toDate(String value) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		DateFormat df3 = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
		java.sql.Date d = null;
		Date date = null;
		if (value != null && !"".equals(value)) {
			try {
				date = df.parse(value);
				d = new java.sql.Date(date.getTime());
			} catch (ParseException e) {
				try {
					date = df2.parse(value);
					d = new java.sql.Date(date.getTime());
				} catch (ParseException e1) {
					try {
						date = df3.parse(value);
						d = new java.sql.Date(date.getTime());
					} catch (ParseException e2) {
						logger.info("数据：" + value+ "为不可以识别的日期数据格式，放弃存储此字段数据");
						logger.error(e2);
						e2.printStackTrace();
					}
				}
			}
		}
		return d;
	}
	
	/**
	 * 解析通过"*"符号分割的乘法计算式，并转换为long型
	 * @param value
	 * @return
	 */
	public static long parseLong(String value) {
		StringTokenizer st = new StringTokenizer(value, "*");
		long result = 1;
		while (st.hasMoreElements()) {
			String item = ((String) st.nextElement()).trim();
			result = Long.parseLong(item) * result;
		}
		return result;
	}
	
	/**
	 * 
	 * 日期格式化：20110301
	 * @param Date date
	 * 
	 */
	public static String FormatDate(Date date) {
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
    String formatDate = null;
    try {
      formatDate = simpleDate.format(date);
    }
    catch(Exception e) {
      logger.error("FormatDate() has exception:", e);
    }
    return formatDate;
  }
	
	public static String yyyyMMddHH_Date(Date date) {
    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHH");
    String formatDate = null;
    try {
      formatDate = simpleDate.format(date);
    }
    catch(Exception e) {
      logger.error("FormatDate() has exception:", e);
    }
    return formatDate;
  }
}
