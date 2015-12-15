import java.util.*;
import java.util.Random;

public class Matchup {

    private static databaseAPI database;

    public static void main(String[] args)
    {
    	database = new databaseAPI();
    	scoreMatchup("Asheq","Ron",1);
    	
    	database.endConnection();
    }

    public static String scoreMatchup(String userName1, String userName2, int week)
    {
    	double score1 = database.scoreStartingLineup(userName1, week);
    	double score2 = database.scoreStartingLineup(userName2, week);

    	String matchupScore = "REPLACE INTO Matchup VALUES(" + week + ",'" + userName1 + "'," + score1 + ",'" + userName2 + "'," + score2 + ");";
    	database.updateMatchup(matchupScore);
    	return matchupScore;
    }

    public static ArrayList<String> pairFantasyTeams(int week)
    {
    	ArrayList<String> fantasyTeams = database.getFantasyTeams();
    	ArrayList<String> matchupInsertion = new ArrayList<String>(5);

    	int teamsLeft = 10;
    	String userName1, userName2;
    	int firstIndex, secondIndex;

    	Random rand = new Random();

    	while (teamsLeft>0)
    	{
    		firstIndex = rand.nextInt(teamsLeft);
    		userName1 = fantasyTeams.remove(firstIndex);
			teamsLeft--;

    		secondIndex = rand.nextInt(teamsLeft);
    		userName2 = fantasyTeams.remove(secondIndex);
    		teamsLeft--;
    		
    		String matchupString = "INSERT INTO Matchup VALUES(" + week + ",'" + userName1 + "'," + 0.0 + ",'" + userName2 + "'," + 0.0 + ");";

    		matchupInsertion.add(matchupString);
    		database.updateMatchup(matchupString);
    	}
    	return matchupInsertion;
    }
}