(ns joy.units)

(def simple-metric {:meter 1,
                    :km 1000,
                    :cm 1/100,
                    :mm [1/10 :cm]})

(comment
  (->     (* 3  (:km simple-metric))
       (+ (* 10 (:meter simple-metric)))
       (+ (* 80 (:cm simple-metric)))
       (+ (* (:cm simple-metric)
             (* 10 (first (:mm simple-metric)))))
       float)
  ;;=> 3010.81
  )

;;
;; Listing 7.1
;;
(defn convert [context descriptor]
  (reduce (fn [result [mag unit]]
            (+ result
               (let [val (get context unit)]
                 (if (vector? val)
                   (* mag (convert context val))
                   (* mag val)))))
          0
          (partition 2 descriptor)))

(comment
  (convert simple-metric [1 :meter])
  ;;=> 1

  (convert simple-metric [50 :cm])
  ;;=> 1/2

  (convert simple-metric [100 :mm])
  ;;=> 1/10

  (float (convert simple-metric [3 :km 10 :meter 80 :cm 10 :mm]))
  ;;=> 3010.81

  (convert {:bit 1, :byte 8, :nibble [1/2 :byte]} [32 :nibble])
  ;;=> 128N
  )

