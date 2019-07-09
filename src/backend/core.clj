(ns backend.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server with-channel
                                        on-close on-receive]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.handler.dump :refer [handle-dump]]
            [ring.util.response :as resp]
            [reitit.ring :as reitit-ring]
            [cheshire.core :refer [generate-string parse-string]]
            [backend.ws :refer [clients messages ws-req-handler
                                broadcast UPDATE-ACTIVE-USERS]]
            [backend.loading-page :refer [loading-page]]))

(defn ws-handler [req]
  (with-channel req con
    (swap! clients assoc con {:connected true})
    (on-receive con (fn [data]
                      (println data)
                      (ws-req-handler con (parse-string data true))))
    (on-close con (fn [status]
                    (swap! clients dissoc con)
                    (println con " disconnected. status: " status)
                    (broadcast {:message UPDATE-ACTIVE-USERS})))))

(defn rooms-data [req]
  {:status 200
   :body (generate-string [{:id 1
                            :name "Get functional or die trying"
                            :active false
                            :status "room"}
                           {:id 2
                            :name "My life after React"
                            :active true
                            :status "room"}
                           {:id 3
                            :name "OOP anonymous"
                            :active false
                            :status "room"}])
   :headers {}})

(defn users-data [req]
  (let [users (into [] (vals @clients))
        data (->> users
                  (mapv :data)
                  (filter identity))]
    {:status 200
     :body (generate-string data)
     :headers {}}))

(defn messages-handler [req]
  {:status 200
     :body (generate-string @messages)
     :headers {}})

(defn index-handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (loading-page)})

(def middleware
  [#(wrap-defaults % site-defaults)
   wrap-exceptions])

(def app
  (reitit-ring/ring-handler
   (reitit-ring/router
    [["/" {:get {:handler index-handler}}]
     ["/chat" {:get {:handler ws-handler}}]
     ["/rooms-data" {:get {:handler rooms-data}}]
     ["/users-data" {:get {:handler users-data}}]
     ["/messages" {:get {:handler messages-handler}}]])
   (reitit-ring/routes
    (reitit-ring/create-resource-handler {:path "/" :root "/public"})
    (reitit-ring/create-default-handler))
   {:middleware middleware}))

(defn -dev-main [port]
  (let [p (Integer/parseInt (or port "8000"))]
    (run-server (wrap-reload #'app) {:port p :join? false})))

(defn -main [port]
  (run-server app {:port (Integer. port)}))


