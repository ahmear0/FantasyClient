public enum teamName {

	CIN("CIN"),
	PIT("PIT"),
	CLE("CLE"),
	BAL("BAL"),

	BUF("BUF"),
	NWE("NWE"),
	NYJ("NYJ"),
	MIA("MIA"),

	JAX("JAX"),
	TEN("TEN"),
	HOU("HOU"),
	IND("IND"),

	KAN("KAN"),
	DEN("DEN"),
	OAK("OAK"),
	SDG("SDG"),

	DET("DET"),
	GNB("GNB"),
	CHI("CHI"),
	MIN("MIN"),

	WAS("WAS"),
	PHI("PHI"),
	NYG("NYG"),
	DAL("DAL"),

	CAR("CAR"),
	NOR("NOR"),
	TAM("TAM"),
	ATL("ATL"),

	SEA("SEA"),
	ARI("ARI"),
	SFO("SFO"),
	STL("STL");

	String team;

	teamName(String _name){
		team = _name;
	}

	String getTeamName()
	{
		return team;
	}
}