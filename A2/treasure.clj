
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

    ; first if
    (if (> i 0);go up
        (do
            (if  (= (aget ar (dec i) j)  "@")
                (do
                        ; return true
                        (println "a")
                        (true? (= 1 1))   
                )
            )

            (if  (= (aget ar (dec i) j)  "-")
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

    (if (< j 12);go down
        (do
            (if  (= (aget ar i (inc j))  "@")
                (do
                        ; return true
                        (println "a")
                        (true? (= 1 1))   
                )
            )

            (if  (= (aget ar i (inc j))  "-")
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

);method close

(def i (atom 0))
(def j (atom 0))
(println (aget ar 0 0))
(println (aget ar 0 1))
(println (findTreasure @i @j))
(println (aget ar 0 0))
(println (aget ar 0 1))
(println (aget ar 0 2))

