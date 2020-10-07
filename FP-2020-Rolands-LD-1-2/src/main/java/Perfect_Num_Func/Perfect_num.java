/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Perfect_Num_Func;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;
/**
 *
 * @author Rolands
 */
public class Perfect_num {
    
    public static void main(String [] args){
        //int n = 6;
        
        /*List<Integer> divisors = IntStream.range(1,n).boxed().collect(Collectors.toList());
        Predicate<Integer> isDivisable = (i) -> n % i == 0;
        Integer sum = divisors.stream().filter(isDivisable)
            .collect(Collectors.summingInt(Integer::intValue));*/
        
        Function<Integer, String> isPerfect = (n) -> {
            List<Integer> divisors = IntStream.range(1,n).boxed().collect(Collectors.toList());
            Predicate<Integer> isDivisable = (i) -> n % i == 0;
            Integer sum = divisors.stream().filter(isDivisable)
                .collect(Collectors.summingInt(Integer::intValue));
            
            
            if(sum == n){return "perfect";}
            else if(sum > n){return "abundant";}
            else{return "deficient";}
        };
        
        System.out.println(isPerfect.apply(6));
    }
}
