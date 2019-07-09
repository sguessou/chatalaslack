(ns frontend.components.nickname.nickname-form
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as s]
            [frontend.ws :refer [ws-send-message token]]
            [frontend.components.footer :refer [footer]]))

(defn nickname-form []
  (let [name (r/atom "")]
    (fn []
      [:div.section
       [:div.columns
        [:div.column]
        [:div.column
         [:article.message
          [:div.message-header
           [:p "Chat à la Slack howto."]
           [:button.delete {:aria-label "delete"}]]
          [:div.message-body
           "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque risus mi, tempus quis placerat ut, porta nec nulla. Vestibulum rhoncus ac ex sit amet fringilla."]]
         [:div.field
          [:label.label "Nickname"]
          [:div.control[:input.input {:type "text"
                                      :placeholder "Enter a nickname"
                                      :value @name
                                      :on-change #(reset! name (-> %
                                                                   .-target
                                                                   .-value))}]]]
         [:div.field
          [:div.control
           [:button.button.is-link {:on-click (fn []
                                                (if (empty? @name)
                                                  (js/console.log "invalid nickname!")
                                                  (do
                                                    (s/dispatch! "#/chat")
                                                    (let [user {:name @name
                                                                :uuid token
                                                                :status :register-name}]
                                                      (rf/dispatch [:set-current-user (dissoc user :status)])
                                                      (ws-send-message {:data user})))))}
            "Start chatting..."]]]]
        [:div.column]]
       [footer]]
      )))
