/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Source;

/**
 *
 * @author Rolands Laucis
 */
public class Solution {
    
    public static void main(String [] args){
        System.out.println(palindrome("Sapalsarītadēdatīraslapas"));
    }
    
    //recursively goes to the middle of the string, 
    //each itteration dicards first and last char
    //once in the middle, checks if the chars are the same
    //going back outwards checks that first and last characters are the same
    //if they all are, then it is a palidrome and returns true
    static boolean palindrome(String s) {
        //convert to lowercase because, A and a are not the same character internally
        s = s.toLowerCase();
        
        if(s.length() > 2){//this is not the middle, so recurse deeper
            if(palindrome(s.substring(1, s.length() - 1)) == true){
                //the inner string is a palindrome, check if here first and last char are equal
                if(s.charAt(0) == s.charAt(s.length() - 1)){
                    return true;
                }else{
                    return false;
                }
            }else{//the inner string is not a palindrome, return false up the chain
                return false;
            }
        }else if(s.length() == 2){//this is the middle with 2 chars
            if(s.charAt(0) == s.charAt(1)){//check if they are the same
                return true;
            }else{
                return false;
            }
        }else{//this is the middle of the string, but only 1 char, so is true
            return true;
        }
    }
}
