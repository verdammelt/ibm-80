(ns ibm-80.app
  (:require [compojure.core :refer [defroutes GET POST context]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [redirect response]]
            [ibm-80.parser.record :refer [guess-delimeter]]))


(defroutes sort-routes
  (POST "/" {body :body} (response {:delimiter (str (guess-delimeter (body "testString")))})))

(defroutes app-routes
  (GET "/" []  (redirect "index.html"))
  (context "/sort" [] sort-routes)
  (resources "/")
  (not-found "What'chyu talking about Willis?!"))

;; not a great thing - but good enough for now.
(defn simple-logging-middleware [app]
  (fn [req]
    (let [now (java.util.Date.)]
      (println (format "%s:INFO:%s - %s" 
                       (str now)        ; not a great format.
                       (:request-method req)
                       (:uri req))))
    (app req)))

(def app
  (-> (site app-routes)
     simple-logging-middleware
     (wrap-json-body)
     (wrap-json-response)))
