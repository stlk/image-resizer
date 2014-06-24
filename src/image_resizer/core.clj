(ns image-resizer.core
  (:use image-resizer.name-parser)
  (:use selmer.parser)
  (:import java.io.File))


(defn list-files [d]
  (.listFiles d))


(defn list-directories [d]
    (filter #(.isDirectory %) (.listFiles d)))


(defn list-images [directory]
    (map (fn [d] {:name (.getName d)
                  :images (map (fn [item] {:name (first item)
                                  :files (second item)})
                               (group-by :name (sort-by first (map #(parse-car-name (.getName %)) (list-files d)))))})
         (list-directories directory)))


(clojure.pprint/pprint (list-images (File. "/home/stlk/Documents/clojure/image-resizer/images")))


(selmer.parser/set-resource-path! "/home/stlk/Documents/clojure/image-resizer/images/")

(spit "/home/stlk/Documents/clojure/image-resizer/images/newfile.htm" (render-file "pronajem-vozu.htm"
             {:epoch_list (list-images (File. "/home/stlk/Documents/clojure/image-resizer/images"))}))
