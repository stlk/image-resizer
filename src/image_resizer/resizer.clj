(ns image-resizer.resizer
  (:require [clojure.java.io :as io])
  (:import [java.io File FileInputStream FileOutputStream]
           [java.awt.image AffineTransformOp BufferedImage]
           java.awt.RenderingHints
           java.awt.geom.AffineTransform
           javax.imageio.ImageIO))

(def gallery-path "/home/stlk/Documents/clojure/image-resizer/images/")

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
    (scale img ratio (int (* img-width ratio)) thumb-size)))

(defn save-thumbnail [dir filename thumb-size thumb-prefix]
  (let [path (str gallery-path File/separator dir File/separator)]
    (ImageIO/write
      (scale-image (io/input-stream (str path filename)) thumb-size)
      "jpeg"
      (File. (str path thumb-prefix filename)))))


(save-thumbnail "soucasnost" "Škoda Oct policie 5 dv. 005.jpg" 1280 "_")
