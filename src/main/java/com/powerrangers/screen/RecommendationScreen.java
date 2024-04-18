/*	   Alumno: Romero Garcia Mauricio
	   Carrera: Desarrollo de Software Multiplataforma 
	   Grupo: 1-A
	   Contribución: Proyecto Tienda de Videojuegos (Recomendaciones)
 * 
 */
package com.powerrangers.screen;
import java.util.Scanner;
import com.powerrangers.AppContext;           
import com.powerrangers.util.*;
import com.powerrangers.db.DbContext;
import com.powerrangers.db.types.*;
public class RecommendationScreen implements ScreenBase
{

    @Override
    public void show(AppContext appContext) {
        Scanner scanner = appContext.getScanner();
        DbContext dbContext = appContext.getDbContext();
        System.out.println("¡Recomendaciones para tu primer compra!");                
        Menu menu = appContext.createMenu();
        String MenuSelection;

        System.out.println("Juegos Más vendidos: ");
        System.out.println("1.");
        System.out.println("2.");
        System.out.println("3.");
        System.out.println("4.");
        System.out.println("5.");
        System.out.println("");

        System.out.println("Juegos más populares: ");
        System.out.println("1.");
        System.out.println("2.");
        System.out.println("3.");
        System.out.println("4.");
        System.out.println("5.");

        System.out.println("Juegos más deseados: ");
        System.out.println("1.");
        System.out.println("2.");
        System.out.println("3");
        System.out.println("4.");
        System.out.println("5.");

        



        
        

        menu.AddItem("R", "Regresar al menu anterior"); 
        menu.AddItem("S", "Salir"); 
        MenuSelection = menu.show();                   
    }
    

}
