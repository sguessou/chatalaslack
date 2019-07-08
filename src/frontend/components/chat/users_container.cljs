(ns frontend.components.chat.users-container
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [frontend.ws :refer [token]]))

(defn show-user [data]
  [:a.panel-block {:class (when (= token (:uuid data)) "is-active")}
   [:span.panel-icon
    [:i.fas.fa-user-ninja]]
   (:name data)])

(defn users-panel []
  (fn []
    (let [users @(rf/subscribe [:users])
          filtered-users (filter (fn [user] (not= token (:uuid user))) users)]
      [:nav.panel
       [:p.panel-heading [:i.fas.fa-users] " Active Users " (count filtered-users)]
       (for [user filtered-users]
         ^{:key (:uuid user)} [show-user user])])))
