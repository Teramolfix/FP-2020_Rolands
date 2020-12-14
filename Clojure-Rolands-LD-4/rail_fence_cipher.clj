(ns LD4.rail-fence-cipher)

;*********************************************
;Rail-fence cipher by Rolands Laucis 181rdb204
;*********************************************

;because clojures interleave makes both seq the same lenght, if they are not,
;because it makes a map of key value pairs.
;This fixes that.
(defn my-interleave [a b]
  (if (= (count a) (count b))
    (interleave a b)
    (cons (first a) (interleave b (rest a)))                ;adds in the odd number element
    )
  )

;each fence row has the encoded letters. This returns
(defn read-row-letters [message cycle rowStartOffset scndOffset]
  (if (or (= rowStartOffset 0) (= rowStartOffset scndOffset))
    ;true. Do this for either first or last row
    (take-nth cycle (subs message rowStartOffset))
    ;false
    (my-interleave (take-nth cycle (subs message rowStartOffset))
                   (take-nth cycle (subs message scndOffset))
                )
    )
  )

;so the rail-fence-cipher simplified gets encrypted by
;constructing a new message of the letters it gets by leaping every [cycle] amount of letters
;through the original message. Basically, if the cycle is 4, then take every 4th letter and put them in a row
;the cycle is calculated by = ([# of rails] x 2) - 2, where rails is the amount of rails aka key
(defn encrypt [message key]
  (loop [i 0 newstr nil row key offset (- (* key 2) 2) origCycle (- (* key 2) 2)]
    (if (< i key)
      (recur (+ i 1)                                        ;for number of fence rows, go row by row getting the letters
             (concat newstr (read-row-letters (clojure.string/replace message " " "_") ;replace spaces with _
                                             origCycle
                                             (- key row)
                                             offset)
                                              )             ;concatinate all the row letter segments
             (- row 1)
             (- offset 1)
             origCycle
             )
      (apply str newstr)                                    ;turn the seq into a string
      )
    )
  )

;(encrypt "WEAREDISCOVEREDFLEEATONCE" 3)
;(= (encrypt "WEAREDISCOVEREDFLEEATONCE" 3) "WECRLTEERDSOEEFEAOCAIVDEN")
;
;(encrypt "WE ARE DISCOVERED FLEE AT ONCE" 3)
;(= (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 3) "WRIVDETCEAEDSOEE_LEA_NE__CRF_O")
;
;(encrypt "WEAREDISCOVEREDFLEEATONCEYAB" 3)
;(= (encrypt "WEAREDISCOVEREDFLEEATONCEYAB" 3) "WECRLTEERDSOEEFEAOCYBAIVDENA")
;
;(encrypt "PACKMYBOXWITHADOZENLIQUORJUGS" 4)
;(= (encrypt "PACKMYBOXWITHADOZENLIQUORJUGS" 4) "PBHNRAYOTAELOJCMXIDZIUUSKWOQG")
;
;(encrypt "abcdefghijklm" 6)
;(= (encrypt "abcdefghijklm" 6) "akbjlcimdhegf")

;                             //DECRYPTION//

;i found my own way to mathematically leap through the message letters
;to find the letters in correct order based on the patterns i found (in the txt)

;e.g. this pattern:
;1.....1.....1.....1
;.2...2.2...2.2...2
;..3.3...3.3...3.3
;...4.....4.....4

;19 letters, key = 4
;19/4 = 6 + 1
;top 4
;mid 6
;mid 6
;bot 3
;
;index 0 is the first letter in the original message, then jumping 4 letters over is the next letter, then 6 more letters jump...->
;
;0 4 6 6 14 13 15 5 6 5 15 13 14 6 6 4 16 13 13 7 6 3 17 13 12 ...
;
;4 6 6 14 13 15
;5 6 5 15 13 14
;6 6 4 16 13 13
;7 6 3 17 13 12

;so the 1st number is always the amount of letters in the top layer = 4
;then for as many as there are mid layers there are columns with value of mid layer = 6 6
;then the next one is the bot layer column, but its value is all mid layers + 2 = 6+6+2 = 14
;then for as many as there are mid layers - 1 there are columns with value of mid layer sum + 1 = 6+6+1 = 13
;then the last one is value of mid layer sum + bot layer = 6+6+3 = 15
;
;i did a bunch of experimenting by hand (see txt) and this seems to work for any key and length of message
;
;all mid layers, except the last consecutive mid layer, are static and dont change over rows, like 6 and 13
;
;and the last bit of logic is that the column numbers change by value of 1 up or down alternating,
;always starting from the first column incrementing and skipping this logic for any static column.
;This is show by the rows.
;The column numbers represent the amount of letters to jump forward to get the next letter in the decrypted message.
;
;You can accumulate the values of all numbers before it to get the absolute index of the letter in the encrypted message.
;The accumulated values will quickly overflow the array bounds, so a period remover function is applied to every accumulated number.

(defn period-remover [msg val]
  (def msgLength (count msg))
  (* (- (/ val msgLength) (int (Math/floor (/ val msgLength)))) msgLength)
  )

(defn accumulate-all-before [xs n]
  (loop [i (- n 1) sum 0]
    (if (>= i 0)
      (recur (- i 1) (+ sum (int (nth xs i))))
      sum
      )
    )
  )

(defn make-array-of [val len]
  (if (<= len 0)
    []
   (if (> len 1)
    (vec (cons val (make-array-of val (- len 1))))
    [val]
    )
   )
  )

(defn apply-remainders [remainder row]
  (loop [x remainder workingRow row]
      (if (> x 0)
        (recur (- x 1) (assoc workingRow (int (- x 1)) (+ (nth workingRow (- x 1)) 1)))
        workingRow
        )
    )
  )

(defn gen-first-row [letter-count row-count]
  (def my-cycle (- (* row-count 2) 2))
  (def top (Math/floor (/ letter-count my-cycle)))
  (def mid (* (Math/floor (/ letter-count my-cycle)) 2))
  (def bot (Math/floor (/ letter-count my-cycle)))
  (def remainder (Math/ceil (* (- (/ letter-count my-cycle) (Math/floor (/ letter-count my-cycle))) row-count)))
  (def mids (make-array-of mid (- row-count 2)))
  (def otherMids (keep-indexed #(if (not= %1 0) %2) mids))
  (map inc otherMids)
  (def sumMids (reduce + mids))
  (def row (concat [top] mids [(+ sumMids 2)] otherMids [(+ sumMids bot)]))
  (def row (apply-remainders remainder (vec row)))
  ;(def mapped-row (map #(hash-map % %) row))
  ;row
  (def directions (concat
                    ["up"]
                    (vec (make-array-of "static" (- (count mids) 1)))
                    ["down", "up"]
                    (vec (make-array-of "static" (- (count mids) 1)))
                    ["down"]
                    ))
  (def mapped-row (interleave row directions))
  ;(println mapped-row)
  mapped-row
  )

(defn gen-next-row [current-row]
  (def directions (take-nth 2 (rest current-row)))
  (loop [i 0 new-row nil]
    (if (<= (+ i 2) (count current-row))
      (recur (+ i 2) (conj new-row (cond (= (nth current-row (+ i 1)) "up") (inc (nth current-row i))
                                         (= (nth current-row (+ i 1)) "static") (nth current-row i)
                                         (= (nth current-row (+ i 1)) "down") (dec (nth current-row i))
                                         )
                           )
             )
      (interleave (reverse new-row) directions)
      )
    )
  )

(defn decrypt [message key]
  (def first-row (gen-first-row (count message) key))
  (def all-rows (loop [i (Math/ceil (/ (count message) (/ (count first-row) 2))) letter-jumps-seq nil last-row first-row]
    (if (> i 0)
      (recur (- i 1) (concat letter-jumps-seq last-row) (gen-next-row last-row))
      (take-nth 2 letter-jumps-seq)
      )
    ))

  (def all-rows-letter-index (loop [i 0 final-idexes nil]
                              (if (< i (count all-rows))
                                (recur (+ i 1) (conj final-idexes (period-remover message (accumulate-all-before all-rows i))))
                                (reverse final-idexes)
                                )
                              ))
  (loop [i 0 decrypted-letters nil]
    (if (< i (count message))
      (recur (+ i 1) (conj decrypted-letters (nth message (nth all-rows-letter-index i))))
      (apply str (reverse decrypted-letters))
      )
    )
  )

(decrypt "WECRLTEERDSOEEFEAOCAIVDEN" 3)
(= (decrypt "WECRLTEERDSOEEFEAOCAIVDEN" 3) "WEAREDISCOVEREDFLEEATONCE")

;(decrypt "11111222222223333333344444444555555556666" 6)
;(= (decrypt "11111222222223333333344444444555555556666" 6) "12345654321234565432123456543212345654321")