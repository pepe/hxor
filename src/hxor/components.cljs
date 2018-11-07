(ns hxor.components
  (:require
   [beicon.core :as rxt]
   [hx.react :as hx :include-macros true]
   [hx.hiccup :as hiccup]
   [hxor.events :as events]
   [potok.core :as ptk]))

(defn cursor [state key] (get (deref state) key))

(hx/defnc Header [{:keys [kind] :as props}]
  [:header
   {:style {:padding "0 2rem"}}
   [:h1 "Hello, you "
    [:span {:style {:font-weight "bold"}} kind] " HXOR!"]])

(hx/defnc Buttons [{:keys [kind store]}]
  [:div
   (for [k    ["nice" "leet"]
         :let [current (= kind k)]]
     [:button {:key (str (when current "disabled-" )kind)
               :disabled current
               :on-click #(ptk/emit! store (events/->Make k))}
      "make HXOR " k])])

(hx/defnc Counting [{:keys [count store]}]
  [:div
   [:label "HXOR must count to: "
    [:input {:value count
             :on-change #(ptk/emit! store (events/->SetCount (-> % .-target .-value js/Number.parseInt)))}]]
   [:div (map (fn [i] [:span {:key (str i)} i " "]) (range 1 (inc count)))]])

(hx/defnc Main [{:keys [count store kind]}]
  [:main
   {:style {:padding "2rem"}}
   [:p "There must be something better that the others."]
   [Buttons {:store store :kind kind}]
   [Counting {:store store :count count}]])

(hx/defnc Footer [props]
  [:footer
   {:style {:position "fixed" :bottom 0 :padding "2rem"}}
   "All right reversed"])

(hx/defnc Page [{:keys [store] :as props}]
  (let [state-atom (rxt/to-atom store)
        kind       (cursor state-atom :kind)
        count      (cursor state-atom :count)]
    [:<>
     [Header {:kind kind}]
     [Main {:count count
            :kind  kind
            :store store}]
     [Footer]]))
