
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

(defn findTreasure [i j]
    ;;travelled current node
    (aset ar 0 0 '+)

    ; first if
    (if (> i 0);go up
        (do
            (if  (> i 0)
                (do
                        ; return true
                        (println 'true)    
                )
            )
                
        )
    )

);method close

(def i (atom 1))
(def j (atom 0))
(println (aget ar 0 0))
(println (aget ar 0 1))
(findTreasure @i @j)