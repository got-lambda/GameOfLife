(ns GameOfLife.test.core
  (:use [GameOfLife.core])
  (:use [clojure.test]))

(deftest count-neighbours-empty
  (is (= 0 (count-neighbours #{} [1 1]))))

(deftest count-neighbours-above
  (is (= 1 (count-neighbours #{[0 1]} [1 1]))))

(deftest count-neighbours-left-and-right
  (is (= 2 (count-neighbours #{[0 0] [0 2]} [0 1]))))

(deftest count-neighbours-left
  (is (= 1 (count-neighbours #{[0 0] [0 3]} [0 1]))))

(deftest neighbour-coordinates-test
  (is (= #{[0 0][0 1][0 2][1 0][1 2][2 0][2 1][2 2]} (neighbour-coordinates [1 1]))))

(deftest evolve-empty-world
  (is (= #{} (evolve #{}))))

(deftest evolve-lonely-dies
  (is (= #{} (evolve #{[0 0]}))))

(deftest live-cell-with-two-neighbours-lives-in-next-generation
  (is (= [0 1] (evolve-single #{[0 0][0 1][0 2]} [0 1]))))

(deftest dead-cell-with-two-neighbours-stays-dead-in-next-generation
  (is (= nil (evolve-single #{[0 0][0 2]} [0 1]))))

(deftest live-cell-with-three-neighbours-lives-in-next-generation
  (is (= [0 1] (evolve-single #{[0 0][0 1][0 2][1 0]} [0 1]))))

(deftest dead-cell-with-three-neighbours-lives-in-next-generation
  (is (= [0 1] (evolve-single #{[0 0][0 2][1 0]} [0 1]))))

(deftest live-cell-with-more-than-three-neighbours-dies-in-next-generation
  (is (= nil (evolve-single #{[0 0][0 1][0 2][1 0][1 1]} [0 1]))))

(deftest evolve-single-cell
  (is (= nil (evolve-single #{} [0 0]))))

(deftest evolve-square
  (is (= #{[0 0][0 1][1 0][1 1]} (evolve #{[0 0][0 1][1 0][1 1]}))))

(deftest evolve-tri
  (is (= #{[1 0][1 1][1 2]} (evolve #{[0 1][1 1][2 1]}))))

(deftest evolve-tri-three-times
  (is (= #{[1 0][1 1][1 2]} (evolve (evolve (evolve #{[0 1][1 1][2 1]}))))))

(deftest evolve-tri-three-times-advanced
  (is (= [#{[0 1][1 1][2 1]}
	  #{[1 0][1 1][1 2]}
	  #{[0 1][1 1][2 1]}]
	   (take 3 (iterate evolve #{[0 1][1 1][2 1]})))))