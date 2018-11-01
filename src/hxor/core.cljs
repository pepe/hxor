(ns hxor.core
  (:require [hx.react :as hx :include-macros true]
            [hx.hiccup :as hiccup]
            [beicon.core :as rxt]
            ["react-dom" :as react-dom]
            [hxor.components :as comps]
            [hxor.store :as store]))

(defonce store (store/init))

(defonce state (rxt/to-atom store))

(defn render []
  (react-dom/render
   (hiccup/parse [comps/Page {:store store}])
   (. js/document getElementById "container")))


(defn init []
  (add-watch state :state
             (fn [_key _ref old-value new-value]
               (render)))
  (render))
