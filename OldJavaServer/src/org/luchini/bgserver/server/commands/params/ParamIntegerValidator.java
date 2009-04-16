package org.luchini.bgserver.server.commands.params;

public class ParamIntegerValidator implements ParamValidator {

	@Override
	public boolean validate(String value) {
		boolean out = true;
		try {
			Integer.parseInt(value);
		} catch (Exception e) {
			out = false;
		}
		return out;
	}

}
