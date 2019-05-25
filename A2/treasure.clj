
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

(def row-length (dec rows))
(def col-length (dec cols))


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
    ; (println "going - " i j )
    ; (println (aget ar (inc i) 0))
    ; (println (type (aget ar 0 0)))
    ; (println (type '-'))
    ; (println (= (str (aget ar (inc i) 0 )) (str "-")))
    
    ; first if
    ; (println "3")
    (if (and (< i row-length) (not= rt true));go up
        ;go down
        (do
            (if  (= (str (aget ar (inc i) j )) (str "@"))
                (do
                        ; return true
                        ; (println "1Returning true1")
                        (def rt true) 
                )
            )

            (if  (= (str (aget ar (inc i) j )) (str "-"))
                (do
                    (findTreasure (inc i) j) 
                    (if  (= rt true)
                        (do
                                ; return true
                                ; (println "Returning true")
                                (def rt true)  
                        )

                        (do
;;                            (println "setting !" (inc i) j)
                            (aset ar (inc i) j '!)
                        )                

                     ) 
                )
            )
        )
    )

    (if (and (> i 0) (not= rt true));go up
        (do
            (if  (= (str (aget ar (dec i) j )) (str "@"))
                (do
                        ; return true
                        ; (println "1Returning true1")
                        (def rt true)
                )
            )

            (if  (= (str (aget ar (dec i) j )) (str "-"))
                (do
                    (findTreasure (dec i) j)
                    (if  (= rt  true)
                        (do
                                ; return true
                                ; (println "Returning true")
                                (def rt true)   
                        )

                        (do
;                          (println "setting !" (inc i) j)
                            (aset ar (dec i) j '!)
                        )                

                     ) 
                )
            )
        )
    )

    ; (println "2")
    (if (and (< j col-length) (not= rt true));go right
        (do
            (if  (= (str (aget ar  i  (inc j) )) (str "@"))
                (do
                        ; return true
                        ; (println "1Returning true1")
                        (def rt true)
                )
            )

            (if  (= (str (aget ar i (inc j) )) (str "-"))
                (do
                    (findTreasure  i (inc j))
                    (if  (=  rt true)
                        (do
                                ; return true
                                ; (println "Returning true")
                                (def rt true) 
                        )

                        (do
;;                            (println "setting !" (inc i) j)
                            (aset ar i (inc j) '!)
                        )                

                     ) 
                )
            )
        )
    )

    

    ; (println "4")
    (if (and (> j 0) (not= rt true));go right
        ;go left
        (do
            (if  (= (str (aget ar i (dec j) )) (str "@"))
                (do
                        ; return true
                        ; (println "1Returning true1")
                        (def rt true)  
                )
            )

            (if  (= (str (aget ar i (dec j) )) (str "-"))
                (do
                    (findTreasure  i (dec j)) 
                    (if  (= rt true)
                        (do
                                ; return true
                                ; (println "Returning true")
                                (def rt true) 
                        )

                        (do
;;                            (println "setting !" (inc i) j)
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
(findTreasure @i @j)

; (println (aget ar 0 0))
; (println (aget ar 0 1))
; (println (aget ar 0 3))
; (println (aget ar 1 2))

(println "")
(if (= rt true) 
    (println "Woo hoo, I found the treasure :-)")
    (println "Uh oh, I could not find the treasure :-(")
)
(println "")
;print
(loop [x 0]
    (when (< x rows)
       
        (loop [y 0]
            (when (< y cols)
                (if (and (= (str (aget ar x y )) (str "+")) (= rt false))
                 (aset ar x y "!")
                )
                (print (aget ar x y))
                (recur (+ y 1))
            )    
        )
        (println "")     
       (recur (+ x 1)))) 

; REFERENCES
; https://stackoverflow.com/questions/34425145/clojure-initialize-2d-vector
; https://stackoverflow.com/questions/9047231/read-a-file-into-a-list-each-element-represents-one-line-of-the-file
; https://stackoverflow.com/questions/52446937/clojure-multiple-tasks-in-if
        
