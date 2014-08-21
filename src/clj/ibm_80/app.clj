(ns ibm-80.app
  (:require [compojure.core :refer [defroutes GET POST context]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [redirect response]]))


(defroutes sort-routes
  (POST "/" {body :body} (response {:recvd (body "testString")})))

(defroutes app-routes
  (GET "/" []  (redirect "index.html"))
  (context "/sort" [] sort-routes)
  (resources "/")
  (not-found "What'chyu talking about Willis?!"))

(def app
  (-> (site app-routes)
     (wrap-json-body)
     (wrap-json-response)))
