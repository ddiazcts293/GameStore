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
public class RecommendationScreen implements ScreenBase
{

    @Override
    public void show(AppContext appContext) {
        Scanner scanner = appContext.getScanner();
        System.out.println("¡Recomendaciones para tu primer compra!");                
        Menu menu = appContext.createMenu();
        String MenuSelection;

        System.out.println("Juegos Más vendidos: ");
        System.out.println("1.God of war - G4");
        System.out.println("2.Portal 2 - G2");
        System.out.println("3.Half-life: alyx - G1");
        System.out.println("4.Half-life 2 - G0");
        System.out.println("5.Elden ring - G0");
        System.out.println("");
        System.out.println("Juegos más populares: ");
        System.out.println("1.God of war - G4");
        System.out.println("2.Red Dead redemption 2 - G4");
        System.out.println("3.Elden ring - G4");
        System.out.println("4.Portal 2 - G2");
        System.out.println("5.Persona 5 Royal - G0");
        System.out.println("");
        System.out.println("Juegos más deseados: ");
        System.out.println("1.God of war - G4");
        System.out.println("2.Half-life: Alyx - G1");
        System.out.println("3.Half-life 2 - G0");
        System.out.println("4.Elden Ring - G0");
        System.out.println("5.Baldur´s Ring - G0");
        System.out.println("");
        
        

        menu.AddItem("R", "Regresar al menu anterior"); 
        menu.AddItem("S", "Salir"); 
        MenuSelection = menu.show();                   
    }
    

}
