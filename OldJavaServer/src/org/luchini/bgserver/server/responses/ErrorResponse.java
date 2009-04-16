package org.luchini.bgserver.server.responses;

import org.luchini.treeview.annotations.TreeAttribute;

public class ErrorResponse extends AbstractResponse {

	public static final long UNKNOWN_CODE = 100; 
	public static final String UNKNOWN_MSG = "Unknown command";
	public static final long WRONG_PARAMS_CODE = 101;
	public static final String WRONG_PARAMS_MSG = "Wrong params";
	public static final long SERVER_ERROR_CODE = 102;
	public static final String SERVER_ERROR_MSG = "Server error";
	
	public static final long INVALID_GAMEENGINE_CODE = 200;
	public static final String INVALID_GAMEENGINE_MSG = "Invalid GameEngine uniqueName";
	public static final long INVALID_ROOM_CODE = 201;
	public static final String INVALID_ROOM_MSG = "Invalid Room uniqueID";
	
	@TreeAttribute private String sourceRef;
	@TreeAttribute private long errorCode;
	
	private String errorMessage;
	
	
	public ErrorResponse(String sourceRef, long errorCode, String errorMessage) {
		super(sourceRef);
		this.sourceRef = sourceRef;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String getSourceRef() {
		return this.sourceRef;
	}

	@Override
	public String toString() {
		return this.errorMessage;
	}
	
	public long getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}

}
