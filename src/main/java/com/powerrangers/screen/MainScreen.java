package com.powerrangers.screen;
import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class MainScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext) 
    {
        System.out.println("GameStore v1.0");
        
        if (appContext.isLoggedIn())
        {
            Customer loggedInCustomer = appContext.getCurrentCustomer();
            System.out.printf("\n¡Bienvenido, %s!\n", loggedInCustomer.name);
        }
        else
        {
            System.out.println("Bienvenido");
        }

        System.out.println("¿Qué le gustaría hacer?");

        Menu menu = appContext.createMenu();
        menu.AddItem("J", "Explorar el catágolo de juegos");
        
        if (!appContext.isLoggedIn())
        {
            menu.AddItem("I", "Iniciar sesión");
            menu.AddItem("R", "Registrarse");
        }
        else
        {
            menu.AddItem("V", "Carrito de compras");
            menu.AddItem("B", "Biblioteca");
            menu.AddItem("A", "Ajustes de cuenta");
            menu.AddItem("C", "Cerrar sesión");
        }

        menu.AddItem("RR", "Explorar la tendencia en videojuegos");
        menu.AddItem("S", "Salir");

        String chosenOption = menu.show();
        switch (chosenOption) 
        {
            case "J":
                appContext.goToScreen(ScreenOption.GameCatalog);
                break;
            case "B":
                appContext.goToScreen(ScreenOption.GameLibrary);
                break;
            case "I":
                appContext.goToScreen(ScreenOption.Login);
                break;
            case "R":
                appContext.goToScreen(ScreenOption.SignUp);
                break;
            case "C":
                appContext.logout();
                break;
            case "RR":
                appContext.goToScreen(ScreenOption.Recommendation);
                break;
            case "V":
                appContext.goToScreen(ScreenOption.Purchase);
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
