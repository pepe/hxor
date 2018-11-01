(ns hxor.events
  (:require [potok.core :as ptk]))


(defrecord Make [which]
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :kind which)))

(defrecord SetCount [amount]
  ptk/UpdateEvent
  (update [_ state] (assoc state :count amount)))
