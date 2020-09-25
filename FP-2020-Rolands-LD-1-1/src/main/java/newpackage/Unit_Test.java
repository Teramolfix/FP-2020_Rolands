/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import static newpackage.Perfect_Number.detect;

/**
 *
 * @author Rolands Laucis
 */
public class Unit_Test {
    //main
    public static void main(String [] args){
        
        //prints out all perfect numbers
        for(int i = 0; i <= 8128; i++){
            var calc = detect(i);
            if(calc == "perfect"){
                System.out.println(i);
            }
        }
    }
}
