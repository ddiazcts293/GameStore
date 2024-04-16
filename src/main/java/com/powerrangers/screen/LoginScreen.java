/*
 * Archivo: LoginScreen.java
 * Autor: Díaz Cortés Daniel Salomón
 * Grupo: 1A-DSM
 * Descripción: Sección de inicio de sesión.
 */

package com.powerrangers.screen;

import java.util.Scanner;

import com.powerrangers.AppContext;
import com.powerrangers.util.*;

public class LoginScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext) 
    {
        // Toma la instancia previamente inicializada de Scanner
        Scanner scanner = appContext.getScanner();
        // Declara las variables para almacenar los datos ingresados
        String email, password, chosenOption;
        // Declara una variable para indicar si el inicio de sesión fue exitoso
        boolean successfulLogin;

        // Bucle que se ejecutará hasta que se inicie sesión exitosamente o
        // hasta que se elija hacer otra acción si lo anterior falla
        do
        {
            // Solicita las credenciales de acceso
            System.out.println("Iniciar sesión");
            System.out.print("Ingrese su email: ");
            email = scanner.nextLine();
            System.out.print("Ingrese su contraseña: ");
            password = scanner.nextLine();
            
            // Realiza el inicio de sesión y almacena el resultado de la 
            // operación
            successfulLogin = appContext.login(email, password);
            
            // Si el inicio de sesión fue exitoso, muestra un mensaje de 
            // bienvenida y regresa al menú principal
            if (successfulLogin)
            {
                System.out.printf(
                    "Bienvenido %s!\n", 
                    appContext.getCurrentCustomer().username);

                // Termina el bucle actual
                break;
            }
            // Si no lo fue, le pregunta al usuario que acción desea realizar
            else
            {
                // Informa al usuario acerca del suceso
                System.out.println("Error: email o contraseña incorrecta");

                // Crea un menú que ofrece algunas opciones a realizar tras
                // un intento fallido de iniciar sesión
                Menu menu = appContext.createMenu()
                    .AddItem("1", "Volver a intentar")
                    .AddItem("2", "Registrar nueva cuenta")
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
                        appContext.goToScreen(ScreenOption.SignUp);
                        return;
                    case "3":
                        // Navega hacia la pantalla principal
                        appContext.goToScreen(ScreenOption.MainScreen);
                        return;
                    default:
                        break;
                }
            }
        }
        while (!successfulLogin);

        // Se dirige a la pantalla principal
        appContext.goToScreen(ScreenOption.MainScreen);
    }
}
