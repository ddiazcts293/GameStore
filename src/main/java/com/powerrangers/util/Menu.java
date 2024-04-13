/*
 * Archivo: Menu.java
 * Autor: Díaz Cortés Daniel Salomón
 * Grupo: 1A-DSM
 * Descripción: Proporciona las funciones para mostrar un menú de diferentes
 * opciones en el que el usuario podrá elegir una de ellas.
 */

package com.powerrangers.util;

import java.util.Scanner;
import java.util.HashMap;

public class Menu 
{
    private Scanner _scanner;
    private HashMap<String, String> _items;

    public Menu(Scanner scanner)
    { 
        _scanner = scanner;
        _items = new HashMap<>();
    }

    public Menu AddItem(String key, String text)
    {
        _items.put(key, text);
        return this;
    }

    public String show()
    {
        return show("");
    }

    public String show(String prompt)
    {
        String enteredKey = "";

        System.out.println();
        _items.forEach((k, s) -> System.out.printf("[%s] - %s\n\033[s", k, s));

        do
        {
            System.out.printf("\033[u\033[0K%s: ", prompt);

            try
            {
                enteredKey = _scanner.nextLine();
            }
            catch (Exception ex)
            {
            }
        }
        while (!_items.containsKey(enteredKey));

        return enteredKey;
    }
}
