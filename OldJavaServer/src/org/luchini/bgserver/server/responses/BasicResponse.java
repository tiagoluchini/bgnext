package org.luchini.bgserver.server.responses;

import org.luchini.treeview.annotations.TreeAttribute;

public class BasicResponse extends AbstractResponse {

	public static final String OK = "OK";
	public static final String BYE = "TCHAU TCHAU!";
	
	@TreeAttribute
	private String sourceRef;
	
	private String response;
	
	public BasicResponse(String sourceRef) {
		super(sourceRef);
		this.sourceRef = sourceRef;
	}
	
	public BasicResponse(String sourceRef, String response) {
		super(sourceRef);
		this.sourceRef = sourceRef;
		this.response = response;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return this.response;
	}
	
}
