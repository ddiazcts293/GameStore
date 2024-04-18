package com.powerrangers.db.types;

public class GameInShoppingCar 
{
    public GameInShoppingCar()
    { }
    
    public GameInShoppingCar(
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
}
