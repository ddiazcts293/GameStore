package com.powerrangers.screen;

import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class MainScreen implements ScreenBase
{
    @Override
    public void run(AppContext appContext) 
    {
        System.out.println("Pantalla principal");
        System.out.println("Juegos disponibles: ");

        Game[] games = appContext.getDbContext().getGames();

        for (Game game : games)
        {
            System.out.println(game.name + " by " + game.developer);    
        }

        System.out.println("Que desea hacer?");

        Menu menu = appContext.createMenu()
            .AddItem("1", "Biblioteca de juegos")
            .AddItem("2", "Lista de deseos")
            .AddItem("3", "Salir");

        String chosenOption = menu.show();

        switch (chosenOption) 
        {
            case "1":
                appContext.goToScreen(ScreenOption.GameLibrary);
                break;
            default:
                System.out.println(chosenOption);
                break;
        }
    }
}
