# clj-email-reply-parser

Email Reply Parser for Clojure.

## Usage

```clojure
(require [clj-email-reply-parser.core :as reply-parser])

(def email-message "Yes that is fine, I will email you in the morning.

On Fri, Nov 16, 2012 at 1:48 PM, Driftt <support@driftt.com> wrote:

> Our support team just commented on your open Ticket:
> \"Hi Rico, can we chat in the morning about your question?\"")

(reply-parser/reply email-message)
;; => "Yes that is fine, I will email you in the morning."
```
