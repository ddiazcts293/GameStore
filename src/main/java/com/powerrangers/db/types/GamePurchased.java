/*
 * Archivo: GamePurchased.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que representa un juego comprado.
 */

package com.powerrangers.db.types;

import java.time.*;

public class GamePurchased 
{
    public int id;

    public Customer customer;

    public Game game;

    public float pricePaid;

    public LocalDate date;
}
