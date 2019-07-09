(ns frontend.components.chat.rooms-container
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(defn show-room [item]
  (let [{:keys [id name active status]} item]
    [:a.panel-block {:class (when active "is-active")
                     :on-click #(rf/dispatch [:change-room id])}
     [:span.panel-icon
      (if (= status "room")
        [:i.fas.fa-comments {:aria-hidden "true"}]
        [:i.fas.fa-user-ninja {:aria-hidden "true"}])]
     name]))

(defn rooms-panel []
  (fn []
    [:nav.panel
     [:p.panel-heading [:i.fas.fa-level-up-alt] " Chat Rooms"]
     (let [rooms @(rf/subscribe [:rooms-data])]
       (for [room rooms]
         ^{:key (:id room)}[show-room room]))]))
