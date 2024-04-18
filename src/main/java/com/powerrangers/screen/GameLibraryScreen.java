package com.powerrangers.screen;

import com.powerrangers.AppContext;
import com.powerrangers.util.*;

public class GameLibraryScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext)
    {
        System.out.println("Biblioteca de juegos");
            
        Menu menu = appContext.createMenu()
            .AddItem("1", "Regresar a pantalla anterior")
            .AddItem("2", "Ir a menú principal");

        var dbContext = appContext.getDbContext();
        var wishList = dbContext.getCustomerWishList(appContext.getCurrentCustomer());
        for(int i = 0; i < wishList.length; i++){
            var game = wishList[i].game;
            System.out.println(game.name);
        }

        switch (menu.show()) 
        {
            case "1":
                appContext.goToPreviousScreen();
                break;
            case "2":
                appContext.goToScreen(ScreenOption.MainScreen);
                break;
            default:
                break;
        }
    }
}
