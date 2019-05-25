
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

(def ar (to-array-2d lines))
(def rows (alength ar))
(println rows)
(def cols (alength (aget ar 0)))
(println cols)

;; function to iterate 

(defn findTreasure [i j]
    (loop [i 0]
       (when (< i 5)
       (println i)
       (recur  ( (inc i) (inc j)))
       )
       ))
(def i 0)
(def j 0)
(aset ar 0 0 '5)
(println (aget ar 0 0))
(println (aget ar 0 1))
(findTreasure i j)








