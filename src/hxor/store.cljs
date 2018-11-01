(ns hxor.store
  (:require [potok.core :as ptk]))

(defn init []
  (ptk/store
   {:state
    {:kind "leet"
     :count 10}}))

