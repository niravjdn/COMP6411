
(ns clojure.nirav.treasure
    (:gen-class))

(require ['clojure.string :as 'str])
(use 'clojure.java.io)

; read file
(defn get-lines [file]
    (str/split-lines (slurp file)))

(def lines '(1 2 3))
; (println    lines)
; (def lines-from-file (slurp "map.txt"))
; (println lines-from-file)
(def lines (get-lines "map.txt"))
(println (nth lines 4))








