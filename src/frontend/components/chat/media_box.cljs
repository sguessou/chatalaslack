(ns frontend.components.chat.media-box
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [time.core :as t]
            [frontend.ws :refer [ws-send-message token]]))

(defn media-box []
  (let [val (r/atom "")
        active-room (rf/subscribe [:active-room])]
    (fn []
      [:div.box
       [:article.media
        [:div.media-content
         [:div.field
          [:p.control
           [:textarea.textarea {:placeholder "Add a comment..."
                                :value @val
                                :on-change #(reset! val (-> %
                                                            .-target
                                                            .-value))}]]]
         [:nav.level
          [:div.level-left
           [:div.level-item
            [:a.button.is-info {:on-click (fn []
                                            (if (empty? @val)
                                              (js/console.log "empty")
                                              (do
                                                (let [current-user @(rf/subscribe [:current-user token])]
                                                  (ws-send-message {:data {:message @val
                                                                           :uuid token
                                                                           :name (:name current-user)
                                                                           :message-uuid (str (random-uuid))
                                                                           :room @active-room
                                                                           :time (t/format (t/date) "yyyy-MM-dd HH:mm")
                                                                           :status :broadcast-message}})
                                                  (reset! val "")))))}
             "Submit"]]]]]]])))
