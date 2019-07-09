(ns frontend.components.chat.users-container
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [frontend.ws :refer [token]]))

#_(defn show-user [data]
  [:a.panel-block
   [:span.panel-icon
    [:i.fas.fa-user-ninja]]
   (:name data)])

(defn users-panel []
  (fn []
    (let [count @(rf/subscribe [:count-active-users token])]
      (js/console.log count)
      [:nav.panel
       [:p.panel-heading [:i.fas.fa-users] " Active Users " count]
       #_(for [user users]
         ^{:key (:uuid user)} [show-user user])])))
