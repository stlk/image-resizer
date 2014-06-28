(ns image-resizer.core
  (:use image-resizer.name-parser)
  (:use image-resizer.resizer)
  (:use image-resizer.collection)
  (:use selmer.parser)
  (:import java.io.File))

(def directories [{:id "20-30"
           :name "20. až 30. léta"}
           {:id "40-50"
            :name "40. až 50. léta"}
           {:id "60-70"
            :name "60. až 70. léta"}
           {:id "80-soucasnost"
            :name "80. léta - současnost"}])


(defn list-files [d]
  (.listFiles d))


(defn list-directories [d]
    (filter #(.isDirectory %) (.listFiles d)))


(defn list-images [directory]
    (reverse (map (fn [d] {:name  (find-name (.getName d) directories)
                  :path (.getName d)
                  :cars (map (fn [[{car-name :name car-id :identifier} files]]
                                 {:id car-id
                                  :name car-name
                                  :files files})
                               (group-by #(select-keys % [:name :identifier]) (sort-by first (map #(let [name (parse-car-name (.getName %))]
                                                                       (resize-image d (:original_path name) (:path name))
                                                                       name) (list-files d)))))})
         (list-directories directory))))


(selmer.parser/set-resource-path! "/home/stlk/Documents/clojure/image-resizer/resources/")


(spit "/home/stlk/Documents/clojure/image-resizer/images/pronajem-vozu.htm" (render-file "pronajem-vozu.htm"
             {:epoch_list (list-images (File. "/home/stlk/Documents/clojure/image-resizer/images_src"))}))


;(clojure.pprint/pprint (list-images (File. "/home/stlk/Documents/clojure/image-resizer/images_src")))
