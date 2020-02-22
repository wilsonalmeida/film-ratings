(defproject film-ratings "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [duct/core "0.7.0"]
                 [duct/module.ataraxy "0.3.0"]
                 ;; [g7s/module.shadow-cljs "0.1.1"]
                 [duct/module.logging "0.4.0"]
                 [duct/logger.timbre "0.4.1"]
                 [duct/module.sql "0.5.0"]
                 [duct/module.web "0.7.0"]
                 [org.xerial/sqlite-jdbc "3.27.2"]
                 [org.postgresql/postgresql "42.1.4"]
                 [duct/database.sql.hikaricp "0.4.0"]
                 [hiccup "1.0.5"]]
  :plugins [[duct/lein-duct "0.12.1"]]
  :main ^:skip-aot film-ratings.main
  :uberjar-name "film-ratings.jar"
  :resource-paths ["resources" "target/resources"]
  :clean-targets ^{:protect false} ["target"]
  ;; "resources/film_ratings/public/js"
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :dependencies [[cider/piggieback "0.4.0"]]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.1"]
                                   [eftest "0.5.7"]
                                   [kerodon "0.9.0"]]}})
