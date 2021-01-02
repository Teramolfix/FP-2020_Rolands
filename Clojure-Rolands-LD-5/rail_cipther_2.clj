(ns LD4.rail-cipther-2)

;generates ints in a range [a, b]
(defn my-range [a b]
  (if (= a b)
    (cons a nil)
    (cons a (my-range (+ a 1) b))
    )
  )

;a triangle shape function, like the y = |x|, but translated around for this use
;generates ints in range [0, key] then [key, 0] periodically, while x goes up
(defn tri-func [x key]
  (+ key (if (> (- x key) 0)
            (* -1 (- x key))
            (- x key)
          )
     )
  )

;a more functional implementation of the rail cipher
(defn encrypt [message key]
  (apply str
         (map (fn [x] (nth message (first x)))
                  (sort-by last (map (fn [i] [i, (tri-func (rem i (- (* 2 key) 2)) (- key 1))]) ;(- (* 2 key) 2) is the cycle and might be a wrong assumption
                      (seq (my-range 0 (dec (count message))))
                      )
               )
           )
         )
  )

;(encrypt "WEAREDISCOVEREDFLEEATONCE" 3)
(= (encrypt "WEAREDISCOVEREDFLEEATONCE" 3) "WECRLTEERDSOEEFEAOCAIVDEN")

;(encrypt "WE ARE DISCOVERED FLEE AT ONCE" 3)
(= (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 3) "WRIVDETCEAEDSOEE_LEA_NE__CRF_O")

;(encrypt "WEAREDISCOVEREDFLEEATONCEYAB" 3)
(= (encrypt "WEAREDISCOVEREDFLEEATONCEYAB" 3) "WECRLTEERDSOEEFEAOCYBAIVDENA")

;(encrypt "PACKMYBOXWITHADOZENLIQUORJUGS" 4)
(= (encrypt "PACKMYBOXWITHADOZENLIQUORJUGS" 4) "PBHNRAYOTAELOJCMXIDZIUUSKWOQG")

;(encrypt "abcdefghijklm" 6)
(= (encrypt "abcdefghijklm" 6) "akbjlcimdhegf")


(defn decrypt [message key]
  ;could not think of a better solution than last  
  )