(ns image-resizer.name-parser
  (:require [slugger.core :refer :all])
  (:use image-resizer.collection))

(def cars [{:id "lada 1986"
           :name "Lada 1986"}
           {:id "sanita 1984-1990"
            :name "Škoda 1203 - sanita"}
           {:id "Octavia policie kombi"
            :name "Škoda Octavia kombi - policie"}
           {:id "Skoda Oct policie 5 dv."
            :name "Škoda Octavia 5 dv. - policie"}])

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn has-id? [path]
  (let [c (nth path (- (count path) 7))]
  (or (= '\( c) (= '\0 c))))

(defn file-map [path offset id]
  (let [name (apply str (drop-last offset path))
        identifier (->slug name)]
    {:identifier identifier
     :name (find-name name cars)
     :id id
     :original_path path
     :path (str identifier "_" id ".jpg")}))

(defn parse-car-name [path]
  (let [name-length (count path)]
    (if (has-id? path)
      (file-map path 8 (parse-int (subs path (- name-length 7) (- name-length 4))))
      (file-map path 4 0))))


(parse-car-name "Hupmobile A 1928.jpg")
(parse-car-name "Hupmobile A 1928  USA (1).jpg")
(parse-car-name "Hupmobile A 1928  USA 001.jpg")
(parse-car-name "lada 1986.jpg")
