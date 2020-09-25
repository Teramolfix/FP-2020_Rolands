/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import static newpackage.Perfect_Number.detect;
/**
 *
 * @author Rolands Laucis
 */
public class Perfect_Number {
    //states of number
    public enum STATE{deficient, perfect, abundant}
    
    //main, if to run this class by itsself.
    public static void main(String [] args){
        System.out.println(detect(6));
    }
    
    //LD 1.1 Task method implementation: 
    public static String detect(int n){
        return process(n).name();
    }
    
    //helper function. Returns an Integer set of all of a numbers divisors
    public static Set<Integer> divisors(int n){
        Set<Integer> divs = new HashSet<Integer>();
        
        //formula for finding all divisors
        for (int i = 1; i<n; i++) 
            if (n % i == 0)
                divs.add(i);
        
        return divs;
    }
    
    //helper function. Returns STATE enum of detected number classification
    public static STATE process(int n){
        Set<Integer> divs = divisors(n);
        
        //sum up all divisors
        var sum = 0;
        Iterator<Integer> i = divs.iterator();
        while(i.hasNext()){
            sum += i.next();
        }
        
        //classify number based on sum
        if(sum == n){
            return STATE.perfect;
        }else if(sum > n){
            return STATE.abundant;
        }else{
            return STATE.deficient;
        }
    }
}
