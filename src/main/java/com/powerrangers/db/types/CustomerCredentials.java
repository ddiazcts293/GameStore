/*
 * Archivo: CustomerCredentials.java
 * Autor: Díaz Cortés Daniel Salomón
 * Descripción: Clase que contiene las credenciales de acceso de un cliente.
 */

package com.powerrangers.db.types;

public class CustomerCredentials 
{
    // Constructor predeterminado
    public CustomerCredentials()
    { }

    // Constructor que recibe las credenciales del cliente
    public CustomerCredentials(
        int _id,
        String _email,
        String _password)
    {
        id = _id;
        email = _email;
        password = _password;
        customer = null;
    }
    
    // Identificador de la credencial
    public int id;

    // Correo electrónico de inicio de sesión
    public String email;

    // Contraseña
    public String password;

    // Infomación asociada al cliente
    public Customer customer;
}
