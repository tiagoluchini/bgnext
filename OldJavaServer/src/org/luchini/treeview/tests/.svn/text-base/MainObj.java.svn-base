package org.luchini.treeview.tests;

import java.util.List;

import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class MainObj {

	@TreeAttribute
	private String id;
	@TreeNode
	private String name;
	@TreeNode
	private SecObj secObj;
	@TreeNode
	private List<SecObj> secondos;
	
	public MainObj() {}
	
	public MainObj(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public SecObj getSecObj() {
		return secObj;
	}

	public void setSecObj(SecObj secObj) {
		this.secObj = secObj;
	}

	public List<SecObj> getSecondos() {
		return secondos;
	}

	public void setSecondos(List<SecObj> secondos) {
		this.secondos = secondos;
	}
	
}
