package com.powerrangers.screen;

import java.util.Scanner;

import com.powerrangers.AppContext;
import com.powerrangers.db.DbContext;
import com.powerrangers.db.types.Customer;
import com.powerrangers.util.*;

public class AccountSettingsScreen implements ScreenBase {
    @Override
    public void show(AppContext appContext)
    {

        //Se muestra el menú principal para la configuración de cuenta.
        //La función DbContext corresponde a la base de datos creada en el programa.
        //appContext corresponde al objeto que contiene todos los componentes utilizados en el programa. 
        System.out.println("Configuración de cuenta");
        DbContext dbContext = appContext.getDbContext();
        Scanner scanner = appContext.getScanner();

        //Customer es el objeto con el cual se trabajarán los datos del usuario.
        Customer customer = appContext.getCurrentCustomer();
        String option;

        //Se agrega un ciclo 'do' para que después de modificar un dato se dé la opción de continuar modificando si así lo desea.
        do
        {
            System.out.print("Seleccione la opción a modificar en su cuenta: ");
            
            Menu menu = appContext.createMenu()
            .AddItem("1", "Nombre")
            .AddItem("2", "Username")
            .AddItem("3", "Correo")
            .AddItem("4", "Contraseña")
            .AddItem("5", "Eliminar cuenta")
            .AddItem("6", "Salir del menú");
            
            option = menu.show();


            //El switch case lleva al usuario a la opción que seleccionó.
            //Aquí vemos las funciones con las que se trabaja el objeto 'Customer'.
            switch (option) 
            {
                case "1":
                    System.out.println("Por favor teclee su nuevo nombre");
                    customer.name = scanner.nextLine();
                    dbContext.updateCustomer(customer);
                    System.out.println("Nombre cambiado exitosamente");
                    break;
                case "2":
                    System.out.println("Por favor ingrese su nuevo Username");
                    customer.username = scanner.nextLine();
                    dbContext.updateCustomer(customer);
                    System.out.println("Username cambiado exitosamente");
                    break;
                case "3":
                    System.out.println("Por favor ingrese su nuevo Correo");
                    customer.credentials.email = scanner.nextLine();
                    dbContext.updateCustomerCredentials(customer.credentials);
                    System.out.println("Correo cambiado exitosamente");
                    break;
                case "4":
                    System.out.println("Por favor ingrese su nueva contraseña");
                    customer.credentials.password = scanner.nextLine();
                    dbContext.updateCustomerCredentials(customer.credentials);
                    System.out.println("Contraseña cambiada exitosamente");
                    break;
                case "5":
                    System.out.println("Cuenta eliminada");
                    appContext.deleteCustomerAccount();
                    appContext.goToScreen(ScreenOption.MainScreen);
                    return;
                default:
                    break;
                }
        }
        while (option.compareTo("6") != 0);

        appContext.goToScreen(ScreenOption.MainScreen);
    }
}
