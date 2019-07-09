(ns frontend.components.events
  (:require [reagent.core :as r]
            [day8.re-frame.http-fx]
            [re-frame.core :as rf]
            [ajax.core :refer [json-request-format json-response-format]]))

(rf/reg-event-db
 :load-db
 (fn [_ _]
   {}))

(rf/reg-event-db
 :bad-response
 (fn []
   (js/console.log "fatal backend error.")))

(rf/reg-event-db
 :process-rooms-data
 (fn [db [_ response]]
   (assoc db :rooms response)))

(rf/reg-event-fx
 :initialize-db
 (fn [{:keys [db]} _]
   {:http-xhrio {:method :get
                 :uri "/rooms-data"
                 :format (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success [:process-rooms-data]
                 :on-failure [:bad-response]}
    :db db}))

(rf/reg-event-fx
 :update-active-users
 (fn [{:keys [db]} _]
   {:http-xhrio {:method :get
                 :uri "/users-data"
                 :format (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success [:process-users-data]
                 :on-failure [:bad-response]}
    :db db}))

(rf/reg-event-db
 :process-users-data
 (fn [db [_ response]]
   (assoc db :rooms (concat (filterv #(= (:status %) "room") (:rooms db)) response))))

(rf/reg-event-db
 :change-room
 (fn [db [_ id]]
   (assoc db :rooms (mapv (fn [room]
                           (if (= id (:id room))
                             (assoc room :active true)
                             (assoc room :active false)))
                         (:rooms db)))))


(rf/reg-event-fx
 :update-messages
 (fn [{:keys [db]} _]
   {:http-xhrio {:method :get
                 :uri "/messages"
                 :format (json-request-format)
                 :response-format (json-response-format {:keywords? true})
                 :on-success [:process-new-messages]
                 :on-failure [:bad-response]}
    :db db}))

(rf/reg-event-db
 :process-new-messages
 (fn [db [_ response]]
   (assoc db :messages response)))

(rf/reg-event-db
 :set-current-user
 (fn [db [_ data]]
   (assoc db :current-user data)))
