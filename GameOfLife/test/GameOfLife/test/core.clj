(ns GameOfLife.test.core
  (:use [GameOfLife.core])
  (:use [clojure.test]))

(deftest count-neighbours
  (is (= 0 (neighbours #{} [1 1]))))
