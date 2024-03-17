package com.powerrangers;

import com.powerrangers.db.*;
import com.powerrangers.db.types.*;;

public class Main 
{
    public static void main(String[] args) 
    {
        DbContext.populateLists();

        DbContext context = new DbContext();
        var games = context.getGames();

        System.out.println("Available games: ");
        
        for (Game game : games) 
        {
            System.out.println(game.name + " by " + game.developer);    
        }
    }
}
