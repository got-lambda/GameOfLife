(ns GameOfLife.core)

(defn neighbour-coordinates [[x y]]
  (set (for [i [-1 0 1] j [-1 0 1] :when (not (= 0 i j))]
	 [(+ x i) (+ y j)])))

(defn alive? [world [x y]]
  (world [x y]))

(defn count-neighbours
  [world [x y]]
  (let [coords (neighbour-coordinates [x y])]
    (count (filter world coords))))

