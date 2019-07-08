(ns frontend.components.footer
  (:require [reagent.core :as r]))

(defn footer []
  [:div.footer
   [:div.content.has-text-centered
    [:p
     [:strong "λ Chat à la Slack"]
     " by "
     [:a {:href "#"} "SG"] " 2019"]]])
