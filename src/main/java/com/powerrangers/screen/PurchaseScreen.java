package com.powerrangers.screen;

//import java.util.Scanner;
import com.powerrangers.AppContext;
import com.powerrangers.db.types.*;

public class PurchaseScreen implements ScreenBase 
{
    @Override
    public void show(AppContext appContext)
    {
        System.out.println("---Apartado de compra---");

        var dbContext = appContext.getDbContext();
        var scanner = appContext.getScanner();
        var shoppingCar = dbContext.getCustomerShoppingCar(appContext.getCurrentCustomer());
        float total = 0;

        System.out.println("Lista de juegos seleccionados: ");

        for (int i = 0; i < shoppingCar.length; i++) {
            var gameInShoppingCar = shoppingCar[i];

            System.out.println("Juego: " + gameInShoppingCar.game.name);
            System.out.println("Precio: $" + gameInShoppingCar.game.price);

            total += gameInShoppingCar.game.price;
        }

        //Precio total
        System.out.println("Monto total: ");
        System.out.println("$" + total);

        System.out.println("");

        //Método de pago
        System.out.println("Selecciona el Método de Pago");
        System.out.println("(1)Tarjeta de Crédito/Debito");
        System.out.println("(2)Fondos");
        int opc = scanner.nextInt();
        scanner.nextLine();
        String banco = "";
        
        int digitos = 0;
        int cvv = 0;
        String nombre = "";

        switch (opc) {
            case 1:
                System.out.println("Banco asociado a la tarjeta: ");
                banco = scanner.nextLine();
                System.out.println("Ingrese los números de la tarjeta: ");
                digitos = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Ingrese el CVV (Se encuentra detrás de la tarjeta): ");
                cvv = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Nombre completo del usuario: ");
                nombre = scanner.nextLine();
                break;
            case 2:

                break;
            default:
                break;
        }

        //Aplicar descuento 
        System.out.println("Procesando...");
        try 
        {
            Thread.sleep(1500);
        } 
        catch (Exception e) 
        { }
        
        System.out.println("Quisiera agregar algún descuento?");
        System.out.println("(1)Si / (2)No");
        int descuento = scanner.nextInt();
        scanner.nextLine();

        double totaldescuento = 0;
        
        switch (descuento) {
            case 1:
                totaldescuento = total * .75;
                System.out.println("El descuento que te tocó es de... 25%");
                System.out.println("El monto total era: "+total);
                System.out.println("El monto con descuento es: "+totaldescuento);
                break;
            case 2:
                break;
        
            default:
                break;
        }

        //Mostrar el contenido de la compra y datos del usuario
        System.out.println("Nombre del usuario: "+nombre);
        System.out.println("Tu tarjeta a utilizar es: "+banco+" con los digitos "+digitos);
        System.out.println("Correo : " +appContext.getCurrentCustomer().credentials.email);
        System.out.println("Monto Total : $"+totaldescuento);
        System.out.println("Escriba '1' para confirmar compra / Escriba '2' para cancelar");
        int confirmar = scanner.nextInt();
        scanner.nextLine();

        switch (confirmar) {
            case 1:
            for (GameInShoppingCar gameInShoppingCar : shoppingCar) {
                dbContext.buyGame(
                    gameInShoppingCar.customer, 
                    gameInShoppingCar.game);
                }
                System.out.println("¡Compra realizada!");
                
                break;
            case 2:
                appContext.goToScreen(ScreenOption.MainScreen);
                break;
        
            default:
                break;
        }

        appContext.getScanner().nextLine(); //Detener el menu
        

    } //No borrar
} //No borrar 
