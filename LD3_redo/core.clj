(ns LD3_redo.core)

;alt shift p to run

;; 1. Find the last element of a list.
(defn my-last [xs]
  (if (= nil (next xs))
    (first xs)
    (my-last (next xs))
    )
  )

(assert (= :d
           (my-last '(:a :b :c :d))))

;; 2. Find the N-th element of a list.
(defn get-nth [xs n]
  (if (= n 0)
    (first xs)
    (get-nth (next xs) (dec n))
    )
  )

(assert (= :c
           (get-nth '(:a :b :c :d) 2)))

;; 3. Find the length of a list
(defn my-length [xs]
  (if (= nil xs)
    0
    (inc (my-length (next xs)))
    )
  )

(assert (= 4
           (my-length '(:a :b :c :d))))

;; 4. Reverse a list.
(defn my-reverse [xs]
  (if (empty? xs)
    xs
    (cons (my-last xs) (my-reverse (butlast xs)))
    )
  )

(assert (= '(:d :c :b :a)
           (my-reverse '(:a :b :c :d))))

;; 5. Find out whether a list is a palindrome.
(defn is-palindrome? [xs]
  (if (= (first xs) (my-last xs))
    true
    false
    )
  )

(assert (= true
           (is-palindrome? '(:a :b :c :b :a))))

;; 6. Duplicate the elements of a list.
(defn duplicate [xs]
  (if (= nil (next xs))
    (seq [(first xs) (first xs)])
    (cons (first xs) (cons (first xs) (duplicate (next xs)))))
  )

(assert (= '(:a :a :b :b :c :c)
           (duplicate '(:a :b :c))))

;; 7. Eliminate consecutive duplicates of a list.
(defn compress [xs]
  (if (= nil (next (next xs)))
    (seq [(first xs)])
    (cons (first xs) (compress (next (next xs))))
    )
  )

(assert (= '(:a :b :c)
           (compress '(:a :a :b :b :c :c))))

;; 8. Remove the N-th element of a list
(defn remove-at [xs n]
  (if (= nil (next xs))
    xs
    (if (= n 0)
      ;skip this
      (remove-at (next xs) (- n 1))
      ;build seq normal
      (cons (first xs) (remove-at (next xs) (- n 1)))
      )
    )
  )

(assert (= '(:a :b :d :e)
           (remove-at '(:a :b :c :d :e) 2)))

;; 9. Insert a new element at the N-th position of a list.
(defn insert-at [x xs n]
  (if (= nil (next xs))
    xs
    (if (= n 0)
      ;add here
      (cons x (cons (first xs) (insert-at x (next xs) (- n 1))))
      ;build seq normal
      (cons (first xs) (insert-at x (next xs) (- n 1)))
      )
    )
  )

(assert (= '(:a :b :x :c :d)
           (insert-at :x '(:a :b :c :d) 2)))

;; 10. Create a list containing all integers within a given range.
(defn my-range [a b]
  (if (= a b)
    (cons a nil)
    (cons a (my-range (+ a 1) b))
    )
  )

(assert (= '(3 4 5 6 7)
           (my-range 3 7)))

;; 11. Concatenate two lists
(defn my-concat [xs ys]
  (if (= nil xs)
    ys
    (cons (first xs) (my-concat (next xs) ys))
    )
  )

(assert (= '(:a :b :c :d :e :f)
           (my-concat '(:a :b :c) '(:d :e :f))))

;; 12. Split a list into two parts; the length of the first part is given.
(defn my-drop [xs n]
  (if (= n 1)
    (rest xs)
    (my-drop (next xs) (- n 1))
    )
  )

(assert (= '(:d :e)
           (my-drop '(:a :b :c :d :e) 3)))

;; 13. Split a list into two parts; the length of the first part is given.
(defn my-take [xs n]
  (if (<= n 0)
    nil
    (cons (first xs) (my-take (next xs) (dec n)))
    )
  )

(assert (= '(:a :b :c)
           (my-take '(:a :b :c :d :e) 3)))

;; 14. Implement the filtering function
(defn my-filter [p xs]
  (when (seq xs)
    (if (p (first xs))
      (cons (first xs) (my-filter p (next xs)))
      (my-filter p (next xs))
      )
    )
  )

(assert (= '(1 3 5)
           (my-filter odd? '(1 2 3 4 5))))

;; 15. Implement the mapping function
(defn my-map [f xs]
  (when (seq xs)
    (cons (f (first xs)) (my-map f (next xs)))
    )
  )

(assert (= '(2 3 4 5 6)
           (my-map inc '(1 2 3 4 5))))

;; 16. Implement the reducing function
(defn my-reduce [f acc xs]
  (if (= nil xs)
    acc
    (my-reduce f (f acc (first xs)) (next xs))
    )
  )

(assert (= 15
           (my-reduce + 0 '(1 2 3 4 5))))

;; 17. Implement the flattening function
(defn my-flatten [xs]
  (when (seq xs)
    (if (seq? (first xs))
      (my-concat (my-flatten (first xs)) (my-flatten (next xs)))
      (cons (first xs) (my-flatten (next xs)))
      )
    )
  )

(assert (= '(:a :b :c :d :e)
           (my-flatten '(:a (:b (:c :d) :e)))))