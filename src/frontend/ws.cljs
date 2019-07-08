(ns frontend.ws
  (:require [cljs.core.async :as a :refer [<! >!]]
            [re-frame.core :as rf]
            [wscljs.client :as ws]
            [wscljs.format :as fmt]
            [cemerick.url :refer [url]]
            [clojure.string :as s]))

(def clients (atom {}))

(def token (str (random-uuid)))

(defn ws-handler [message]
  (let [data (.parse js/JSON (.-data message))
        keyed-data (js->clj data :keywordize-keys true)  
        status (:status keyed-data)]
    (cond
      (= status "update-active-users")
      (rf/dispatch [:update-active-users])
      (= status "update-messages")
      (rf/dispatch [:update-messages])
      :default
      (js/console.log "cond default"))))

(def my-url (str (url (-> js/window .-location .-href)) "chat"))

(def ws-url (or (s/replace my-url #"http" "ws") (s/replace my-url #"https" "ws")))

(def handlers {:on-message ws-handler
               :on-open #(prn (str "connected!!" ))
               :on-close #(prn "conn closed!")})

(def socket (ws/create ws-url handlers))

(defn ws-send-message [data]
  (when socket
    (ws/send socket data fmt/json)))

