package com.powerrangers.screen;

import java.util.Scanner;

import com.powerrangers.AppContext;
import com.powerrangers.db.DbContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class GameLibraryScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext)
    {
        System.out.println("Biblioteca de juegos");
        
        Customer currentCustomer = appContext.getCurrentCustomer();
        DbContext dbContext = appContext.getDbContext();
        Scanner scanner = appContext.getScanner();

        Menu menu = appContext.createMenu()
            .AddItem("W", "Ver lista de deseos")
            .AddItem("C", "Ver juegos comprados");
        
        String chosenOption = menu.show();
        switch (chosenOption) {
            case "W":
            {
                System.out.println("Juegos en la lista de deseos");

                var wishlist = dbContext.getCustomerWishList(currentCustomer);
                for (GameInWishList gameInWishList : wishlist)
                {
                    System.out.println(gameInWishList.game.name);
                }    
                break;
            }            
            case "C":
            {
                System.out.println("Juegos comprados");

                var purchasedGameList = dbContext.getPurchasedGames(currentCustomer);
                for (var purchasedGame : purchasedGameList)
                {
                    System.out.println(purchasedGame.game.name);
                }    
                break;
            }

            default:
                break;
        }

        System.out.print("Presione ENTER para regresar al menú principal...");
        scanner.nextLine();
    }
}
