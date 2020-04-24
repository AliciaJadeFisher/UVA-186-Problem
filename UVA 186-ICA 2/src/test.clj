(ns test)

(require '[main :refer [nodes, nodes2, all-routes, all-routes2 dijkstra1, dijkstra2,]])

(defn test-dijkstra1-work []
  (println "================== Given Problem Solutions ==================")
  (println "Problem 1 : Santa Barbara to Las Vegas")
  (dijkstra1 "Santa Barbara" "Las Vegas" all-routes)
  (println "")
  (println "Problem 2 : San Diego to Los Angeles")
  (dijkstra1 "San Diego" "Los Angeles" all-routes)
  (println "")
  (println "Problem 3 : San Louis Obispo to Los Angeles")
  (dijkstra1 "San Louis Obispo" "Los Angeles" all-routes)
  (println "=============================================================")
  (println "================== Found Problem Solutions ==================")
  (println "Problem 1 : A to J")
  (dijkstra1 "A" "J" all-routes2)
  (println "")
  (println "Problem 2 : C to H")
  (dijkstra1 "C" "H" all-routes2)
  (println "")
  (println "Problem 3 : J to B")
  (dijkstra1 "J" "B" all-routes2)
  (println "=============================================================")
  )

(defn test-dijkstra2-work []
  (println "================== Given Problem Solutions ==================")
  (println "Problem 1 : Santa Barbara to Las Vegas")
  (println "Solution : " (dijkstra2 nodes :Santa-Barbara :Las-Vegas))
  (println "")
  (println "Problem 2 : San Diego to Los Angeles")
  (println "Solution : " (dijkstra2 nodes :San-Diego :Los-Angeles))
  (println "")
  (println "Problem 3 : San Louis Obispo to Los Angeles")
  (println "Solution : " (dijkstra2 nodes :San-Louis-Obispo :Los-Angeles))
  (println "=============================================================")
  (println "================== Found Problem Solutions ==================")
  (println "Problem 1 : A to J")
  (println "Solution : " (dijkstra2 nodes2 :A :J))
  (println "Problem 2 : C to H")
  (println "Solution : " (dijkstra2 nodes2 :C :H))
  (println "")
  (println "Problem 3 : J to B")
  (println "Solution : " (dijkstra2 nodes2 :J :B))
  (println "=============================================================")
  )

;Optimal Solution Tests
; =========== Small Problem ==========
; + San Diego to Los Angeles
; + Dijkstra 1 Timings = 3.200744 msecs, 2.716888 msecs, 3.134822 msecs, 3.758203 msecs, 3.310828 msecs
; + Dijkstra 1 Average = 3.224297‬ msecs
; + Dijkstra 2 Timings = 0.497297 msecs, 0.415374 msecs, 0.394893 msecs, 0.371852 msecs, 0.439694 msecs
; + Dijkstra 2 Average = 0.423822 msecs
; ====================================
; ========== Medium Problem ==========
; + San Louis Obispo to Los Angeles
; + Dijkstra 1 Timings = 3.497714 msecs, 3.338349 msecs, 4.696473 msecs, 3.929088 msecs, 3.898367 msecs
; + Dijkstra 1 Average = 3.8719982 msecs
; + Dijkstra 2 Timings = 0.491536 msecs, 0.488976 msecs, 0.492816 msecs, 0.519697 msecs, 0.534417 msecs
; + Dijkstra 2 Average = 0.5054884‬ msecs
; ====================================
; ========== Large Problem ===========
; + Santa Barbara to Las Vegas
; + Dijkstra 1 Timings = 4.991523 msecs, 4.27982 msecs, 4.174216 msecs, 4.27214 msecs, 4.651031 msecs
; + Dijkstra 1 Average = 4.473746 msecs
; + Dijkstra 2 Timings = 0.6125 msecs, 0.628501 msecs, 0.6061 msecs, 0.606739 msecs, 0.593299 msecs
; + Dijkstra 2 Average = 0.6094278‬ msecs
; =====================================
; These test show that the second Dijkstra solution is the most optimal solution for any given problem,
; however the first Dijkstra solution provides more information.