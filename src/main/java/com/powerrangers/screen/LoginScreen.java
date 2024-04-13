package com.powerrangers.screen;

import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class LoginScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext) 
    {
        System.out.println("Iniciar sesión");

        Menu menu = appContext.createMenu()
            .AddItem("1", "Registrarse")
            .AddItem("2", "Regresar al menu anterior")
            .AddItem("3", "Ir al menú principal");

        switch (menu.show())
        {
            case "1":
                appContext.goToScreen(ScreenOption.SignUp);
                break;
            case "2":
                appContext.goToPreviousScreen();
                break;
            case "3":
                appContext.goToScreen(ScreenOption.MainScreen);
                break;
            default:
                break;
        }
    }
}
