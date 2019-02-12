(ns hxor.components
  (:require
   [beicon.core :as rxt]
   [hx.react :as hx]
   [hx.hooks :as hxh]
   [potok.core :as ptk]
   [hxor.events :as events]))

(hx/defnc Header [{:keys [store]}]
  (let [state (-> store rxt/to-atom hxh/<-deref)
        kind  (:kind state)]
  [:header
   {:style {:padding "0 2rem"}}
   [:h1 "Hello, you "
    [:span {:style {:font-weight "bold"}} kind] " HXOR!"]]))

(hx/defnc Buttons [{:keys [store]}]
  (let [state (-> store rxt/to-atom hxh/<-deref)
        kind  (:kind state)]
    [:div
     (for [k    ["nice" "leet"]
           :let [current (= kind k)]]
       [:button {:key      (str (when current "disabled-" ) kind)
                 :disabled current
                 :on-click #(ptk/emit! store (events/->Make k))}
        "make HXOR " k])]))

(hx/defnc NumberSpan [{i :i}]
  [:span {:key (str i)} i " "])

(hx/defnc Counting [{:keys [store]}]
  (let [state (-> store rxt/to-atom hxh/<-deref)
        count (:count state)]
    [:<>
     [:div
      [:label "HXOR must count to: "
       [:input {:value     count
                :on-change #(ptk/emit! store (events/->SetCount (-> % .-target .-value js/Number.parseInt)))}]]]
     [:div (map (fn [i] [NumberSpan {:i i}]) (range 1 (inc count)))]]))

(hx/defnc Main [{:keys [store]}]
  [:main
   {:style {:padding "2rem"}}
   [:p "There must be something better that the others."]
   [Buttons {:store store}]
   [Counting {:store store}]])

(hx/defnc Footer [_]
  [:footer
   {:style {:position "fixed" :bottom 0 :padding "2rem"}}
   "All right reversed"])

(hx/defnc Page [{:keys [store]}]
  [:<>
   [Header {:store store}]
   [Main {:store store}]
   [Footer]])
