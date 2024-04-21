/*
 * Archivo: DbContext.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que proporciona las funciones necesarias para la 
 * obtención y manipulación de la información provista por la base de datos.
 */

package com.powerrangers.db;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import com.powerrangers.db.types.*;

public class DbContext 
{
    Connection _dbConnection = null;

    // Define las listas de catalogo y de usuarios registrados
    // Por ahora se limitará a almacenar los datos en la memoria estática
    private ArrayList<GameCategory> categoryList = new ArrayList<>();
    private ArrayList<Game> gameList = new ArrayList<>();
    private ArrayList<Customer> customerList = new ArrayList<>();
    private ArrayList<GameInShoppingCar> shoppingCars = new ArrayList<>();
    private ArrayList<GameInWishList> wishLists = new ArrayList<>();
    private ArrayList<PurchasedGame> purchasedGames = new ArrayList<>();

    /**
     * Indica si se ha rellenado las listas internas.
     */
    private boolean listsPopulated = false;

    public boolean connectToMySQL(String connectionString)
    {
        try
        {
            _dbConnection = DriverManager.getConnection(
                connectionString, "program", "superpwd");
        }
        catch (Exception exception)
        {
            return false;
        }

        return true;
    }

    //#region Catálogo de juegos

    /**
     * Obtiene un arreglo con todas las categorías de juegos disponibles.
     * 
     * @return Arreglo del tipo GameCategory.
     */
    public GameCategory[] getGameCategories()
    {
        ArrayList<GameCategory> list = new ArrayList<>();

        try (var statement = _dbConnection.createStatement())
        {
            String sqlQuery = "SELECT * FROM GameCategories";
            var resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) 
            {
                GameCategory category = new GameCategory();
                category.id = resultSet.getInt("id");
                category.title = resultSet.getString("title");    
                category.description = resultSet.getString("description");

                list.add(category);
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: " + ex.getMessage());
        }

        GameCategory[] array = new GameCategory[list.size()];
        list.toArray(array);
        return array;
    }
    
    /**
     * Obtiene un arreglo con todos los juegos disponibles.
     * 
     * @return Arreglo del tipo Game.
     */
    public Game[] getGames()
    {
        // SELECT * FROM Games
        // SELECT * FROM GamesByCategories WHERE game_id = 1

        Game[] gameArray = new Game[gameList.size()];
        gameList.toArray(gameArray);

        return gameArray;
    }

    /**
     * Obtiene un arreglo con todos los juegos disponibles que corresponden a 
     * una categoría específica.
     * 
     * @param categoryToFilter Objeto GameCategory que representa la categoría 
     * de juegos a obtener.
     * @return Arreglo del tipo Game.
     */
    public Game[] getGames(GameCategory categoryToFilter)
    {
        // SELECT Games.id, Games.name, Games.developer, Games.year, Games.price, 
        // Games.description FROM Games 
        // INNER JOIN GamesByCategories
        // ON Games.id = GamesByCategories.game_id
        // WHERE GamesByCategories.category_id = 2

        if (categoryToFilter == null)
        {
            return new Game[0];
        }

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
     * Obtiene un objeto con la información de un cliente con base a sus
     * credenciales de acceso.
     * 
     */
    public Customer searchCustomer(String email, String password)
    {
        Customer foundCustomer = null;

        try
        {
            String sqlQuery = 
                "SELECT * " +
                "FROM Customers " +
                "INNER JOIN CustomerCredentials " +
                "ON Customers.id = CustomerCredentials.customer_id " +
                "WHERE CustomerCredentials.email = ? " +
                "AND CustomerCredentials.password = ?";

            var statement = _dbConnection.prepareStatement(sqlQuery);
            statement.setString(1, email);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) 
            {
                foundCustomer = new Customer();
                foundCustomer.id = resultSet.getInt(1);
                foundCustomer.username = resultSet.getString(2);    
                foundCustomer.name = resultSet.getString(3);
                foundCustomer.credentials.id = resultSet.getInt(4);
                foundCustomer.credentials.email = resultSet.getString(5);
                foundCustomer.credentials.password = resultSet.getString(6);
                foundCustomer.credentials.customer = foundCustomer;
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: " + ex.getMessage());
            foundCustomer = null;
        }

        return foundCustomer;
    }

    /**
     * Registra una nueva cuenta de cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean registerCustomer(Customer customer)
    {
        // INSERT INTO Customers (name, username) VALUES (?, ?)
        // INSERT INTO CustomerCredentials (customer_id, email, password) 
        // VALUES ([id], 'admin@gamestore.com', 'admin')

        String sqlCustomerQuery = 
            "INSERT INTO Customers (name, username) VALUES (?, ?)";
        String sqlCustomerCredentialsQuery =
            "INSERT INTO CustomerCredentials (customer_id, email, password) " +
            "VALUES (?, ?, ?)";
        
        try
        {
            var statement = _dbConnection.prepareStatement(
                sqlCustomerQuery, 
                java.sql.Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, customer.name);
            statement.setString(2, customer.username);

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
            {
                var resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                {
                    customer.id = resultSet.getInt(1);
                }
            }

            statement = _dbConnection.prepareStatement(
                sqlCustomerCredentialsQuery, 
                java.sql.Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, customer.id);
            statement.setString(2, customer.credentials.email);
            statement.setString(3, customer.credentials.password);

            rowAffected = statement.executeUpdate();
            if (rowAffected == 1)
            {
                var resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                {
                    customer.credentials.id = resultSet.getInt(1);
                }
            }
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Actualiza la información de un cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean updateCustomer(Customer customer)
    {
        // UPDATE Customers 
        // SET username = 'Elmer Homero', name = 'sysadmin' 
        // WHERE id = 3
     
        String sqlQuery = "UPDATE Customers SET username = ?, name = ? " + 
            "WHERE id = ?";

        try
        {
            var statement = _dbConnection.prepareStatement(sqlQuery);
            statement.setString(1, customer.name);
            statement.setString(2, customer.username);
            statement.setInt(3, customer.id);

            statement.executeUpdate();
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: " + ex.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Actualiza la información de inicio de sesión de un cliente.
     * 
     * @param credentials Objeto CustomerCredentials con las credenciales del 
     * cliente.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean updateCustomerCredentials(CustomerCredentials credentials)
    {
        // UPDATE CustomerCredentials 
        // SET email = 'admin@gamestore.net', password = 'admin_123' 
        // WHERE customer_id = '3'

        return true;
    }

    /**
     * Elimina la cuenta de un cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean deleteCustomer(Customer customer)
    {
        // DELETE FROM Customers WHERE id = 3

        customerList.remove(customer);
        return true;
    }

    //#endregion

    //#region Manejo de lista de deseos

    /**
     * Agrega un juego a la lista de deseos de un cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @param game Objeto Game con la información del juego a agregar.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean addGameToWishList(Customer customer, Game game)
    {
        // INSERT INTO GamesInWishList (customer_id, game_id, date) 
        // VALUES (2, 12, datetime())

        GameInWishList gameInWishList = new GameInWishList();
        gameInWishList.customer = customer;
        gameInWishList.game = game;
        gameInWishList.date = LocalDate.now();

        wishLists.add(gameInWishList);
        return true;
    }

    /**
     * Elimina un juego de la lista de deseos de un cliente.
     * 
     * @param gameInWishList Objeto GameInWishList con la información del juego
     * agregado previamente.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean removeGameFromWishList(GameInWishList gameInWishList)
    {
        // DELETE FROM GamesInWishList WHERE id = 6

        wishLists.remove(gameInWishList);
        return true;
    }

    /**
     * Obtiene un arreglo con todos los juegos en la lista de deseos de un 
     * cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @return Arreglo del tipo GameInWishList.
     */
    public GameInWishList[] getCustomerWishList(Customer customer)
    {
        // SELECT GamesInWishList.id, GamesInWishList.customer_id, 
        // GamesInWishList.game_id, GamesInWishList.date, Games.name, 
        // Games.price, Games.developer
        // FROM ((GamesInWishList
        // INNER JOIN Customers ON Customers.id = GamesInWishList.customer_id)
        // INNER JOIN Games ON Games.id = GamesInWishList.game_id)
        // WHERE GamesInWishList.customer_id = 1

        if (customer == null)
        {
            return new GameInWishList[0];
        }

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

    /**
     * Agrega un juego al carrito de compras de un cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @param game Objeto Game con la información del juego a agregar.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean addGameToShoppingCar(Customer customer, Game game)
    {
        GameInShoppingCar gameInShoppingCar = new GameInShoppingCar();
        gameInShoppingCar.customer = customer;
        gameInShoppingCar.game = game;

        shoppingCars.add(gameInShoppingCar);
        return true;
    }

    /**
     * Elimina un juego del carrito de compras de un cliente.
     * 
     * @param gameInShoppingCar Objeto GameInShoppingCar con la información del
     * juego agregado previamente.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean removeGameFromShoppingCar(GameInShoppingCar gameInShoppingCar)
    {
        shoppingCars.remove(gameInShoppingCar);
        return true;
    }

    /**
     * Obtiene un arreglo con todos los juegos en el carrito de compras de un 
     * cliente.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @return Arreglo del tipo GameInShoppingCar.
     */
    public GameInShoppingCar[] getCustomerShoppingCar(Customer customer)
    {
        if (customer == null)
        {
            return new GameInShoppingCar[0];
        }

        ArrayList<GameInShoppingCar> filteredList = new ArrayList<>();
        GameInShoppingCar[] filteredArray;

        for (GameInShoppingCar gameInShoppingCar : shoppingCars) 
        {
            if (gameInShoppingCar.customer.id == customer.id)
            {
                filteredList.add(gameInShoppingCar);
            }
        }

        filteredArray = new GameInShoppingCar[filteredList.size()];
        filteredList.toArray(filteredArray);

        return filteredArray;
    }

    //#endregion

    //#region Manejo de lista de juegos comprados

    /**
     * Efectua la compra de un videojuego.
     *  
     * @param customer Objeto Customer con la información del cliente.
     * @param game Objeto Game con la información del juego a comprar.
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean buyGame(Customer customer, Game game)
    {
        PurchasedGame purchasedGame = new PurchasedGame();
        purchasedGame.customer = customer;
        purchasedGame.game = game;

        purchasedGames.add(purchasedGame);
        return true;
    }

    /**
     * Obtiene un arreglo con todos los juegos que el cliente ha comprado.
     * 
     * @param customer Objeto Customer con la información del cliente.
     * @return Arreglo del tipo PurchasedGame.
     */
    public PurchasedGame[] getPurchasedGames(Customer customer)
    {
        if (customer == null)
        {
            return new PurchasedGame[0];
        }

        ArrayList<PurchasedGame> filteredList = new ArrayList<>();
        PurchasedGame[] filteredArray;

        for (PurchasedGame purchasedGame : purchasedGames) 
        {
            if (purchasedGame.customer.id == customer.id)
            {
                filteredList.add(purchasedGame);
            }
        }

        filteredArray = new PurchasedGame[filteredList.size()];
        filteredList.toArray(filteredArray);

        return filteredArray;
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
            9,
            "Red Dead Redemption 2",
            "Rockstar Games",
            2019,
            59.99f,
            "Una épica historia del oeste americano con un mundo abierto detallado y una jugabilidad envolvente.",
            new GameCategory[] { actionCategory, adventureCategory }
        ));
        gameList.add(new Game(
            10,
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

        wishLists.add(new GameInWishList(
            1, 
            dorito, 
            gameList.get(0))
        );
        wishLists.add(new GameInWishList(
            2, 
            dorito, 
            gameList.get(4))
        );
        wishLists.add(new GameInWishList(
            3, 
            dorito, 
            gameList.get(5))
        );
        wishLists.add(new GameInWishList(
            4, 
            admin, 
            gameList.get(1))
        );
        wishLists.add(new GameInWishList(
            5, 
            admin, 
            gameList.get(7))
        );
        wishLists.add(new GameInWishList(
            6, 
            admin, 
            gameList.get(8))
        );
        shoppingCars.add(new GameInShoppingCar(
            1, 
            dorito, 
            gameList.get(3))
        );
        shoppingCars.add(new GameInShoppingCar(
            2, 
            dorito, 
            gameList.get(2))
        );
        shoppingCars.add(new GameInShoppingCar(
            3, 
            admin,
            gameList.get(9))
        );

        customerList.add(dorito);

        Customer Roberto = new Customer(
            3,
            "@daikirai",
            "Roberto"
        );
        Roberto.credentials = new CustomerCredentials(
            3,
            "rcfb@gmail.com",
            "DooM"
        );

        customerList.add(Roberto);
        listsPopulated = true;
    }

    //#endregion
}
