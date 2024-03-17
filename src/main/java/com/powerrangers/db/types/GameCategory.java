/*
 * Archivo: GameCategory.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que representa una categoría de juego.
 */

package com.powerrangers.db.types;

public class GameCategory 
{
    public GameCategory()
    { }

    public GameCategory(int _id, String _title, String _description)
    {
        id = _id;
        title = _title;
        description = _description;
    }

    public int id;

    public String title;

    public String description;
}
