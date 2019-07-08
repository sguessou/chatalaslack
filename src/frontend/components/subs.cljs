(ns frontend.components.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]))

(reg-sub
 :rooms-info
 (fn [db _]
   (:rooms db)))

(reg-sub
 :users
 (fn [db _]
   (:users db)))

(reg-sub
 :active-room
 (fn [db _]
   (->> (filter (fn [room] (:active room)) (:rooms db))
        (first)
        (:id))))

(reg-sub
 :current-user
 (fn [db [_ token]]
   (->> (:users db)
        (filter (fn [user] (= token (:uuid user))))
        (first))))

(reg-sub
 :messages
 (fn [db]
   (:messages db)))

(reg-sub
 :room-messages
 (fn []
   (subscribe [:messages]))
 (fn [messages [_ room]]
   (->> messages
        (filterv (fn [message]
                   (= (:room message) room)))
        (take-last 5))))
