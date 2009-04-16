package org.luchini.bgserver.server.commands.params;

public class Param {

	private boolean mandatory = false;
	private int truncateAt = 0;
	private boolean treatBlankAsNull = false;
	private ParamValidator paramValidator = null;
	private String value;
	
	public Param() {}
	
	public Param(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public Param(boolean mandatory, ParamValidator paramValidator) {
		this.mandatory = mandatory;
		this.paramValidator = paramValidator;
	}
	
	public Param(boolean mandatory, int truncateAt, boolean treatBlankAsNull,
			ParamValidator paramValidator) {
		super();
		this.mandatory = mandatory;
		this.truncateAt = truncateAt;
		this.treatBlankAsNull = treatBlankAsNull;
		this.paramValidator = paramValidator;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public int getTruncateAt() {
		return truncateAt;
	}
	public void setTruncateAt(int truncateAt) {
		this.truncateAt = truncateAt;
	}
	public boolean isTreatBlankAsNull() {
		return treatBlankAsNull;
	}
	public void setTreatBlankAsNull(boolean treatBlankAsNull) {
		this.treatBlankAsNull = treatBlankAsNull;
	}
	public ParamValidator getParamValidator() {
		return this.paramValidator;
	}
	public void setParamValidator(ParamValidator paramValidator) {
		this.paramValidator = paramValidator;
	}
	
	public boolean setValue(String value) {
		boolean out = true;
		if (value.length() == 0)
			value = null;
		if (this.mandatory && value == null)
			out = false;
		else if (this.paramValidator != null)
			out = this.paramValidator.validate(value);
		if (!this.treatBlankAsNull)
			value = "";
		if (out) 
			this.value = value;			
		return out;
	}
	public String getValue() {
		return this.value;
	}
}
