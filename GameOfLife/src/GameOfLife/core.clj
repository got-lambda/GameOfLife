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

(defn get-random-world [nr-of-cells w h]
  (set (take nr-of-cells (repeatedly (fn [] [(- (rand-int w) (/ w 2))
					     (- (rand-int h) (/ h 2))])))))

(def world (atom #{}))

(defn setup []
  (frame-rate 5)
  (reset! world (get-random-world 1200 60 40)))

(defn draw []
  (background 0)
  (fill 255)
  (let [zoom (/ (mouse-x) 100)
	x-pan (/ (width) 2)
	y-pan (/ (height) 2)]
    (doseq [[x y] @world]
      (rect (+ (* x zoom) x-pan) (+ (* y zoom) y-pan) zoom zoom)))
  (swap! world evolve))

(defsketch game-of-life                
	:title "Game of Life"
	:setup setup
	:draw draw         
	:size [600 400])

(defn -main []
  (println "Starting..."))
