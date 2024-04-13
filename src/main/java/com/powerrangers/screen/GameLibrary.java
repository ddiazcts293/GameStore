package com.powerrangers.screen;

import com.powerrangers.AppContext;
import com.powerrangers.util.*;

public class GameLibrary implements ScreenBase
{
    @Override
    public void run(AppContext appContext)
    {
        System.out.println("Biblioteca de juegos");

        Menu menu = appContext.createMenu()
            .AddItem("1", "Regresar")
            .AddItem("2", "Volver");

        switch (menu.show()) {
            case "1":
                appContext.goToPreviousScreen();
                break;
            default:
                break;
        }
    }
}
