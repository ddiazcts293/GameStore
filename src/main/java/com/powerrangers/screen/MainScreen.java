package com.powerrangers.screen;

import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class MainScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext) 
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
            .AddItem("1", "Explorar el catágolo de juegos")
            .AddItem("2", "Iniciar sesión")
            .AddItem("3", "Registrarse")
            .AddItem("4", "Salir");

        String chosenOption = menu.show();

        switch (chosenOption) 
        {
            case "1":
                appContext.goToScreen(ScreenOption.GameCatalog);
                break;
            case "2":
                appContext.goToScreen(ScreenOption.Login);
                break;
            case "3":
                appContext.goToScreen(ScreenOption.SignUp);
                break;
            case "4":
                appContext.exit();
                break;
            default:
                break;
        }
    }
}
