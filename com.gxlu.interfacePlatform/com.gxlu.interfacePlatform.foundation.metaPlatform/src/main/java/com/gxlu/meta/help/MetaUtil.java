/**********************************************************************
 *
 * $RCSfile: MetaUtil.java,v $  $Revision: 1.4 $  $Date: 2009/10/14 16:14:52 $
 *
 * $Log: MetaUtil.java,v $
 * Revision 1.4  2009/10/14 16:14:52  liuding
 * *** empty log message ***
 *
 * Revision 1.3  2009/09/28 01:59:42  liuding
 * *** empty log message ***
 *
 * Revision 1.2  2009/09/21 13:02:18  liuding
 * *** empty log message ***
 *
 * Revision 1.1  2009/09/09 09:22:45  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : MetaUtil.java
 * Created on : Sep 8, 2009 10:11:24 PM
 * Creator : lethe
 */
package com.gxlu.meta.help;


import com.gxlu.meta.MetaAttribute;
import com.gxlu.meta.MetaClass;
import com.gxlu.meta.MetaClassRelation;
import com.gxlu.meta.QueryCriteria;

/**
 * <pre>
 * Description : TODO
 * @author lethe
 * </pre>
 */
public class MetaUtil {
	
	public static final String RELATION_SEPARATOR_UNDERLINE = "_";
	public static final String RELATION_SEPARATOR_DOT = ".";
	
	public static final String RELATION_SEPARATOR_DEFAULT = RELATION_SEPARATOR_UNDERLINE;
	
	
	public static final int RELATIONCODE_ERROR = 0;
	public static final int RELATIONCODE_RIGHT = 1;
	public static final int RELATIONCODE_RIGHT_ENDWITH_ATTRIBUTE = 2;
	
	/**
	 * �滻��ϵ�ķָ���
	 * example:
	 * ME2ROOM_NAME TO ME2ROOM.NAME 
	 * @param metaClass
	 * @param relationCode
	 * @param targetSeparator
	 * @param sourceSeparator
	 * @return
	 */
	public static String convertRelationSeparator(MetaClass metaClass,String relationCode,
			String targetSeparator,String sourceSeparator){
		StringBuffer ret = new StringBuffer();
		String code = findFirstRelationCode(metaClass,relationCode,sourceSeparator);
		String nextCode = relationCode;
		if(code != null){
			MetaClass currentClass = metaClass;
			while(code != null){
				ret.append(code);
				currentClass = currentClass.findMetaClassRelation(code).getRelationMetaClass();
				nextCode = nextCode.substring(nextCode.indexOf(sourceSeparator)+sourceSeparator.length());
				code = findFirstRelationCode(currentClass,nextCode,sourceSeparator);
				//���ֻ��relation nextcode�п���Ϊ��
				if(nextCode!=null && nextCode.length()>0){
					ret.append(targetSeparator);
				}
			}
			if(nextCode!=null && nextCode.length()>0){
				ret.append(nextCode);
			}
		}else{
			ret.append(relationCode);
		}
		return ret.toString();
	}
	/**
	 * ����relationCode �е�һ��relation
	 * @param metaClass
	 * @param relationCode
	 * @param fromIndex
	 * @return
	 */
	public static String findFirstRelationCode(MetaClass metaClass,String relationCode){
		return findFirstRelationCode(metaClass,relationCode,RELATION_SEPARATOR_DEFAULT);
	}
	/**
	 * ����relationCode �е�һ��relation
	 * @param metaClass
	 * @param relationCode
	 * @param separator
	 * @param fromIndex
	 * @return
	 */
	public static String findFirstRelationCode(MetaClass metaClass,String relationCode,
			String separator){
		return findFirstRelationCode(metaClass,relationCode,separator,0);
	}
	
	/**
	 * �Ӹ��ӵ�relationCode���ҵ�MetaAttribute
	 * @param metaClass
	 * @param relCode
	 * @return
	 */
	public static MetaAttribute findAttribute(MetaClass metaClass,String relCode){
		return findAttribute( metaClass,relCode,RELATION_SEPARATOR_DEFAULT);
	}
	
	/**
	 * �Ӹ��ӵ�relationCode���ҵ�MetaAttribute
	 * @param metaClass
	 * @param relCode
	 * @param relationSeparator
	 * @return
	 */
	public static MetaAttribute findAttribute(MetaClass metaClass,String relCode,String relationSeparator){
		MetaAttribute attribute = null;
		String code = relCode;
		String nextCode = null;
		code = findFirstRelationCode(metaClass,relCode,relationSeparator);
		if(code != null){
			nextCode = relCode.substring(relCode.indexOf(relationSeparator) + relationSeparator.length());
			MetaClassRelation rel = metaClass.findMetaClassRelation(code);		
			attribute = rel.getRelationMetaClass().findMetaAttribute(nextCode);
			if(attribute == null && nextCode.indexOf(relationSeparator)>0){
				attribute = findAttribute(rel.getRelationMetaClass(),nextCode,relationSeparator);
			}
		}
		return attribute;
	}
	
	public static MetaClass findMetaClass(MetaClass metaClass,String relCode){
		return findMetaClass( metaClass,relCode,RELATION_SEPARATOR_DEFAULT);
	}
	
	public static MetaClass findMetaClass(MetaClass metaClass,String relCode,String relationSeparator){
		MetaClass lastMetaClass = null;
		String code = relCode;
		String nextCode = null;
		code = findFirstRelationCode(metaClass,relCode,relationSeparator);
		if(code != null){
			if(relCode.length()>code.length() + relationSeparator.length()){
				nextCode = relCode.substring(code.length() + relationSeparator.length());
			}else{
				nextCode ="";
			}
			MetaClassRelation rel = metaClass.findMetaClassRelation(code);
			if(nextCode.indexOf(relationSeparator)>0){
				lastMetaClass = findMetaClass( rel.getRelationMetaClass(),nextCode,relationSeparator);
				if(lastMetaClass == null){
					lastMetaClass = rel.getRelationMetaClass();
				}
			}else{
				lastMetaClass = rel.getRelationMetaClass();
			}
		}
		return lastMetaClass;
	}
	
	/**
	 * ���ݽ����˵�MetaClass��RelationCode ������ʼ�˵�MetaClass
	 * @param endMetaClass �����˵�MetaClass
	 * @param relCode
	 * @return
	 */
	public static MetaClass findStartMetaClass(MetaClass endMetaClass,String relCode){
		String[] relCodeList = relCode.split(RELATION_SEPARATOR_DEFAULT);
		StringBuffer rightRelCode = new StringBuffer();
		for (int i = relCodeList.length-1; i>=0; i--) {
			rightRelCode.append( relCodeList[i] );
			if(i != 0){
				rightRelCode.append( RELATION_SEPARATOR_DEFAULT );
			}
		}
		return findMetaClass( endMetaClass,rightRelCode.toString(),RELATION_SEPARATOR_DEFAULT);
	}
	
	/**
	 * ���ݸ���relationCode ����AssembleString
	 * ֧�ֲ��Ǵ���relationCode
	 * example��
	 * PHYSICALCONTAINER2ME_ME2ROOM_NAME
	 * @param criterial
	 * @param relCode
	 */
	public static void initAssembleStringByRelationCode(QueryCriteria criterial,String relCode){
		initAssembleStringByRelationCode( criterial, relCode, RELATION_SEPARATOR_DEFAULT);
	}
	
	/**
	 * ���ݸ���relationCode ����AssembleString
	 * @param criterial
	 * @param relCode
	 * @param relationSeparator
	 */
	public static void initAssembleStringByRelationCode(QueryCriteria criterial,String relCode,String relationSeparator){
		MetaClass metaClass = criterial.getMetaClass();
		if(metaClass == null){
			metaClass = criterial.getMetaClassRelation().getRelationMetaClass();
		}
		if(metaClass == null){
			return;
		}
		String assembleString = getAssembleStringByRelationCode(metaClass,relCode,relationSeparator);
		criterial.addAssembleFragment(assembleString);
	}
	
	/**
	 * ���ݸ��ӵ�relationCode �õ� AssembleString
	 * @param metaClass
	 * @param complexRelCode
	 * @return
	 */
	public static String getAssembleStringByRelationCode(MetaClass metaClass,String complexRelCode){
		return getAssembleStringByRelationCode( metaClass, complexRelCode,RELATION_SEPARATOR_DEFAULT);
	}
	/**
	 * ���ݸ��ӵ�relationCode �õ� AssembleString
	 * @param metaClass
	 * @param complexRelCode
	 * @param relationSeparator
	 * @return
	 */
	public static String getAssembleStringByRelationCode(MetaClass metaClass,String complexRelCode,String relationSeparator){
		StringBuffer assembleString = new StringBuffer();
		initAssembleStringByRelationCode(metaClass,complexRelCode,relationSeparator,assembleString);
		return assembleString.toString();
	}
	
	private static void initAssembleStringByRelationCode(MetaClass metaClass,String complexRelCode,
			String relationSeparator,StringBuffer assembleString){
		String code = findFirstRelationCode(metaClass,complexRelCode,relationSeparator);
		String nextCode = null;
		if(code != null){
			assembleString.append("[");
			assembleString.append(code);
			nextCode = complexRelCode.substring(complexRelCode.indexOf(relationSeparator) + relationSeparator.length());
			MetaClass nextMetaClass = metaClass.findMetaClassRelation(code).getRelationMetaClass();
			initAssembleStringByRelationCode(nextMetaClass,nextCode,relationSeparator,assembleString);
			assembleString.append("]");
		}
	}
	
	private static String findFirstRelationCode(MetaClass metaClass,String relationCode,
			String separator,int fromIndex){
//		if(relationCode.indexOf(separator,fromIndex) == -1){
		if(relationCode == null || relationCode.length() == 0){
			return null;
		}
		String code = null;
		if(relationCode.indexOf(separator,fromIndex) == -1){
			code = relationCode;
		}else{
			code = relationCode.substring(0,relationCode.indexOf(separator,fromIndex));
		}
		MetaClassRelation relation = metaClass.findMetaClassRelation(code);
		if(relation != null){
			return code;
		}else{
			if(relationCode.indexOf(separator,fromIndex) != -1){
				return findFirstRelationCode(metaClass,relationCode,separator,relationCode.indexOf(separator,fromIndex)+separator.length());
			}else{
				return null;
			}
		}
	}
	
	
	
	/**
	 * ���relationCode�Ƿ���ȷ
	 * @param metaClass
	 * @param relationCode 0�������relationCode  
	 * 					   1����ȷ��relationCode 
	 * 					   2����ȷ��relationCode ��������ȷ����������β
	 * @return
	 */
	public static int checkRelationCode(MetaClass metaClass,String relationCode){
		return checkRelationCode(metaClass,relationCode,RELATION_SEPARATOR_DEFAULT);
	}
	/**
	 * ���relationCode�Ƿ���ȷ
	 * @param metaClass
	 * @param relationCode 0�������relationCode  
	 * 					   1����ȷ��relationCode 
	 * 					   2����ȷ��relationCode ��������ȷ����������β
	 * @return
	 */
	public static int checkRelationCode(MetaClass metaClass,String relationCode,String relationSeparator){
		int ret = RELATIONCODE_ERROR;
		MetaAttribute attribute = null;
		String code = relationCode;
		String nextCode = null;
		code = findFirstRelationCode(metaClass,relationCode,relationSeparator);
		if(code != null){
			if(relationCode.indexOf(relationSeparator) == -1){
				return RELATIONCODE_RIGHT;
			}
			nextCode = relationCode.substring(relationCode.indexOf(relationSeparator) + relationSeparator.length());
			if(nextCode.length()==0){
				return RELATIONCODE_RIGHT;
			}
			
			MetaClassRelation rel = metaClass.findMetaClassRelation(code);		
			attribute = rel.getRelationMetaClass().findMetaAttribute(nextCode);
			if(attribute == null && nextCode.indexOf(relationSeparator)>0){
				return checkRelationCode(rel.getRelationMetaClass(),nextCode,relationSeparator);
			}else{
				ret = RELATIONCODE_RIGHT_ENDWITH_ATTRIBUTE;
			}
		}
		return ret;
	}

}

