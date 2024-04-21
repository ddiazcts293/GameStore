/**
 * Archivo: SignUpScreen.java
 * Autor: Diaz Cortes Daniel Salomon
 * Grupo: 1A-DSM
 */

package com.powerrangers.screen;

import java.util.Scanner;

import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class SignUpScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext) 
    {
        Scanner scanner = appContext.getScanner();
        Customer newCustomer = new Customer();
        boolean successfulSignUp = false;
        String chosenOption;

        do {
            System.out.println("Registro de nueva cuenta");
            
            // Solicita la información del cliente
            System.out.print("Ingrese su nombre completo: ");
            newCustomer.name = scanner.nextLine();
            System.out.print("Ingrese un nombre de usuario: ");
            newCustomer.username = scanner.nextLine();
            System.out.print("Ingrese su email: ");
            newCustomer.credentials.email = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            newCustomer.credentials.password = scanner.nextLine();

            successfulSignUp = appContext.signup(newCustomer);
            if (!successfulSignUp)
            {
                // Informa al usuario acerca del suceso
                System.out.println("Error: no se pudo registrar su cuenta");

                // Crea un menú que ofrece algunas opciones a realizar tras
                // un intento fallido de iniciar sesión
                Menu menu = appContext.createMenu()
                    .AddItem("1", "Volver a intentar")
                    .AddItem("2", "Iniciar sesión")
                    .AddItem("3", "Ir al menú principal");

                // Muestra el menú y espera hasta que se elija una opción
                chosenOption = menu.show("Elija una opción");
                // Procesa la opción elegida
                switch (chosenOption)
                {
                    case "1":
                        // Vuelve al comienzo del bucle para solicitar que se
                        // ingresen nuevamente las credenciales de acceso
                        continue;
                    case "2":
                        // Navega hacia la sección de registro de cliente
                        appContext.goToScreen(ScreenOption.Login);
                        return;
                    case "3":
                        // Navega hacia la pantalla principal
                        appContext.goToScreen(ScreenOption.MainScreen);
                        return;
                    default:
                        break;
                }
            }
        } while (!successfulSignUp);

        appContext.goToPreviousScreen();
    }
}
