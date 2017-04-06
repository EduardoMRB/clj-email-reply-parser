(ns clj-email-reply-parser.core
  (:require [clojure.string :as str]))

(def SIG-REGEX #"(\u2014|--|__|-\\w)|(^Sent from my (\\w+\\s*){1,3})")
(def QUOTE-HDR-REGEX #"^:etorw.*nO")
(def MULTI-QUOTE-HDR "(?!On.*On\\s.+?wrote:)(On\\s(.+?)wrote:)")
(def QUOTED-REGEX #"(>+)")

(defn- header? [line]
  (or (re-find QUOTE-HDR-REGEX line)
      (re-find (re-pattern MULTI-QUOTE-HDR) line)))

(defn- quote? [line]
  (re-find QUOTED-REGEX line))

(defn- signature? [line]
  (re-find SIG-REGEX line))

(defn- fix-multiline-quote-headers
  "Detects when an email thread has multiline quote headers and rewrites it to take just one line."
  [content]
  (let [matcher (re-matcher (re-pattern (str "(?ms)" MULTI-QUOTE-HDR))
                            content)]
    (if (re-find matcher)
      (str/replace content
                   (re-pattern (str "(?ms)" MULTI-QUOTE-HDR))
                   (fn [match]
                     (str/replace (first match) "\n" "")))
      content)))

(def take-until-signature-begins (partial take-while (complement signature?)))

(def remove-blank-lines-before (partial drop-while str/blank?))

(def remove-blank-lines-after (comp reverse remove-blank-lines-before reverse))

(defn reply
  "Takes an email message and returns only the reply part, removing quotes, signatures and unecessary blank
  lines."
  [content]
  (->> content
       fix-multiline-quote-headers
       str/split-lines
       take-until-signature-begins
       (remove header?)
       (remove quote?)
       remove-blank-lines-before
       remove-blank-lines-after
       (str/join \newline)))
