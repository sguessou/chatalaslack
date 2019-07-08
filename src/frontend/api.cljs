(ns frontend.api
  (:require [ajax.core :refer [GET]]))

(defn fetch-rooms-data []
  (GET "/rooms-data" {:handler (fn [resp] resp)}))


