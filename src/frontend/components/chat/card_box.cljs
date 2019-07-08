(ns frontend.components.chat.card-box
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [frontend.ws :refer [token]]))

(defn card-box []
  (let [current-user @(rf/subscribe [:current-user token])]
    [:div.box
     [:div.card-content
      [:div.media
       [:div.media-left
        [:figure.image.is-48x48
         [:img {:src "https://bulma.io/images/placeholders/96x96.png"}]]]
       [:div.media-content
        [:p.title.is-4 (:name current-user)]
        [:p.subtitle.is-6 "@blabla"]]]
      [:div.content
       "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus nec iaculis mauris."]]]))
