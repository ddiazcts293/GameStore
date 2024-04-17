/*
 * Archivo: Menu.java
 * Autor: Díaz Cortés Daniel Salomón
 * Grupo: 1A-DSM
 * Descripción: Proporciona las funciones para mostrar un menú de diferentes
 * opciones en el que el usuario podrá elegir una de ellas.
 */

package com.powerrangers.util;

import java.util.Scanner;
import java.util.*;

public class Menu 
{
    private class MenuItem
    {
        private String _key;
        private String _text;
        
        public MenuItem(String key, String text)
        {
            _key = key;
            _text = text;
        }
     
        public String getKey()
        {
            return _key;
        }

        public String getText()
        {
            return _text;
        }
    }

    private Scanner _scanner;
    private ArrayList<MenuItem> _items;

    public Menu(Scanner scanner)
    { 
        _scanner = scanner;
        _items = new ArrayList<>();       
    }

    public Menu AddItem(String key, String text)
    {
        _items.add(new MenuItem(key, text));
        return this;
    }

    public void clear()
    {
        _items.clear();
    }

    public String show()
    {
        return show("");
    }

    public String show(String prompt)
    {
        String enteredKey = "";
        boolean exitFromLoop = false;

        System.out.println();
        _items.forEach((item) -> 
            System.out.printf("[%s] - %s\n\033[s", 
                item.getKey(), 
                item.getText()));

        do
        {
            System.out.printf("\033[u\033[0K%s: ", prompt);

            try
            {
                enteredKey = _scanner.nextLine();
            }
            catch (Exception ex)
            {
                continue;
            }

            for (var item : _items) 
            {
                if (item.getKey().compareTo(enteredKey) == 0)
                {
                    exitFromLoop = true;
                }    
            }
        }
        while (!exitFromLoop);

        return enteredKey;
    }
}
