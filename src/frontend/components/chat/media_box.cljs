(ns frontend.components.chat.media-box
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [time.core :as t]
            [frontend.ws :refer [ws-send-message token]]))

(defn media-box []
  (let [val (r/atom "")]
    (fn []
      (let [active-room @(rf/subscribe [:active-room])
            status @(rf/subscribe [:room-status])
            to @(rf/subscribe [:recipient])
            recipient (if (= status "room") "all" to)]
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
                                                  (let [name @(rf/subscribe [:name])
                                                        uuid (str (random-uuid))
                                                        time (t/format (t/date) "yyyy-MM-dd HH:mm")]
                                                    (ws-send-message {:data {:message @val
                                                                             :uuid token
                                                                             :name name
                                                                             :message-uuid uuid
                                                                             :room active-room
                                                                             :time time
                                                                             :recipient recipient
                                                                             :status :broadcast-message}})
                                                    (reset! val "")))))}
               "Submit"]]]]]]]))))
