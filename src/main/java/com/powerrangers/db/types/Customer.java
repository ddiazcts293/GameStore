/*
 * Archivo: Customer.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que contiene la información general de un cliente.
 */

package com.powerrangers.db.types;

public class Customer 
{
    // Constructor predeterminado
    public Customer()
    { 
        credentials = new CustomerCredentials();
    }

    // Constructor que recibe la información de un cliente
    public Customer(
        int _id,
        String _username,
        String _name)
    {
        id = _id;
        username = _username;
        name = _name;
        credentials = new CustomerCredentials();
    }

    // Identificador de cliente
    public int id;

    // Username o nickname del cliente
    public String username;

    // Nombre completo del cliente
    public String name;

    // Credenciales de acceso
    public CustomerCredentials credentials;
}
