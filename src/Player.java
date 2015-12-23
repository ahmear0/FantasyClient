import java.util.ArrayList;

public class Player {
	
	private int playerID;
	private String lastName, firstName, position;
	private teamName team;

	public Player(int _playerID, String _lastName, String _firstName, String _teamName, String _position)
	{
		playerID = _playerID;
		lastName = _lastName;
		firstName = _firstName;
		team = teamName.valueOf(_teamName);
		position = _position;
	}

	public String getFullName()
	{
		return "" + firstName + " " + lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public int getID()
	{
		return playerID;
	}

	public String getPosition()
	{
		return position;
	}

	public teamName getTeamName()
	{
		return team;
	}

	public String toString()
	{
		return "Player <" + playerID + ", " + firstName + " " + lastName + " " + team.getTeamName() + ", " + position + ">";
	}
}