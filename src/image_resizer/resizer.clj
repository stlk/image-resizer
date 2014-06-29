(ns image-resizer.resizer
  (:require [clojure.java.io :as io]
            [fivetonine.collage.util :as util]
            [fivetonine.collage.core :refer [with-image resize]])
  (:import [java.io File]))

(def gallery-path "/home/stlk/Documents/clojure/image-resizer/images/rental/")

(defn save-thumbnail [dir filename new-filename thumb-size thumb-prefix]
  (let [path (str dir File/separator)]
    (def image (util/load-image (str path filename)))
    (with-image image
            (resize :width thumb-size)
            (util/save (str gallery-path thumb-prefix new-filename) :quality 1.0))))

(defn resize-image [dir orig-path path]
  (.mkdir (File. "/home/stlk/Documents/clojure/image-resizer/images/"))
  (.mkdir (File. gallery-path))
  (save-thumbnail dir orig-path path 1280 "")
  (save-thumbnail dir orig-path path 180 "_"))
