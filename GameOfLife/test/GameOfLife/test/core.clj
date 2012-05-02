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