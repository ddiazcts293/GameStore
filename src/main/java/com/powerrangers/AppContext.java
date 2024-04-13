package com.powerrangers;

import java.util.HashMap;
import java.util.Scanner;

import com.powerrangers.db.DbContext;
import com.powerrangers.screen.*;
import com.powerrangers.util.*;

public class AppContext
{
    private boolean _inialized;
    private ScreenOption _currentScreen;
    private ScreenOption _previousScreen;

    private Scanner _scanner;
    private DbContext _dbContext;
    private HashMap<ScreenOption, ScreenBase> _screenInstances;
    
    public AppContext(Scanner scanner)
    {
        _inialized = false;
        _scanner = scanner;
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

    public void goToScreen(ScreenOption screen)
    {
        if (screen != ScreenOption.Unknown && _currentScreen != screen)
        {
            _previousScreen = _currentScreen;
            _currentScreen = screen;

            showCurrentScreen();
        }
    }

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

    public ScreenOption getCurrentScreen()
    {
        return _currentScreen;
    }

    public Scanner getScanner()
    {
        return _scanner;
    }

    public DbContext getDbContext()
    {
        return _dbContext;
    }

    public Menu createMenu()
    {
        return new Menu(_scanner);
    }

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

    public void exit()
    {
        // Guardar base de datos
        System.exit(0);
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
