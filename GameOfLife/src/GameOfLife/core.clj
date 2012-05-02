(ns GameOfLife.core
  (:use quil.core)
  (:gen-class))

(defn neighbour-coordinates [[x y]]
  (set (for [i [-1 0 1] j [-1 0 1] :when (not (= 0 i j))]
	 [(+ x i) (+ y j)])))

(defn alive? [world [x y]]
  (world [x y]))

(defn count-neighbours
  [world [x y]]
  (let [coords (neighbour-coordinates [x y])]
    (count (filter world coords))))

(defn evolve-single [world [x y]]
  (let [c (count-neighbours world [x y])]
    (cond (and (= 2 c) (world [x y])) [x y] ; has two neighbours and exists in the world
	  (= 3 c) [x y]
	  :else nil)))

(defn evolve [world]
  (let [all-cells-to-test (into world (mapcat neighbour-coordinates world))]
    (set (filter #(evolve-single world %) all-cells-to-test))))

(def world (atom #{[10 10][10 11][10 12]}))

(defn setup []
  (frame-rate 1))

(defn draw []
  (background 0)
  (fill 255)
  (doseq [[x y] @world]
    (rect (* x 10) (* y 10) 10 10))
  (swap! world evolve))

(defsketch game-of-life                
	:title "Game of Life"
	:setup setup
	:draw draw         
	:size [600 400])

(defn -main []
  (println "Starting..."))
