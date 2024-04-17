/*
 * Archivo: DbContext.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que representa un enlace entre el programa y la base de
 * datos.
 */

package com.powerrangers.db;

import java.time.LocalDate;
import java.util.ArrayList;
import com.powerrangers.db.types.*;

public class DbContext 
{
    // Define las listas de catalogo y de usuarios registrados
    // Por ahora se limitará a almacenar los datos en la memoria estática
    private ArrayList<GameCategory> categoryList = new ArrayList<>();
    private ArrayList<Game> gameList = new ArrayList<>();
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<GameInShoppingCar> shoppingCars = new ArrayList<>();
    private ArrayList<GameInWishList> wishLists = new ArrayList<>();
    private ArrayList<PurchasedGame> purchasedGames = new ArrayList<>();

    private boolean listsPopulated = false;

    //#region Catálogo de juegos

    // Devuelve un arreglo de todas las categorias de juegos.
    public GameCategory[] getGameCategories()
    {
        GameCategory[] categoryArray = new GameCategory[categoryList.size()];
        categoryList.toArray(categoryArray);
        
        return categoryArray;
    }
    
    // Devuelve un arreglo de todos los juegos disponibles.
    public Game[] getGames()
    {
        Game[] gameArray = new Game[gameList.size()];
        gameList.toArray(gameArray);

        return gameArray;
    }

    // Devuelve un arreglo de los juegos disponibles de una categoria dada.
    public Game[] getGames(GameCategory categoryToFilter)
    {
        ArrayList<Game> filteredGameList = new ArrayList<>();
        Game[] filteredGameArray;

        for (Game game : gameList) 
        {
            for (GameCategory category : game.categories) 
            {
                if (category.id == categoryToFilter.id)
                {
                    filteredGameList.add(game);
                    break;
                }
            }
        }

        filteredGameArray = new Game[filteredGameList.size()];
        filteredGameList.toArray(filteredGameArray);

        return filteredGameArray;
    }

    //#endregion

    //#region Manejo de cuentas de clientes 

    /**
     * Obtiene la lista de clientes registrados.
     */
    public Customer[] getCustomers()
    {
        Customer[] customerArray = new Customer[customerList.size()];
        customerList.toArray(customerArray);

        return customerArray;
    }

    /**
     * Registra una nueva cuenta de cliente.
     * 
     * @param customer Objeto con la información del cliente.
     * @return
     */
    public boolean registerCustomer(Customer customer)
    {
        customerList.add(customer);
        return true;
    }

    /**
     * Actualiza la informacion de un cliente.
     * 
     * @param customer Objeto con la información del cliente.
     * @return
     */
    public boolean updateCustomer(Customer customer)
    {
        return true;
    }

    /**
     * Actualiza la informacion de inicio de sesion de un cliente.
     * 
     * @param credentials Objeto con las credenciales del cliente.
     * @return
     */
    public boolean updateCustomerCredentials(CustomerCredentials credentials)
    {
        return true;
    }

    /**
     * Elimina la cuenta de un cliente.
     * 
     * @param customer Objeto con la información del cliente.
     * @return
     */
    public boolean deleteCustomer(Customer customer)
    {
        customerList.remove(customer);
        return true;
    }

    //#endregion

    //#region Manejo de lista de deseos

    public boolean addGameToWishList(Customer customer, Game game)
    {
        GameInWishList gameInWishList = new GameInWishList();
        gameInWishList.customer = customer;
        gameInWishList.game = game;
        gameInWishList.date = LocalDate.now();

        wishLists.add(gameInWishList);
        return true;
    }

    public boolean removeGameFromWishList(GameInWishList gameInWishList)
    {
        wishLists.remove(gameInWishList);
        return true;
    }

    public GameInWishList[] getCustomerWishList(Customer customer)
    {
        ArrayList<GameInWishList> filteredWishList = new ArrayList<>();
        GameInWishList[] filteredWishListArray;

        for (GameInWishList gameInWishList : wishLists) 
        {
            if (gameInWishList.customer.id == customer.id)
            {
                filteredWishList.add(gameInWishList);
            }
        }

        filteredWishListArray = new GameInWishList[filteredWishList.size()];
        filteredWishList.toArray(filteredWishListArray);

        return filteredWishListArray;
    }

    //#endregion

    //#region Manejo de carrito de compras

    public boolean addGameToShoppingCar(Customer customer, Game game)
    {
        return true;
    }

    public boolean removeGameFromShoppingCar(Customer customer, GameInWishList gameInWishList)
    {
        return true;
    }

    public GameInShoppingCar[] getCustomerShoppingCar(Customer customer)
    {
        return new GameInShoppingCar[0];
    }

    //#endregion

    //#region Manejo de lista de juegos comprados

    public boolean buyGame(Customer customer, Game game)
    {
        return true;
    }

    public PurchasedGame[] getPurchasedGames(Customer customer)
    {
        return new PurchasedGame[0];
    }

    //#endregion

    //#region Private methods

    // Rellena las listas internas con informacion de muestra.
    public void populateLists()
    {
        if (listsPopulated)
        {
            return;
        }

        // Define algunas categorías
        GameCategory roleCategory = new GameCategory(
            1,
            "Juegos de rol",
            "");
        GameCategory shooterCategory = new GameCategory(
            2,
            "Shooter",
            "");
        GameCategory platformCategory = new GameCategory(
            3, 
            "Plataformas",
            "");
        GameCategory actionCategory = new GameCategory(
            4,
            "Acción",
            "");
        GameCategory adventureCategory = new GameCategory(
            5, 
            "Aventura",
            "");

        // Agrega las categorías a la lista
        categoryList.add(roleCategory);
        categoryList.add(shooterCategory);
        categoryList.add(platformCategory);
        categoryList.add(actionCategory);
        categoryList.add(adventureCategory);

        // Define y agrega algunos juegos
        gameList.add(new Game(
            1, 
            "Elden Ring", 
            "FromSoftware",
            2022,
            59.99f,
            "Un mundo abierto épico con una jugabilidad desafiante y una narrativa envolvente creada por Hidetaka Miyazaki y George R.R. Martin.",
            new GameCategory[] {
                roleCategory, 
                adventureCategory, 
                actionCategory
            }
        ));
        gameList.add(new Game(
            2,
            "Half-Life: Alyx",
            "Valve",
            2020,
            59.99f,
            "Una experiencia inmersiva en realidad virtual que continúa la saga Half-Life, centrada en la historia de Alyx Vance.",
            new GameCategory[] { shooterCategory }
        ));
        gameList.add(new Game(
            3,
            "Baldur's Gate 3",
            "Larian Studios",
            2021,
            59.99f,
            "La tercera parte de la saga de rol clásico, con una gran fidelidad a los manuales de Dragones y Mazmorras.",
            new GameCategory[] { roleCategory }
        ));
        gameList.add(new Game(
            4,
            "Persona 5 Royal",
            "ALTUS",
            2020,
            59.99f,
            "Una versión extendida y mejorada del exitoso juego de rol japonés, con nuevas zonas, personajes y características jugables.",
            new GameCategory[] { roleCategory }));
        gameList.add(new Game(
            5,
            "Portal 2",
            "Valve",
            2011,
            19.99f,
            "Un juego de culto que combina puzles y plataformas en primera persona con un modo cooperativo adictivo.",
            new GameCategory[] { platformCategory }
        ));
        gameList.add(new Game(
            6,
            "Half-Life 2",
            "Valve",
            2004,
            9.99f,
            "La secuela del revolucionario Half-Life, que destaca por su diseño y narrativa en un mundo postapocalíptico.",
            new GameCategory[] { roleCategory }
        ));
        gameList.add(new Game(
            7,
            "The Witcher 3: Wild Hunt",
            "CD Projekt Red",
            2015,
            39.99f,
            "Un juego de rol en un mundo abierto con una historia profunda y gráficos impresionantes.",
            new GameCategory[] { roleCategory }
        ));
        gameList.add(new Game(
            8,
            "Divinity: Original Sin 2",
            "Larian Studios",
            2017,
            44.99f,
            "Un RPG táctico con una gran libertad de elección y consecuencias significativas en un mundo rico y detallado.",
            new GameCategory[] { roleCategory }
        ));
        gameList.add(new Game(
            6,
            "Red Dead Redemption 2",
            "Rockstar Games",
            2019,
            59.99f,
            "Una épica historia del oeste americano con un mundo abierto detallado y una jugabilidad envolvente.",
            new GameCategory[] { actionCategory, adventureCategory }
        ));
        gameList.add(new Game(
            6,
            "God of War",
            "Santa Monica Studio",
            2022,
            49.99f,
            "La aclamada saga regresa con una narrativa madura y una mecánica de combate refinada en el mundo de la mitología nórdica.",
            new GameCategory[] { actionCategory, adventureCategory }
        ));

        Customer admin = new Customer(
            1,
            "@danny",
            "Danny DZ"
        );
        admin.credentials = new CustomerCredentials(
            1,
            "danny@gmail.com",
            "1234"
        );

        customerList.add(admin);

        Customer dorito = new Customer(
            2,
            "@eldorito",
            "Luis"
        );
        dorito.credentials = new CustomerCredentials(
            2,
            "dorito@gmail.com",
            "1234"
        );

        customerList.add(dorito);
        listsPopulated = true;
    }

    //#endregion
}
