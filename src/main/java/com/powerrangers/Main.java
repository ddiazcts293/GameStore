/*
 * Archivo: Main.java
 * Autor: Díaz Cortés Daniel Salomón
 * Grupo: 1A-DSM
 * Descripción: Punto de entrada del programa.
 */

package com.powerrangers;

import java.util.Scanner;
import com.powerrangers.db.*;
import com.powerrangers.screen.*;

public class Main 
{
    public static void main(String[] args) 
    {
        // Inicializa un escaner de entrada de datos de usuario. Esta instancia
        // sera la unica que se utilizara en todo el programa.
        Scanner scanner = new Scanner(System.in);
        AppContext appContext = new AppContext(scanner);
        DbContext dbContext = appContext.getDbContext();
        
        // En algun momento dado se hara aqui la conexion con la base de datos
        // Por ahora solo se limitara a rellenar listas con datos de muestra.
        dbContext.populateLists();

        // Se dirige a la pantalla principal.
        appContext.goToScreen(ScreenOption.MainScreen);

        // Finaliza el escaner de entrada de datos para que libere los recursos
        // que este utiliza.
        scanner.close();
    }
}
