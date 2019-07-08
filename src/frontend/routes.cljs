(ns frontend.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as s]
            [re-frame.core :as rf]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [frontend.state :refer [app-state]]
            [frontend.components.nickname.nickname-form-container :refer [container]]
            [frontend.components.chat.chat-container :refer [chat-panel]]))

(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (s/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (s/set-config! :prefix "#")
  (defroute "/" []
    (swap! app-state assoc :page :home))
  (defroute "/chat" []
    (do
      (rf/dispatch [:update-messages])
      (swap! app-state assoc :page :chat)))
  (hook-browser-navigation!))


(defmulti current-page #(@app-state :page))
(defmethod current-page :home []
  [container])
(defmethod current-page :chat []
  [chat-panel])
(defmethod current-page :default []
  [:div "default"])
