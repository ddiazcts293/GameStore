/*
 * Archivo: Console.java
 * Autor: Díaz Cortés Daniel Salomón
 * Grupo: 1A-DSM
 * Descripción: Proporciona las funciones para controlar lo que se muestra en 
 * la consola.
 */

package com.powerrangers.util;

public class Console 
{
    public static void saveCursorPosition()
    {
        System.out.print("\033[s");
    }

    public static void restoreCursorPosition()
    {
        System.out.print("\033[u");
    }

    public static void clearLine(int flag)
    {
        if (flag >= 0 && flag <= 2)
            System.out.printf("\033[%dK", flag);
    }

    public static void clearDisplay(int flag)
    {
        if (flag >= 0 && flag <= 4)
            System.out.printf("\033[%dJ", flag);
    }
}
