(ns frontend.components.nickname.nickname-form-container
  (:require [frontend.components.navbar :refer [navbar]]
            [frontend.components.nickname.nickname-form :refer [nickname-form]]))

(defn container []
  [:<>
   [navbar]
   [nickname-form]])

