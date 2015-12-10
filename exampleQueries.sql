

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



