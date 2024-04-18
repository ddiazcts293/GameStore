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
        //

        System.out.println("Juegos disponibles: ");
        Game[] games = appContext.getDbContext().getGames();
        
        for (Game game : games)
        {
            System.out.println(game.name + " by " + game.developer);    
        }

        System.out.println("Que desea hacer?");

        Menu menu = appContext.createMenu();
        menu.AddItem("C", "Explorar el catágolo de juegos");
        
        if (!appContext.isLoggedIn())
        {
            menu.AddItem("I", "Iniciar sesión");
            menu.AddItem("R", "Registrarse");
        }
        else
        {
            menu.AddItem("A", "Ajustes de cuenta");
        }

        menu.AddItem("RR", "Explorar la tendencia en videojuegos");

        menu.AddItem("S", "Salir");

        String chosenOption = menu.show();
        switch (chosenOption) 
        {
            case "C":
                appContext.goToScreen(ScreenOption.GameCatalog);
                break;
            case "I":
                appContext.goToScreen(ScreenOption.Login);
                break;
            case "R":
                appContext.goToScreen(ScreenOption.SignUp);
                break;
            case "RR":
                appContext.goToScreen(ScreenOption.Recommendation);
                break;
            case "A":
                appContext.goToScreen(ScreenOption.AccountSettings);
                break;
            case "S":
                appContext.exit();
                break;
            default:
                break;
        }
    }
}
