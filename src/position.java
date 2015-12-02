//position.java

public enum position {
	qb("qb"), rb("rb"), wr("wr"), te("te");

	private String positionAbbrev;

	private position(String s) {
		positionAbbrev = s;
	}

	public String getPosition() {
		return positionAbbrev;
	}

}
