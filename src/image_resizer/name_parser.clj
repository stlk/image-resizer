(ns image-resizer.name-parser)

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn has-id? [path]
  (let [c (nth path (- (count path) 7))]
  (or (= '\( c) (= '\0 c))))

(defn file-map [path offset id]
  (let [name (apply str (drop-last offset path))]
    {:name name
       :id id
       :original_path path
       :path (str name "_" id ".jpg")}))

(defn parse-car-name [path]
  (let [name-length (count path)]
    (if (has-id? path)
      (file-map path 8 (parse-int (subs path (- name-length 7) (- name-length 4))))
      (file-map path 4 0))))


(parse-car-name "Hupmobile A 1928.jpg")
(parse-car-name "Hupmobile A 1928  USA (1).jpg")
(parse-car-name "Hupmobile A 1928  USA 001.jpg")