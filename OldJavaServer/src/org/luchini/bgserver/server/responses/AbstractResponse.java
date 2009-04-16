package org.luchini.bgserver.server.responses;

import org.luchini.treeview.annotations.TreeNode;

@TreeNode(fixedAttributes={"type"},
		fixedAttributesValues={"response"})
public abstract class AbstractResponse {

	public AbstractResponse(String sourceRef) {}
	
	public abstract String getSourceRef();
	
}
