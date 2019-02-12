(ns hxor.core
  (:require [hx.react :as hx]
            [beicon.core :as rxt]
            ["react-dom" :as dom]
            [hxor.components :as comps]
            [hxor.store :as store]))

(defonce store (store/init))

(defn render []
  (dom/render
   (hx/f [comps/Page {:store store}])
   (. js/document getElementById "container")))


(defn init []
  (render))
