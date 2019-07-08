(defproject reaktor-assignment "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 [http-kit "2.3.0"]
                 [ring-server "0.5.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring "1.7.1"]
                 [haslett "0.1.6"]
                 [ring-cors "0.1.13"]
                 [cheshire "5.8.1"]
                 [metosin/reitit "0.3.7"]
                 [prone "1.6.3"]
                 [hiccup "1.0.5"]
                 [ultra-csv "0.2.3"]
                 [clj-time "0.15.0"]
                 [reagent "0.8.1"]
                 [danlentz/clj-uuid "0.1.9"]
                 [cljs-ajax "0.8.0"]
                 ;;[adzerk/env "0.4.0"]
                 [com.cemerick/url "0.1.1"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [time "0.4.4"]]
  
  :main backend.core
  :min-lein-version "2.5.0"
  :uberjar-name "chat.jar"
  
  :clean-targets ^{:protect false} ["target"]
  :resource-paths ["resources" "target"]
  :profiles {:dev
             {:dependencies [[binaryage/devtools "0.9.9"]]}
             :uberjar {:aot :all}})
