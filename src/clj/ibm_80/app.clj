(ns ibm-80.app
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [ring.util.response :refer [redirect]]))

(defroutes app-routes
  (GET "/" []  (redirect "index.html"))
  (resources "/")
  (not-found "What'chyu talking about Willis?!"))

(def app
  (site app-routes))
