(ns frontend.components.chat.chat-container
  (:require [reagent.core :as r]
            [frontend.components.navbar :refer [navbar]]
            [frontend.components.footer :refer [footer]]
            [frontend.components.chat.rooms-container :refer [rooms-panel]]
            [frontend.components.chat.users-container :refer [users-panel]]
            [frontend.components.chat.chat-box :refer [box]]
            [frontend.components.chat.media-box :refer [media-box]]
            [frontend.components.chat.card-box :refer [card-box]]))

(defn chat-panel []
  [:<>
   [navbar]
   [:div.section
    [:div.columns
     [:div.column.is-one-third
      [:div.section
       [rooms-panel]
       [users-panel]
       [card-box]]]
     [:div.column
      [:div.section
       [box]
       [:br]
       [media-box]]]]]
   [footer]])
