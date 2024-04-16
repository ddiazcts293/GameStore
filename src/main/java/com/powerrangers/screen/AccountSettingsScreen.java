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
        System.out.println("Configuración de cuenta");
        DbContext dbContext = appContext.getDbContext();
        Scanner scanner = appContext.getScanner();

        Customer customer = appContext.getCurrentCustomer();
        String option;

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
                    dbContext.updateCustomer(customer);
                    System.out.println("Correo cambiado exitosamente");
                    break;
                case "4":
                    System.out.println("Por favor ingrese su nueva contraseña");
                    customer.credentials.password = scanner.nextLine();
                    dbContext.updateCustomer(customer);
                    System.out.println("Contraseña cambiada exitosamente");
                    break;
                case "5":
                    System.out.println("Cuenta eliminada");
                    appContext.deleteCustomerAccount();
                    appContext.goToScreen(ScreenOption.MainScreen);
                    break;
                default:
                    break;
                }
        }
        while (option.compareTo("6") != 0);

        appContext.goToScreen(ScreenOption.MainScreen);
    }
}
