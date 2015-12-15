/* List the week 1 leader in Passing Yards */
SELECT lastName, firstName, passYds
From Player Natural Join playerScores 
WHERE passYds IS NOT NULL
AND week=1
ORDER BY passYds DESC
LIMIT 1;

/* List the winning fantasy teams from week 1 */
SELECT userName1 as winner, score1 as winScore
FROM Matchup
WHERE week=1 AND score1>score2 
UNION 
(SELECT userName2, score2
FROM Matchup
WHERE week=1 AND score2>score1)


/* Which professional teams do Ron's players belong to?
How many belong to each professional team? */
SELECT teamName, COUNT(teamName)
FROM (FantasyTeam NATURAL JOIN Rostered NATURAL JOIN Player)
WHERE userName='Ron'
GROUP BY teamName
ORDER BY count(teamName) DESC;


/* Find Ron's Opponent's Roster for Week 1 */
SELECT playerID, position, lastName, firstName
FROM (Player P NATURAL JOIN Rostered R), FantasyTeam Opponent, Matchup M
WHERE M.userName1 = 'Ron' AND
  	M.userName2 = Opponent.userName AND
  	Opponent.userName = R.userName AND
  	M.week = 1
UNION
(SELECT playerID, position, lastName, firstName
FROM (Player P NATURAL JOIN Rostered R), FantasyTeam Opponent, Matchup M
WHERE M.userName2 = 'Ron' AND
  	M.userName1 = Opponent.userName AND
  	Opponent.userName = R.userName AND
  	M.week = 1);


/* Find Ron's Opponent's Starting Roster for Week 1 */
SELECT playerID, position, lastName, firstName
FROM (Player P NATURAL JOIN Rostered R), FantasyTeam Opponent, Matchup M
WHERE M.userName1 = 'Ron' AND
  	M.userName2 = Opponent.userName AND
  	Opponent.userName = R.userName AND
  	M.week = 1 AND start=1
UNION
(SELECT playerID, position, lastName, firstName
FROM (Player P NATURAL JOIN Rostered R), FantasyTeam Opponent, Matchup M
WHERE M.userName2 = 'Ron' AND
  	M.userName1 = Opponent.userName AND
  	Opponent.userName = R.userName AND
  	M.week = 1 AND start=1);

/* List all wide recievers that play for the New England Patriots */
SELECT playerID, firstName, lastName 
FROM Player
WHERE position='WR' AND teamName='NWE';


/* List the top-10 players with the most rushing yards */
SELECT lastName, firstName, teamName, sum(rushYds) 
FROM playerScores Natural Join Player 
WHERE position='RB' 
GROUP BY playerID 
ORDER BY sum(rushYds) 
DESC limit 10;

/* List all teams (the week, their total recieving and passing yards) that have all recieving yards accounted for 
by passing yards in the sql database
for week 5 */
SELECT teamName, week, recieving, passing 
FROM
	(SELECT teamName, week, sum(recYds) as recieving, sum(passYds) as passing
	FROM playerScores Natural Join Player 
	WHERE week=5
	GROUP BY teamName) AS passCatch
WHERE passCatch.recieving=passCatch.passing;


/* List the current scores for Ron's week 8 starting lineup */
SELECT rosterSpot, firstName, lastName, passYds, passTD, rushYds, rushTD, recYds, recTD, fLoss 
FROM Rostered Natural Join playerScores Natural Join Player
WHERE week=8
AND userName='Ron'
AND start=1;


/* Remove all prior quarterback scores so that I can demonstrate the scrapeAPI in action */
DELETE ps 
FROM playerScores as ps Natural Join Player as p 
WHERE ps.playerID=p.playerID AND p.position='QB';





