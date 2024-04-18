package com.powerrangers.db.types;

import java.time.*;

public class GameInWishList 
{
    
    public GameInWishList()
    { }
    
    public GameInWishList(
        int _id, 
        Customer _customer, 
        Game _game)
    {
        id = _id;
        customer = _customer;
        game = _game;
    }

    public int id;

    public Customer customer;

    public Game game;

    public LocalDate date;
    
}
