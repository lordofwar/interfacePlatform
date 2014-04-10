/**********************************************************************
 *
 * $RCSfile: QueryAssemble.java,v $  $Revision: 1.1 $  $Date: 2009/09/09 09:19:48 $
 *
 * $Log: QueryAssemble.java,v $
 * Revision 1.1  2009/09/09 09:19:48  liuding
 * *** empty log message ***
 *
 *
 *
 *********************************************************************/
/*
 * Copyright 2008 gxlu, Inc. All rights reserved.
 * File name : QueryAssemble.java
 * Created on : Sep 9, 2009 9:51:54 AM
 * Creator : lethe
 */
package com.gxlu.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Description : TODO
 * author : lethe
 * </pre>
 */
public class QueryAssemble {
	
	private String assembleString;
	/**最外层 currentRelationCode is null**/
	private String currentRelationCode;
	private List<QueryAssemble> childs = new ArrayList<QueryAssemble>();

	public QueryAssemble(String assembleString) {
		this.assembleString = assembleString;
		initQueryAssemble();
	}
	
	public boolean hasChilds(){
		return childs.size()==0?false:true;
	}

	private void initQueryAssemble() {
		if (assembleString == null || assembleString.length() == 0) {
			return;
		}
		String subAssembleString = assembleString;
		if (!assembleString.startsWith("[")
				&& assembleString.indexOf("[") != -1) {
			currentRelationCode = assembleString.substring(0, assembleString
					.indexOf("["));
			subAssembleString = assembleString.substring(currentRelationCode.length());
		} else if (assembleString.indexOf("[") == -1) {
			currentRelationCode = assembleString;
			return;
		}
		int cursor = 0;
		int end = cursor;
		char ch;
		int left = 0;
		int right = 0;
		while (subAssembleString.length() != cursor) {
			for (int i = 0; i < subAssembleString.length() - cursor; i++) {
				if ((ch = subAssembleString.charAt(i + cursor)) == '[')
					left++;
				if (ch == ']')
					right++;
				if (left == right) {
					end = i + cursor;
					break;
				}
			}
			int temp = cursor;
			cursor = end + 1;
			String oneChild = subAssembleString.substring(temp + 1, end);
			QueryAssemble assembleChild = new QueryAssemble(oneChild);
			childs.add(assembleChild);
		}
	}

	/**
	 * 追加assemble 支持重复
	 * @param assembleFragment
	 */
	protected void addAssembleFragment(String assembleFragment){
		QueryAssemble queryAssemble = new QueryAssemble(assembleFragment);
		addAssembleFragment(this,queryAssemble);
		reBuildAssembleString();
	}
	
	private void addAssembleFragment(QueryAssemble all,QueryAssemble fragment){
		List<QueryAssemble> fragmentChilds = fragment.childs;
		List<QueryAssemble> allChilds = all.childs;
		for (QueryAssemble subFragQueryAssemble : fragmentChilds) {
			QueryAssemble subAllQueryAssembleChild = null;
			for (QueryAssemble subAllQueryAssemble : allChilds) {
				if(subAllQueryAssemble.currentRelationCode.equals(subFragQueryAssemble.currentRelationCode)){
					subAllQueryAssembleChild = subAllQueryAssemble;
					break;
				}
			}
			if( subAllQueryAssembleChild == null){
				all.childs.add(subFragQueryAssemble);
			}else{
				addAssembleFragment(subAllQueryAssembleChild,subFragQueryAssemble);
			}
		}
	}
	/**
	 * 只reBuild最外层 里层不会Rebuild
	 */
	private void reBuildAssembleString(){
		StringBuffer newAssembleString = new StringBuffer();
		for (QueryAssemble child : childs) {
			reBuildAssembleString(child,newAssembleString);
		}
		assembleString = newAssembleString.toString();
		
	}
	private void reBuildAssembleString(QueryAssemble queryAssemble,StringBuffer newAssembleString){
		newAssembleString.append("[");
		newAssembleString.append(queryAssemble.currentRelationCode);
		if(queryAssemble.hasChilds()){
			for (QueryAssemble child : queryAssemble.childs) {
				reBuildAssembleString(child,newAssembleString);
			}
		}
		newAssembleString.append("]");
	}
	
	@Override
	public String toString() {
		return assembleString;
	}

	protected String getAssembleString() {
		return assembleString;
	}

	public String getCurrentRelationCode() {
		return currentRelationCode;
	}

	public List<QueryAssemble> getChilds() {
		return childs;
	}
}
