(ns frontend.components.navbar
  (:require [reagent.core :as r]))

(defn navbar []
  [:nav.navbar.is-fixed-top {:role "navigation"
                             :aria-label "main navigation"}
   [:div.navbar-brand
    [:a.navbar-item {:href "https://changelog.com/posts/rich-hickeys-greatest-hits"}
     [:figure.image.is-32x32
      [:img {:src "/images/lambda.png"}]]
     [:h1.subtitle.is-3 "Chat à la Slack.."]]
    
    [:a.navbar-burger {:role "button"
                       :aria-label "menu"
                       :aria-expanded "false"}
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]
     [:span {:aria-hidden "true"}]]]])
