(ns image-resizer.core
  (:import java.io.File))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn has-id? [path]
  (let [c (nth path (- (count path) 7))]
  (or (= '\( c) (= '\0 c))))

(defn parse-car-name [path]
  (let [name-length (count path)
        extract-name #(apply str (drop-last % path))]
  (if (has-id? path)
    {:name (extract-name 8)
     :id (parse-int (subs path (- name-length 7) (- name-length 4)))}
    {:name (extract-name 4)
     :id 0})))


(parse-car-name "Hupmobile A 1928.jpg")
(parse-car-name "Hupmobile A 1928  USA (1).jpg")
(parse-car-name "Hupmobile A 1928  USA 001.jpg")

(defn my-ls [d f]
    (println d " - " (.getName f) " - " (parse-car-name (.getName f))))


(defn list-files [d]
  (.listFiles d))


(defn list-directories [d]
    (filter #(.isDirectory %) (.listFiles d)))


(defn list-images [directory]
  (doseq [d (list-directories directory)]
  (let [name (.getName d)]
      (doseq [f (list-files d)]
    (my-ls name f)))))


(list-images (File. "/home/stlk/Documents/clojure/image-resizer/images"))

