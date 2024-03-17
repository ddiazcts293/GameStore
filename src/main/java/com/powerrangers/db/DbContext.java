/*
 * Archivo: DbContext.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que representa un enlace entre el programa y la base de
 * datos.
 */

package com.powerrangers.db;

import java.util.ArrayList;
import com.powerrangers.db.types.*;

public class DbContext 
{
    // Define las listas de catalogo y de usuarios registrados
    // Por ahora se limitará a almacenar los datos en la memoria
    private ArrayList<GameCategory> categoryList;
    private ArrayList<Game> gameList;
    private ArrayList<Customer> customerList;

    // Constructor predeterminado
    public DbContext()
    {
        // Crea las instancias de las listas
        categoryList = new ArrayList<>();
        gameList = new ArrayList<>();
        customerList = new ArrayList<>();

        // Rellena las listas con datos de ejemplo
        populateLists();
    }

    public ArrayList<GameCategory> getGameCategories()
    {
        return categoryList;
    }
    
    // Función que devuelve una lista de todos los juegos disponibles
    public ArrayList<Game> getGames()
    {
        return gameList;
    }

    // Función que devuelve la lista de los juegos disponibles segun una 
    // categoria dada
    public ArrayList<Game> getGames(GameCategory category)
    { 
        return gameList;
    }

    // Función que localiza a un cliente con base a sus credenciales
    public Customer findCustomer(String email, String password)
    {
        Customer foundCustomer = null;

        // Ciclo que recorre la lista de usuarios registrados
        for (Customer customer : customerList) 
        {
            var testCredentials = customer.credentials;

            // Verifica si tanto el email como la contrasena coinciden con las
            // del cliente actual
            if (testCredentials.email.compareTo(email) == 0 &&
                testCredentials.password.compareTo(password) == 0)
            {
                // Obtiene la informacion del cliente actual
                foundCustomer = customer;
                break; // Sale del ciclo
            }
        }

        return foundCustomer;
    }

    public boolean updateCustomer(Customer customer)
    {
        return true;
    }

    public boolean updateCustomerCredentials(CustomerCredentials credentials)
    {
        return true;
    }

    public boolean deleteCustomer(Customer customer)
    {
        return true;
    }

    //#region Private methods

    // Función que rellena las listas con datos de muestra
    private void populateLists()
    {
        GameCategory roleCategory = new GameCategory(
            1,
            "Juegos de rool",
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
            "Accion",
            "");
        GameCategory adventureCategory = new GameCategory(
            5, 
            "Aventura",
            "");

        categoryList.add(roleCategory);
        categoryList.add(shooterCategory);
        categoryList.add(platformCategory);
        categoryList.add(actionCategory);
        categoryList.add(adventureCategory);

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
            "@admin",
            "ADMIN"
        );
        admin.credentials = new CustomerCredentials(
            1,
            "admin@gamestore.com",
            "1234"
        );

        customerList.add(admin);
    }

    //#endregion
}
