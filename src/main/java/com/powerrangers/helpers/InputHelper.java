package com.powerrangers.helpers;

import java.util.Scanner;

public class InputHelper 
{
    Scanner _scanner;

    public InputHelper(Scanner scanner)
    {
        _scanner = scanner;
    }
    
    public int displayMenu(String prompt, String[] options)
    {
        int result = -1;

        for (int i = 0; i < options.length; i++)
        {
            System.out.printf("[%d] %s\n", i, options[i]);
        }

        System.out.println();

        do 
        {
            System.out.printf("%s > ", prompt);

            try 
            {
                result = _scanner.nextInt();
            } 
            catch (Exception e) 
            {
                // No hacer nada si se produce una excepcion
            }
            
            _scanner.nextLine();
        } 
        while (result < 0 || result >= options.length);

        return result;
    }

    public int requestInt(String prompt)
    {
        do 
        {
            System.out.printf("%s > ", prompt);
            
            try 
            {
                return _scanner.nextInt();
            }
            catch (Exception e) 
            {
                // No hacer nada si se produce una excepcion
            }
        }
        while (true);
    }
}
