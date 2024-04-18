/*	   Alumno: Roberto Carlos Farías Bahena 

	   Carrera: Desarrollo de Software Multiplataforma 

	   Grupo: A-1 

	   Contribución: Proyecto Tienda de Videojuegos (Apartado del Catálogo) 
 * 
 */


package com.powerrangers.screen;

import java.util.Scanner;

import com.powerrangers.AppContext;           // Se inicializan las librerías...
import com.powerrangers.db.DbContext;
import com.powerrangers.db.types.*;
import com.powerrangers.util.*;

public class GameCatalogScreen implements ScreenBase
{
    @Override
    public void show(AppContext appContext)                          //Se mandan a llamar las clases, variables y arreglos
    {                                                                //que necesitaremos para el apartado del catálogo...
        Scanner scanner = appContext.getScanner();
        DbContext dbContext = appContext.getDbContext();
        GameCategory[] categories = dbContext.getGameCategories();
        String MenuSelection;

        do {                                                         // Iniciamos un ciclo do-while para que el menú se repita
            System.out.println("Catálogo de juegos");                // al menos que el usuario teclee una de las opciones de salida...
            Menu menu = appContext.createMenu();
            for (int i = 0; i < categories.length; i++) 
            {
                menu.AddItem("G" + Integer.toString(i), "Juegos de " + categories[i].title);
            }

            menu.AddItem("R", "Regresar al menu anterior"); //Código que generamos a partir de los arreglos que obtuvimos
            menu.AddItem("S", "Salir");                     //de la base de datos. Generamos el primer menú en el cual
                                                                     //se muestran las categorías de los juegos.
            MenuSelection = menu.show();

            if (MenuSelection.compareTo("R") == 0)                   //Condiciones para cuando el usuario teclee una de las opciones
            {                                                        //para regresar o salir.
                appContext.goToPreviousScreen();
                return;
            }
            else if (MenuSelection.compareTo("S") == 0)
            {
                appContext.exit();
            }
            else
            {
                int categoryIndex = Integer.parseInt(MenuSelection.substring(1)); // Convertimos la variable tipo string a entera
                GameCategory selectedCategory = categories[categoryIndex];        // para operar...

                Game[] games = dbContext.getGames(selectedCategory);              // Código para obtener los juegos de la categoría
                                                                                  // seleccionada...
                menu.clear();
                for (int i = 0; i < games.length; i++) 
                {
                    menu.AddItem(Integer.toString(i), games[i].name);            // Con este ciclo for se agregan los elementos
                }                                                                // del menú, los cuales son los juegos de la
                                                                                 // categoría seleccionada, uno por uno...
                menu.AddItem("R", "Regresar");
                MenuSelection = menu.show();

                if (MenuSelection.compareTo("R") == 0)
                {
                    appContext.goToPreviousScreen();
                }
                


                int gameIndex = Integer.parseInt(MenuSelection);  // Después de obtener el juego, se le muestra la información
                Game selectedGame = games[gameIndex];             // al usuario


                System.out.println("Juego seleccionado: " + selectedGame.name);
                System.out.println("Descripción: " + selectedGame.description);
                System.out.println("Costo: $" + selectedGame.price);
                System.out.println("Desarrollador: " + selectedGame.developer);
                System.out.println("Año de lanzamiento: " + selectedGame.year);
                System.out.println("¿Qué desea hacer?");
                menu.clear();                                                     //Se limpia el menú para generar uno nuevo
                menu.AddItem("1", "Agregar a la lista de deseos.")       //en el que se le invita al usuario
                .AddItem("2", "Agregar al carrito.")                     //a comprar el juego, agregarlo a la lista de deseos
                .AddItem("R", "Regresar a consultar otros juegos.");     // o volver...
                MenuSelection = menu.show();

                switch (MenuSelection) {
                    case "1":
                    dbContext.addGameToWishList(appContext.getCurrentCustomer(), selectedGame);
                    System.out.println("Artículo agregado a la lista de deseos. Regresando al menú anterior...");
                    break;

                    case "2":
                    dbContext.addGameToShoppingCar(appContext.getCurrentCustomer(), selectedGame);
                    System.out.println("Artículo agregado al carrito. Regresando al menú anterior...");
                    break;

                    case "R":
                    System.out.println("Regresando al menú anterior...");
                    break;

                    default:
                    break;
                }

            }
        } while (true);
    }
}
