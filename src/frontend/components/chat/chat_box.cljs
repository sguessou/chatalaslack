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
    (let [room-status @(rf/subscribe [:room-status])
          room-name @(rf/subscribe [:active-room-name])
          messages (if (= room-status "room") @(rf/subscribe [:n-messages 5]) @(rf/subscribe [:n-messages-private 5]))]
      [:div
       [:h2.title.is-3
        [:i.far.fa-comments] (str " " room-name)]
       [:div.box
        (for [message messages]
          (if (= token (:uuid message))
            ^{:key (:message-uuid  message)} [write-sent-message message]
            ^{:key (:message-uuid  message)} [write-received-message message])) 
        ]])))
