(ns image-resizer.resizer
  (:require [clojure.java.io :as io])
  (:import [java.io File FileInputStream FileOutputStream]
           [java.awt.image AffineTransformOp BufferedImage]
           java.awt.RenderingHints
           java.awt.geom.AffineTransform
           javax.imageio.ImageIO))

(def gallery-path "/home/stlk/Documents/clojure/image-resizer/images/rental/")

(defn scale [img ratio width height]
  (let [scale        (AffineTransform/getScaleInstance
                       (double ratio) (double ratio))
        transform-op (AffineTransformOp.
                       scale AffineTransformOp/TYPE_BILINEAR)]
    (.filter transform-op img (BufferedImage. width height (.getType img)))))

(defn scale-image [file thumb-size]
  (let [img        (ImageIO/read file)
        img-width  (.getWidth img)
        img-height (.getHeight img)
        ratio      (/ thumb-size img-height)]
    (if (< ratio 1)
      (scale img ratio (int (* img-width ratio)) thumb-size)
      img)))

(defn save-thumbnail [dir filename new-filename thumb-size thumb-prefix]
  (let [path (str dir File/separator)]
    (ImageIO/write
      (scale-image (io/input-stream (str path filename)) thumb-size)
      "jpeg"
      (File. (str gallery-path thumb-prefix new-filename)))))

(defn resize-image [dir orig-path path]
  (.mkdir (java.io.File. "/home/stlk/Documents/clojure/image-resizer/images/"))
  (.mkdir (java.io.File. gallery-path))
  (save-thumbnail dir orig-path path 1280 "")
  (save-thumbnail dir orig-path path 180 "_"))


;(resize-image "/home/stlk/Documents/clojure/image-resizer/images_src/20-30/" "Hupmobile A 1928  USA.jpg" "Hupmobile A 1928  USA_xxx.jpg")
