(ns metabase.models.card-favorite
  (:require [korma.core :refer :all]
            [metabase.db :refer :all]
            (metabase.models [card :refer [Card]]
                             [user :refer [User]])
            [metabase.util :refer [new-sql-timestamp]]))

(defentity CardFavorite
  (table :report_cardfavorite))

(defmethod post-select CardFavorite [_ {:keys [card_id owner_id] :as card-favorite}]
  (assoc card-favorite
         :owner (delay (sel :one User :id owner_id))
         :card  (delay (sel :one Card :id card_id))))

(defmethod pre-insert CardFavorite [_ card-favorite]
  (let [defaults {:created_at (new-sql-timestamp)
                  :updated_at (new-sql-timestamp)}]
    (merge defaults card-favorite)))
