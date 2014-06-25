(ns image-resizer.collection)

(defn find-name [name items]
  (:name (first (filter #(= (:id %) name) items)) name))
