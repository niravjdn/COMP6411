
(ns clojure.nirav.treasure
    (:gen-class))

(require ['clojure.string :as 'str])
(use 'clojure.java.io)
; (use 'clojure.string')

; read file
(defn get-lines [file]
    (str/split-lines (slurp file)))

(def lines '(1 2 3))
; (println    lines)
; (def lines-from-file (slurp "map.txt"))
; (println lines-from-file)
(def lines (get-lines "map.txt"))

; print start up screen
(println "This is my challenge: \n")
(doseq [item lines]
    (println item))


(def ar (to-array-2d lines))
(def rows (alength ar))
(println rows)
(def cols (alength (aget ar 0)))
(println cols)



;; function to iterate 

; (defn findTreasure [i j]
;        (when (< i 5)
;        (println i)
;        (recur  (inc i) (inc j) ))
;        );when close
;        )
(def rt false)
(defn findTreasure [i j]
    ;;travelled current node
    (aset ar i j '+)
    ; (println (aget ar (inc i) 0))
    ; (println (type (aget ar 0 0)))
    ; (println (type '-'))
    ; (println (= (str (aget ar (inc i) 0 )) (str "-")))
    
    ; first if
    (if (> i 0);go up
        (do
            (if  (= (str (aget ar (dec i) j )) (str "@"))
                (do
                        ; return true
                        (println "a")
                        (true? (= 1 1))   
                )
            )

            (if  (= (str (aget ar (dec i) j )) (str "-"))
                (do
                    (if  (= (findTreasure (dec i) j)  true)
                        (do
                                ; return true
                                (println "a")
                                (true? (= 1 1))   
                        )

                        (do
                            (aset ar (dec i) j '!)
                        )                

                     ) 
                )
            )
        )
    )

    (println "2")
    (if (< j 12);go right
        (do
            (if  (= (str (aget ar  i  (inc j) )) (str "@"))
                (do
                        ; return true
                        (println "a")
                        (true? (= 1 1))   
                )
            )

            (if  (= (str (aget ar i (inc j) )) (str "-"))
                (do
                    
                    (if  (= (findTreasure  i (inc j))  true)
                        (do
                                ; return true
                                (println "a")
                                (true? (= 1 1))   
                        )

                        (do
                            (aset ar i (inc j) '!)
                        )                

                     ) 
                )
            )
        )
    )

    (println "3")
    (if (< i 7);go down
        (do
            (if  (= (str (aget ar (inc i) j )) (str "@"))
                (do
                        ; return true
                        (println "a")
                        (true? (= 1 1))   
                )
            )

            (if  (= (str (aget ar (inc i) j )) (str "-"))
                (do
                    (if  (= (findTreasure (inc i) j)  true)
                        (do
                                ; return true
                                (println "a")
                                (true? (= 1 1))   
                        )

                        (do
                            (aset ar (inc i) j '!)
                        )                

                     ) 
                )
            )
        )
    )

    (println "4")
    (if (> j 0);go left
        (do
            (if  (= (str (aget ar i (dec j) )) (str "@"))
                (do
                        ; return true
                        (println "a")
                        (true? (= 1 1))   
                )
            )

            (if  (= (str (aget ar i (dec j) )) (str "-"))
                (do
                    (if  (= (findTreasure  i (dec j))  true)
                        (do
                                ; return true
                                (println "a")
                                (true? (= 1 1))   
                        )

                        (do
                            (aset ar i (dec j) '!)
                        )                

                     ) 
                )
            )
        )
    )

    

);method close

(def i (atom 0))
(def j (atom 0))
; (println (aget ar 0 0))
; (println (aget ar 0 1))
(println (findTreasure @i @j))

(println (aget ar 0 0))
(println (aget ar 0 1))
(println (aget ar 0 2))

