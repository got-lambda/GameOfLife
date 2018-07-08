(ns GameOfLife.core
  (:use quil.core)
  (:gen-class))

(defn neighbour-coordinates [[x y]]
  (for [i [-1 0 1] j [-1 0 1] :when (not (= 0 i j))]
    [(+ x i) (+ y j)]))

(defn count-neighbours
  [world pos]
  (let [coords (neighbour-coordinates pos)]
    (count (filter world coords))))

(defn evolve-single [world pos]
  (let [c (count-neighbours world pos)]
    (cond (and (= 2 c) (world pos)) pos ; has two neighbours and exists in the world
	  (= 3 c) pos
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
  (reset! world (get-random-world 50000 500 500)))

(defn draw []
  (background 0)
  (fill 255)
  (let [zoom (/ (mouse-x) 100)
	x-pan (/ (width) 2)
	y-pan (/ (height) 2)]
    (doseq [[x y] @world]
      (rect (+ (* x zoom) x-pan) (+ (* y zoom) y-pan) zoom zoom)))
  (swap! world evolve))

(defn -main []
  (defsketch game-of-life
    :title "Game of Life"
    :setup setup
    :draw draw
    :size [600 400])
  (println "Starting..."))
