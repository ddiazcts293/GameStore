package com.powerrangers.screen;

import java.util.Scanner;

import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class GameCatalogScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext) 
    {
        Scanner scanner = appContext.getScanner();


        System.out.println("Catálogo de juegos");

        Menu menu = appContext.createMenu()
            .AddItem("1", "Regresar al menu anterior")
            .AddItem("2", "Salir");

        switch (menu.show())
        {
            case "1":
                appContext.goToPreviousScreen();
                break;
            case "2":
            System.out.println("Hola");
                break;
            default:
                break;
        }
    }
}
