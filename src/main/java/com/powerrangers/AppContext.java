/*
 * Archivo: AppContext.java
 * Autor: Díaz Cortés Daniel Salomón
 * Grupo: 1A-DSM
 * Descripción: Proporciona las funciones para mostrar menús, cambiar de 
 * pantalla y otros componentes utilizados en todo el programa.
 */

package com.powerrangers;

import java.util.HashMap;
import java.util.Scanner;

import com.powerrangers.db.DbContext;
import com.powerrangers.db.types.Customer;
import com.powerrangers.screen.*;
import com.powerrangers.util.*;

public class AppContext
{
    private boolean _inialized;
    private ScreenOption _currentScreen;
    private ScreenOption _previousScreen;
    private Customer _currentCustomer;

    // Instancia de Scanner que se utiliza en todo el programa
    private Scanner _scanner;
    // Instancia de conector con la base de datos
    private DbContext _dbContext;
    // Un HashMap que almacena las instancias de las diferentes pantallas
    private HashMap<ScreenOption, ScreenBase> _screenInstances;
    
    /**
     * Constructor predeterminado.
     * 
     * @param scanner Instancia de la clase Scanner.
     */
    public AppContext(Scanner scanner)
    {
        _inialized = false;
        _scanner = scanner;
        _currentCustomer = null;
        _dbContext = new DbContext();
        _screenInstances = new HashMap<>();
        
        _screenInstances.put(ScreenOption.MainScreen, new MainScreen());
        _screenInstances.put(ScreenOption.Login, new LoginScreen());
        _screenInstances.put(ScreenOption.SignUp, new SignUpScreen());
        _screenInstances.put(ScreenOption.AccountSettings, new AccountSettingsScreen());
        _screenInstances.put(ScreenOption.GameLibrary, new GameLibraryScreen());
        _screenInstances.put(ScreenOption.GameCatalog, new GameCatalogScreen());
        _screenInstances.put(ScreenOption.Recommendation, new RecommendationScreen());
    }

    // Metodos publicos

    /**
     * Inicializa AppContext.
     * 
     * @apiNote Esta función solo puede ser llamada una vez.
     */
    public void initialize()
    {
        // Verifica que no se haya inicializa previamente.
        if (!_inialized)
        {
            _inialized = true;
            
            // Crea un bucle do-while que se ejecutará indefinidamente en el 
            // cual se muestra la pantalla/menú principal hasta que el propio
            // programa finalize, ya sea cerrando la ventana o al llamar a 
            // exit().
            // Esto es necesario para evitar que el programa termine 
            // abruptamente después de mostrar una sección que no hace nada al
            // llegar a su final.
            do
            {
                goToScreen(ScreenOption.MainScreen);
            }
            while (true);
        }
    }

    /**
     * Finaliza la ejecución del programa.
     */
    public void exit()
    {
        // NOTA: Implementar guardado y desconexión de la base de datos.

        // Llamada a una función que finaliza el programa
        System.exit(0);
    }

    /**
     * Navega hacia una pantalla especifica.
     * 
     * @param screen Pantalla a dirigirse
     */
    public void goToScreen(ScreenOption screen)
    {
        // Verifica que la opción recibida esté definida y no sea la misma que
        // se está mostrando actualmente
        if (screen != ScreenOption.Unknown && _currentScreen != screen)
        {
            // Actualiza los valores de las variables de pantalla previa y 
            // pantalla actual
            _previousScreen = _currentScreen;
            _currentScreen = screen;

            // Llama a la función que muestra la pantalla actual
            showCurrentScreen();
        }
    }

    /**
     * Navega hacia la pantalla previamente mostrada.
     */
    public void goToPreviousScreen()
    {
        // Verifica que la pantalla actual esté definida
        if (_currentScreen != ScreenOption.Unknown)
        {
            // Actualiza los valores de las variables de pantalla previa y
            // pantalla anterior
            ScreenOption screen = _previousScreen;
            _previousScreen = _currentScreen;
            _currentScreen = screen;

            // Llama a la función que muestra la pantalla actual
            showCurrentScreen();
        }
    }

    /**
     * Obtiene un indicador de la pantalla mostrada actualmente.
     * 
     * @return Indicador de la pantalla mostrada.
     */
    public ScreenOption getCurrentScreen()
    {
        return _currentScreen;
    }

    /**
     * Obtiene la instancia del objeto Scanner que es reutilizado para 
     * recibir datos en diferentes formatos.
     * 
     * @return Objeto Scanner.
     */
    public Scanner getScanner()
    {
        return _scanner;
    }

    /**
     * Obtiene la instancia del objeto de contexto de base de datos.
     * 
     * @return Objeto DbContext
     */
    public DbContext getDbContext()
    {
        return _dbContext;
    }

    /**
     * Obtiene el objeto Customer que contiene la información del cliente que 
     * ha iniciado sesión (en caso de haberlo hecho).
     * 
     * @return Objeto Customer o null si aún no se ha iniciado sesión en 
     * ninguna cuenta.
     */
    public Customer getCurrentCustomer()
    {
        return _currentCustomer;
    }

    /**
     * Obtiene un valor booleano que indica si un cliente ha iniciado sesión en
     * el programa.
     * 
     * @return Valor booleano.
     */
    public boolean isLoggedIn()
    {
        // Devuelve el resultado de la comparación entre la variable que
        // almacena el cliente actual y null
        return _currentCustomer != null;
    }

    /**
     * Inicia sesión con base a las credenciales de acceso de una cuenta de un
     * cliente.
     * 
     * @param username Username o email del cliente.
     * @param password Contraseña del cliente.
     * 
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean login(String username, String password)
    {
        Customer[] customerList = _dbContext.getCustomers();

        // Bucle que recorre la lista de usuarios registrados
        for (Customer customer : customerList) 
        {
            var testCredentials = customer.credentials;

            // Verifica si tanto el email como la contrasena coinciden con las
            // del cliente actual
            if (testCredentials.email.compareTo(username) == 0 &&
                testCredentials.password.compareTo(password) == 0)
            {
                // Obtiene la informacion del cliente actual
                _currentCustomer = customer;
                break; // Sale del ciclo
            }
        }

        return isLoggedIn();
    }

    /**
     * Registra a un nuevo cliente.
     * @param customer Objeto con la información de un cliente nuevo.
     * 
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean signup(Customer customer)
    {
        // Realiza el registro de un cliente nuevo
        boolean result = _dbContext.registerCustomer(customer);

        // Verifica si la operación tuvo éxito para establecer el valor de la
        // variable que almacena el cliente actual
        if (result)
        {
            _currentCustomer = customer;
        }

        return result;
    }

    /**
     * Borra la cuenta del cliente actual.
     * 
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean deleteCustomerAccount()
    {
        // Realiza el borrado de la cuenta de la base de datos
        boolean result = _dbContext.deleteCustomer(_currentCustomer);
        
        // Verifica si la operación tuvo éxito para restablecer el valor de la
        // variable que almacena al cliente actual en null.
        // Esto es para que el programa actue como si se haya cerrado la sesión
        if (result)
        {
            _currentCustomer = null;
        }

        return result;
    }

    /**
     * Crea un nuevo objeto Menu que, como su nombre lo indica, puede ser 
     * utilizado para crear menús interactivos.
     * 
     * @return Objeto Menú
     */
    public Menu createMenu()
    {
        return new Menu(_scanner);
    }

    // Metodos privados

    /**
     * Función que muestra la pantalla actual.
     */
    private void showCurrentScreen()
    {
        // Obtiene la instancia correspondiente al indicador de pantalla
        var instance = _screenInstances.get(_currentScreen);
        
        // Si la instancia no es null, entonces la muestra
        if (instance != null)
        {
            // Llamada a la función que borra el contenido toda la pantalla
            Console.clearDisplay(2);
            // Muestra la pantalla y le pasa como argumento la propia instancia
            // de AppContext
            instance.show(this);
        }
    }
}
