package com.powerrangers.screen;

import com.powerrangers.AppContext;
import com.powerrangers.util.*;

public class AccountSettingsScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext)
    {
        System.out.println("Configuración de cuenta");

        Menu menu = appContext.createMenu()
            .AddItem("1", "Regresar a pantalla anterior")
            .AddItem("2", "Ir a menú principal")
            .AddItem("3", "Salir");

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
