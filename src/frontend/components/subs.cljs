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
 :filtered-users
 (fn []
   (subscribe [:users]))
 (fn [users [_ token]]
   (filterv (fn [user] (not= token (:uuid user))) users)))

(reg-sub
 :active-room-data
 (fn [db _]
   (->> (filter (fn [room] (:active room)) (:rooms db))
        (first))))

(reg-sub
 :count-active-users
 (fn []
   (subscribe [:rooms-info]))
 (fn [rooms [_ token]]
   (let [users (filter #(and (= (:status %) "user") (not= (:uuid %) token)) rooms)]
     (count users))))

(reg-sub
 :active-room
 (fn []
   (subscribe [:active-room-data]))
 (fn [room-data]
   (:id room-data)))

(reg-sub
 :active-room-private
 (fn []
   (subscribe [:active-room-data]))
 (fn [room-data]
   (:uuid room-data)))

(reg-sub
 :active-room-name
 (fn []
   (subscribe [:active-room-data]))
 (fn [room-data]
   (:name room-data)))

(reg-sub
 :room-status
 (fn []
   (subscribe [:active-room-data]))
 (fn [room-data]
   (:status room-data)))

(reg-sub
 :recipient
 (fn []
   (subscribe [:active-room-data]))
 (fn [room-data]
   (:uuid room-data)))

(reg-sub
 :current-user
 (fn [db _]
   (:current-user db)))

(reg-sub
 :name
 (fn []
   (subscribe [:current-user]))
 (fn [current-user]
   (:name current-user)))

(reg-sub
 :all-messages
 (fn [db]
   (:messages db)))

(reg-sub
 :messages-by-room
 (fn []
   (subscribe [:all-messages]))
 (fn [messages [_ room]]
   (->> messages
        (filterv (fn [message]
                   (= (:room message) room))))))

(reg-sub
 :n-messages
 (fn []
   (subscribe [:active-room]))
 (fn [room [_ n]]
   (let [messages @(subscribe [:messages-by-room room])
         f (fnil take-last [])]
     (f n messages))))

(reg-sub
 :messages-by-user
 (fn []
   (subscribe [:all-messages]))
 (fn [messages [_ uuid]]
   (let [current-user @(subscribe [:current-user])
         token (:uuid current-user)]
     (->> messages
          (filterv (fn [message]
                     (or
                      (= (:recipient message) uuid)
                      (= (:recipient message) token))))))))

(reg-sub
 :n-messages-private
 (fn []
   (subscribe [:active-room-private]))
 (fn [uuid [_ n]]
   (let [messages @(subscribe [:messages-by-user uuid])
         f (fnil take-last [])]
     (f n messages))))

(reg-sub
 :rooms-data
 (fn []
   [(subscribe [:current-user])
    (subscribe [:rooms-info])])
 (fn [[user rooms]]
   (let [token (:uuid user)]
     (filter #(not= token (:uuid %)) rooms))))


