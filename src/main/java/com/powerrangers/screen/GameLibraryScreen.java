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
