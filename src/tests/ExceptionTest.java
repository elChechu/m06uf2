/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

/**
 *
 * @author julian
 */
public class ExceptionTest {
    
    public static void main(String[] args) {
        
        System.out.println("=============senseTry(false)==============");
        try {
            senseTry(false);
        } catch (Exception ex) {
            System.out.println("ha fallat el senseTry(false)");
        }
        
        System.out.println("=============senseTry(true)==============");
        try {
            senseTry(true);
        } catch (Exception ex) {
            System.out.println("ha fallat el senseTry(true)");
        }
        
        System.out.println("=============ambTry(false)==============");
        try {
            ambTry(false);
        } catch (Exception ex) {
            System.out.println("ha fallat el ambTry(false)");
        }
        
        System.out.println("=============ambTry(true)==============");
        try {
            ambTry(true);
        } catch (Exception ex) {
            System.out.println("ha fallat el ambTry(true)");
        }
        
        System.out.println("=============ambFinally(false)==============");
        try {
            ambFinally(false);
        } catch (Exception ex) {
            System.out.println("ha fallat el ambFinally(false)");
        }
        
        System.out.println("=============ambFinally(true)==============");
        try {
            ambFinally(true);
        } catch (Exception ex) {
            System.out.println("ha fallat el ambFinally(true)");
        }
    }
    
    public static void senseTry(boolean error) throws Exception {
        
        System.out.println("inici");
        if (error) 
            throw new Exception("error al mig!");
        System.out.println("final");
    }
    
    public static void ambTry(boolean error) throws Exception {
        
        try {
            System.out.println("inici");
            if (error) 
                throw new Exception("error al mig!");
            System.out.println("final");
            
        } catch (Exception e) {
            System.out.println("catched: " + e.getMessage());
            System.out.println("final alternatiu");
        }
    }
           
    public static void ambFinally(boolean error) throws Exception {
        
        try {
            System.out.println("inici");
            if (error) 
                throw new Exception("error al mig!");
            System.out.println("final");
            
        } finally {
            System.out.println("final alternatiu");
        }
    }
}
