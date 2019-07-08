(ns ^:figwheel-hooks frontend.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [frontend.routes :refer [app-routes current-page]]
            [frontend.components.events]
            [frontend.components.subs]))

(defn mount []
  (app-routes)
  (r/render
   [current-page]
   (.getElementById js/document "app")))

(defn ^:after-load re-render []
  (mount))

(defonce start-up (do
                    (rf/dispatch-sync [:load-db])
                    (rf/dispatch [:initialize-db])
                    (mount)
                    true))
