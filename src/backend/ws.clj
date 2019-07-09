(ns backend.ws
  (:require [org.httpkit.server :refer [send!]]
            [cheshire.core :refer [generate-string]]))

(def clients (atom {}))
(def messages (atom []))

(def UPDATE-ACTIVE-USERS "update-active-users")
(def UPDATE-MESSAGES "update-messages")

(defn broadcast [payload]
  (let [{message :message} payload
        {data :data :or {data nil}} payload
        body {:status message :data data}
        stringified (generate-string body)]
    (doseq [client @clients]
      (send! (key client) stringified))))

(defn register-name [con {:keys [data]}]
  (let [{:keys [name uuid]} data
        id (.toString (java.util.UUID/randomUUID))
        user-data {:name name :uuid uuid :id id :status "user" :active false}]
    (swap! clients assoc-in [con :data] user-data)
    (broadcast {:message UPDATE-ACTIVE-USERS})))

(defn broadcast-message [con data]
  (let [payload (:data data)
        room (:room payload)]
    (reset! messages (conj @messages (dissoc payload :status)))
    (broadcast {:message UPDATE-MESSAGES :data room})))

(defn ws-req-handler [con data]
  (let [status (keyword (get-in data [:data :status]))]
    (cond
      (= status :register-name)
      (register-name con data)
      (= status :broadcast-message)
      (broadcast-message con data)
      :default
      true)))
