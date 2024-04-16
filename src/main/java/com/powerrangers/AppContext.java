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

    private Scanner _scanner;
    private DbContext _dbContext;
    private HashMap<ScreenOption, ScreenBase> _screenInstances;
    
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
    }

    // Metodos publicos

    /**
     * Inicializa AppContext.
     * 
     * @apiNote Esta función solo puede ser llamada una vez.
     */
    public void initialize()
    {
        if (!_inialized)
        {
            _inialized = true;
            
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
        // Implementar guardado base de datos
        System.exit(0);
    }

    /**
     * Cambia a una sección del programa especificada.
     * 
     * @param screen Sección a cambiar.
     */
    public void goToScreen(ScreenOption screen)
    {
        if (screen != ScreenOption.Unknown && _currentScreen != screen)
        {
            _previousScreen = _currentScreen;
            _currentScreen = screen;

            showCurrentScreen();
        }
    }

    /**
     * Cambia a la sección mostrada previamente.
     */
    public void goToPreviousScreen()
    {
        if (_currentScreen != ScreenOption.Unknown)
        {
            ScreenOption screen = _previousScreen;
            _previousScreen = _currentScreen;
            _currentScreen = screen;

            showCurrentScreen();
        }
    }

    /**
     * Obtiene un indicador de la sección del programa mostrada actualmente.
     * 
     * @return Indicador de la sección del programa.
     */
    public ScreenOption getCurrentScreen()
    {
        return _currentScreen;
    }

    /**
     * Obtiene la instancia del objeto Scanner utilizada por todo el programa.
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
     * Obtiene la instancia del objeto Customer asociado al cliente con sesión
     * iniciada.
     * 
     * @return Objeto Customer
     */
    public Customer getCurrenCustomer()
    {
        return _currentCustomer;
    }

    /**
     * Devuelve un indicador booleano que específica si un cliente ha iniciado
     * sesión en el programa.
     * 
     * @return Valor booleano.
     */
    public boolean isLoggedIn()
    {
        return _currentCustomer != null;
    }

    /**
     * Inicia sesión con base a la información de un cliente dada.
     * @param email Correo del cliente.
     * @param password Contraseña del cliente.
     * 
     * @return Valor booleano que indica si la operación tuvo éxito.
     */
    public boolean login(String email, String password)
    {
        Customer[] customerList = _dbContext.getCustomers();

        // Bucle que recorre la lista de usuarios registrados
        for (Customer customer : customerList) 
        {
            var testCredentials = customer.credentials;

            // Verifica si tanto el email como la contrasena coinciden con las
            // del cliente actual
            if (testCredentials.email.compareTo(email) == 0 &&
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
        boolean result = _dbContext.createCustomer(customer);
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
        boolean result = _dbContext.deleteCustomer(_currentCustomer);
        if (result)
        {
            _currentCustomer = null;
        }

        return result;
    }

    /**
     * Crea un nuevo objeto Menú que, como su nombre lo indica, puede ser 
     * utilizado para crear menús interactivos.
     * 
     * @return Objeto Menú
     */
    public Menu createMenu()
    {
        return new Menu(_scanner);
    }

    // Metodos privados

    private void showCurrentScreen()
    {
        var instance = _screenInstances.get(_currentScreen);
        
        if (instance != null)
        {
            //Console.clearDisplay(2);
            instance.show(this);
        }
    }
}
