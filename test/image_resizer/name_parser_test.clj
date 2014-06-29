(ns image-resizer.name-parser-test
  (:require [clojure.test :refer :all]
            [image-resizer.name-parser :refer :all]))

(deftest parser-assigns-correct-ids
  (are [name result] (= result (:id (parse-car-name name)))
       "Hupmobile A 1928.jpg" 0
       "Hupmobile A 1928 (1).jpg" 1
       "Hupmobile A 1928 001.jpg" 1
       "lada 1986.jpg" 0))


(deftest parser-assigns-correct-names
  (are [name result] (= result (:name (parse-car-name name)))
       "Hupmobile A 1928.jpg" "Hupmobile A 1928"
       "Hupmobile A 1928 (1).jpg" "Hupmobile A 1928"
       "Hupmobile A 1928 001.jpg" "Hupmobile A 1928"
       "lada 1986.jpg" "Lada 1986"))
