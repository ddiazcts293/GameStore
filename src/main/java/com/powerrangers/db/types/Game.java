/*
 * Archivo: Game.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que contiene las propiedades de un videojuego.
 */

package com.powerrangers.db.types;

public class Game 
{
    // Constructor predeterminado
    public Game()
    { }

    // Constructor que recibe las propiedades del juego
    public Game(
        int _id,
        String _name,
        String _developer,
        int _year,
        float _price,
        String _description,
        GameCategory[] _categories)
    {
        id = _id;
        name = _name;
        developer = _developer;
        year = _year;
        price = _price;
        description = _description;
        categories = _categories;
    }

    public int id;

    public String name;
    
    public String developer;

    public int year;

    public float price;

    public float userRating;

    public String description;

    public GameCategory[] categories;
}
