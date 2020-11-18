(ns basic-uzd.core)

;alt shift p to run

;; 1. Find the last element of a list.
(defn my-last [xs]
  (if (= nil (next xs))
    (first xs)
    (my-last (next xs))
    )
  )

;; 2. Find the N-th element of a list.
(defn get-nth [xs n]
  (if (= n 1)
    (first xs)
    (get-nth (next xs) (dec n))
    )
  )


;; 3. Find the length of a list
(defn my-length [xs]
  (if (= nil xs)
    0
    (inc (my-length (next xs)))
    )
  )


;; 4. Reverse a list.
(defn my-reverse [xs]
  (if (= nil (next xs))
    xs
    (conj (my-reverse (next xs)) (first xs))
    )
  )


;; 5. Find out whether a list is a palindrome.
(defn is-palindrome? [xs]
  (= (seq (str num)) (clojure.string/reverse (str num)))
  )

(is-palindrome? [1 2 3 2 1])

;; 6. Duplicate the elements of a list.
(defn duplicate [xs]
  (concat xs xs))

(duplicate [1 2 3])

;; 7. Eliminate consecutive duplicates of a list.
(defn compress [xs]
  (if (= nil (next (next xs)))
    xs
    (conj (first xs) (compress (next (next xs))))
    ))

(compress [1 1 2 2 3 3])

;; 8. Remove the N-th element of a list
(defn remove-at [xs n]
  (if (= (next xs) nil)
    xs
    (if (= 2 n)
      ;skip
      (conj (remove-at (next (next xs)) (dec n)) (first xs))
      (conj (remove-at (next xs) (dec n)) (first xs))
      )
    )
  )

(remove-at [1 2 3 4 5] 2)


;; 9. Insert a new element at the N-th position of a list.
(defn insert-at [x xs n]
  nil)


;; 10. Create a list containing all integers within a given range.
(defn my-range [a b]
  (if (not (= a b))
      (conj (my-range (inc a) b) a)
      [a]
    )
  )

(my-range 1 10)

;; 11. Concatenate two lists
(defn my-concat [xs ys]
  (if (= nil (next xs))
    [xs]
    (if (= nil (next ys))
      [ys]
      (conj (my-concat (next xs) ys) (my-concat xs (next ys)))
      )
    )
  )

(my-concat [1 2 3 4] [5 6 7 8])

;; 12. Split a list into two parts; the length of the first part is given.
(defn my-drop [xs n]
  nil)


;; 13. Split a list into two parts; the length of the first part is given.
(defn my-take [xs n]
  nil)


;; 14. Implement the filtering function
(defn my-filter [p xs]
  (cond
    (empty? xs) nil
    (p (first xs)) (cons (first xs) (my-filter p (next xs)))
    :else (my-filter p (next xs))
    )
  )

(my-filter odd? [1 2 3 4 5 6])

;; 15. Implement the mapping function
(defn my-map [f xs]
  (when (seq xs)
    (cons (f (first xs)) (my-map f (next xs)))
    )
  )

(my-map inc [1 2 3 4 5 6])

;; 16. Implement the reducing function
(defn my-reduce [f acc xs]
  (if (seq xs)
    (f (first xs) (my-reduce f acc (next xs)))
    acc
    )
  )

(my-reduce + 0 [1 2 3 4 5 6])

;; 17. Implement the flattening function
(defn my-flatten [xs]
  nil)
