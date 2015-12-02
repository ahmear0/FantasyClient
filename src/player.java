//player.java

import java.io.*;

public class player
{

    public String FirstName;
    public String LastName;
    

    public position _position;
    public position newPos;

    public player(String first, String last, position pos)
    {
	FirstName = first;
	LastName = last;
	_position = pos;
	newPos = position.wr;
    }
    
    @Override
    public String toString()
    {
	return "Player: " + FirstName + " " + LastName + ".\n";
    }
	
    public position getPosition()
    {
	return _position;
    }
    
}
