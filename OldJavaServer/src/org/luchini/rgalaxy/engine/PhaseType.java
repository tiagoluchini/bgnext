package org.luchini.rgalaxy.engine;

public enum PhaseType {
	
	EXPLORE_NORMAL("1A", "Explore (For keeps)"),
	EXPLORE_AGRESSIVE("1B", "Explore (Aggressive)"),
	DEVELOP("2", "Develop"),
	SETTLE("3", "Settle"),
	CONSUME_TRADER("4A", "Consume (Trade)"),
	CONSUME_POINTS("4B", "Consume (2x points)"),
	PRODUCE("5", "Produce"),
	// Reference
	EXPLORE("1", "Explore"),
	TRADE("$", "Trade"),
	CONSUME("4", "Consume"),
	MULTIPLIER("?", "Multiplier");
	
	private String id;
	private String name;
	
	private PhaseType(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
