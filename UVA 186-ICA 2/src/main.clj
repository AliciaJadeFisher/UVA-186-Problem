; UVA 186 Problem
; Have a list of locations with neighbouring nodes and the distance to them
; Return the shortest possible path, between two given locations
; =====================================
; Second Dijkstra Problem : https://www.pearsonschoolsandfecolleges.co.uk/secondary/Mathematics/16plus/AdvancingMathsForAQA2ndEdition/Samples/SampleMaterial/Chp-02%20023-043.pdf
; p.g. 25, problem 2.1
; =====================================
; Peer Points
; Alicia-Jade Fisher : 5
; Manisha Lakshmipathula : 4
; Jalagari Teja : 4

(ns main)

(use '[clojure.string :only (join)])
(require '[clojure.data.priority-map :refer [priority-map priority-map-keyfn]])

;=========================================== Dijkstra Version 1 ===========================================

; Map of all routes with their neighbouring nodes and distances
(def all-routes {"Mojave" {"Bakersfield" 65, "Barstow" 70, "Los Angeles" 94},
                 "Santa Barbara" {"San Louis Obispo" 106, "Los Angeles" 95},
                 "Baker" {"Barstow" 62, "Las Vegas" 92},
                 "San Diego" {"Los Angeles" 121, "San Bernardino" 103},
                 "Wheeler Ridge" {"Bakersfield" 34, "Los Angeles" 88},
                 "Las Vegas" {"Baker" 92},
                 "Los Angeles" {"Mojave" 94, "Wheeler Ridge" 88, "Santa Barbara" 95, "San Diego" 121, "San Bernardino" 65},
                 "Barstow" {"Mojave" 70, "Baker" 62, "San Bernardino" 73},
                 "Bakersfield" {"San Louis Obispo" 117, "Mojave" 65, "Wheeler Ridge" 24},
                 "San Louis Obispo" {"Bakersfield" 117, "Santa Barbara" 106},
                 "San Bernardino" {"San Diego" 103, "Barstow" 73, "Los Angeles" 65}})

(def all-routes2 {"A" {"B" 4, "C" 5, "D" 8},
                  "B" {"A" 4, "D" 3, "E" 12},
                  "C" {"A" 5, "D" 1, "F" 11},
                  "D" {"A" 8, "B" 3, "C" 1, "E" 9, "F" 4, "G" 10},
                  "E" {"B" 12, "D" 9, "G" 6, "H" 10},
                  "F" {"C" 11, "D" 4, "G" 5, "I" 11},
                  "G" {"D" 10, "E" 6, "H" 3, "F"5, "I" 5, "J" 15}
                  "H" {"E" 10, "G" 3, "J" 14},
                  "I" {"F" 11, "G" 5, "J" 8},
                  "J" {"H" 14, "G" 15, "I" 8}})

; Takes a list of the next destinations and a function (in this case to grab the distance of each node)
; For each node in the next destinations list, it maps the previous node and the distance from the previous node to it
; Then it is all saved to a new list
(defn map-vals [next-nodes get-dist]
  (into {} (for [[k v] next-nodes] [k (get-dist v)])))

; Removes any previously visited nodes from the next-nodes list
(defn remove-prev-nodes [next-nodes visited]
  (select-keys next-nodes (filter (complement visited) (keys next-nodes))))

; Returns the smallest value of two values
(defn min-value [node1 node2]
  (if (< (first node1) (first node2))
    node1
    node2))

; Returns a list that contains the shortest route to all nodes from the starting node
; Next-nodes : A priority map (smallest value first) of the next set of nodes to visit
; Visited : A list of nodes that have been visited
; Peeks at the next node to visit and uses destructuring to separate the node, distances and the previous node
; Poss :  A list of all possible next nodes to visit, that does not contain previously visited nodes
; Next-poss : An updated version of poss that contains the total distance traveled,
; and the previous nodes for the current node set
; This loop is then recursively called with the following :
; Merge-with : Merges the current list of paths with the newly found paths and sorted in ascending order of distance
; Assoc : adds the current node to the visited node list
(defn dijkstra [start routes]
  (loop [next-nodes (priority-map-keyfn first start [0 nil]), visited {}]
    (if-let [[node [dist prev]] (peek next-nodes)]
      (let [poss (remove-prev-nodes (routes node) visited)]
        (let [next-poss (map-vals poss (fn [cost] (let [new-cost (+ dist cost)] [new-cost node])))]
          (recur (merge-with min-value (pop next-nodes) next-poss) (assoc visited node [dist prev]))))
      visited)))

; Iterates through the dijkstra result, from the destination to find the starting node
; Reverses the result to show path from start to destination
(defn path-to [dest route]
  (if (contains? route dest)
    (reverse (take-while identity (iterate (comp second route) dest)))
    nil))

; Finds the destination in the dijkstra result and returns the total distance to destination
(defn distance-to [dest route]
  (if (contains? route dest)
    (first (route dest))
    -1))

; Used to print out the solution values
(defn dijkstra1 [start dest route-col]
  (let [dij (dijkstra start route-col)]
    (println "Solution Path : " (join ", " (path-to dest dij)))
    (println "Solution Distance : " (distance-to dest dij)))
  )
;=========================================== Dijkstra Version 2 ===========================================

(def ^:private place-holder Double/POSITIVE_INFINITY)

; Map of all routes with their neighbouring nodes and distances
(def nodes
  {
   :San-Louis-Obispo {:Bakersfield 117, :Santa-Barbara 106}
   :Bakersfield {:San-Louis-Obispo 117, :Mojave 65, :Wheeler-Ridge 24}
   :Mojave {:Bakersfield 65, :Barstow 70, :Los-Angeles 94}
   :Barstow {:Mojave 70, :Baker 62, :San-Bernardino 73}
   :Wheeler-Ridge {:Bakersfield 34, :Los-Angeles 88}
   :Baker {:Barstow 62, :Las-Vegas 92}
   :Las-Vegas {:Baker 92}
   :Santa-Barbara {:San-Louis-Obispo 106, :Los-Angeles 95}
   :San-Diego {:Los-Angeles 121, :San-Bernardino 103}
   :San-Bernardino {:San-Diego 103, :Barstow 73, :Los-Angeles 65}
   :Los-Angeles {:Mojave 94, :Wheeler-Ridge 88, :Santa-Barbara 95, :San-Diego 121, :San-Bernardino 65}
   }
  )

(def nodes2
  {
   :A {:B 4, :C 5, :D 8},
   :B {:A 4, :D 3, :E 12},
   :C {:A 5, :D 1, :F 11},
   :D {:A 8, :B 3, :C 1, :E 9, :F 4, :G 10},
   :E {:B 12, :D 9, :G 6, :H 10},
   :F {:C 11, :D 4, :G 5, :I 11},
   :G {:D 10, :E 6, :H 3, :F 5, :I 5, :J 15},
   :H {:E 10, :G 3, :J 14},
   :I {:F 11, :G 5, :J 8},
   :J {:H 14, :G 15, :I 8}
   }
  )
; Gets the miles for the current node
; Checks if a node is unvisited and if it is, then calculates the distance to it
; This is repeated for each connected node
; Returns a map of all possible next nodes, with their distances
(defn update-miles [routes miles unvisited node]
  (let [node-miles (get miles node)]
    (reduce-kv
      (fn [m n n-m]
        (if (unvisited n)
          (update-in m [n] min (+ node-miles n-m))
          m))
      miles
      (get routes node)
    )
  )
)

; Miles =  A map of neighbouring nodes from current node with distance associated to each one, if no connection, then place-holder of infinitiy is used
; Node = current node
; Unvisited = list of nodes that have not been visited yet
; If destination is found, then return total miles to distance
; If every node has been visited or the current node has not cost associated to it, then return the miles map
; Next-miles = updated version of miles with the next possible node paths contained
; Next-node = the next shortest possible path
; Recursive call with the updated miles map and the next possible node, with the next node removed from the unvisited map
(defn dijkstra2 [routes start dest]
  (loop [miles (assoc (zipmap (keys routes)(repeat place-holder)) start 0)
         node start
         unvisited (disj (apply hash-set (keys routes)) start)]
    (cond
      (= node dest) (assoc nil start (select-keys miles[dest]))
      (or (empty? unvisited)(= place-holder (get miles node))) miles
      :else
      (let [next-miles (update-miles routes miles unvisited node) next-node (apply min-key next-miles unvisited)]
        (recur next-miles next-node (disj unvisited next-node))
      )
    )
  )
)

