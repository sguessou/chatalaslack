(ns frontend.components.chat.chat-box
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [frontend.ws :refer [token]]))

(defn write-sent-message [data]
  [:div.container
   [:div.columns
    [:div.column
     [:span.tag.is-white (str "@" (:name data) "  " (:time data) " wrote:")]
     [:div.notification.is-success
      (:message data)]]
    [:div.column.is-one-fifth]]])

(defn write-received-message [data]
  [:div.container 
   [:div.columns
    [:div.column.is-one-fifth]
    [:div.column
     [:span.tag.is-white (str "@"(:name data) "  "(:time data) " wrote:")]
     [:div.notification.is-info
      (:message data)]]]])

(defn box []
  (fn []
    (let [rooms @(rf/subscribe [:rooms-info])
          active-room (filter (fn [room] (:active room)) rooms)
          name (:name (first active-room))
          active-room-id @(rf/subscribe [:active-room])
          room-messages @(rf/subscribe [:room-messages active-room-id])]
      [:div
       [:h2.title.is-3 [:i.far.fa-comments] (str " " name)]
       [:div.box
        (for [message room-messages]
          (if (= token (:uuid message))
            ^{:key (:message-uuid  message)} [write-sent-message message]
            ^{:key (:message-uuid  message)} [write-received-message message])) 
        ]])))
