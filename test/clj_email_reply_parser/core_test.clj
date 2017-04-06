(ns clj-email-reply-parser.core-test
  (:require [clojure.test :refer :all]
            [clj-email-reply-parser.core :refer :all]))

(defn- email [k]
  (slurp (str "resources/email_" k ".txt")))

(deftest email-1-4-test
  (is (= "Awesome! I haven't had another problem with it."
         (reply (email "1_4")))))

(deftest email-1-3-test
  (is (= "Oh thanks.

Having the function would be great.

-Abhishek Kona"
         (reply (email "1_3")))))

(deftest email-1-2-test
  (is (= "Hi,

You can list the keys for the bucket and call delete for each. Or if you
put the keys (and kept track of them in your test) you can delete them
one at a time (without incurring the cost of calling list first.)

Something like:

        String bucket = \"my_bucket\";
        BucketResponse bucketResponse = riakClient.listBucket(bucket);
        RiakBucketInfo bucketInfo = bucketResponse.getBucketInfo();
        
        for(String key : bucketInfo.getKeys()) {
            riakClient.delete(bucket, key);
        }


would do it.

See also 

http://wiki.basho.com/REST-API.html#Bucket-operations

which says 

\"At the moment there is no straightforward way to delete an entire
Bucket. There is, however, an open ticket for the feature. To delete all
the keys in a bucket, you’ll need to delete them all individually.\""
         (reply (email "1_2")))))

(deftest email-1-1-test
  (is (= "Hi folks

What is the best way to clear a Riak bucket of all key, values after 
running a test?
I am currently using the Java HTTP API.

-Abhishek Kona"
         (reply (email "1_1")))))

(deftest email-1-5-test
  (is (= "One: Here's what I've got.

- This would be the first bullet point that wraps to the second line
to the next
- This is the second bullet point and it doesn't wrap
- This is the third bullet point and I'm having trouble coming up with enough
to say
- This is the fourth bullet point

Two:
- Here is another bullet point
- And another one

This is a paragraph that talks about a bunch of stuff. It goes on and on
for a while."
         (reply (email "1_5")))))

(deftest email-1-6-test
  (is (= "I get proper rendering as well.

Sent from a magnificent torch of pixels"
         (reply (email "1_6")))))

(deftest email-1-7-test
  (is (= ":+1:"
         (reply (email "1_7")))))

(deftest email-driftt-1-test
  (is (= "hey notifications!"
         (reply (slurp "resources/driftt_1.txt")))))

(deftest email-partial-quote-header
  (is (= "On your remote host you can run:

     telnet 127.0.0.1 52698

This should connect to TextMate (on your Mac, via the tunnel). If that 
fails, the tunnel is not working."
         (reply (email "partial_quote_header")))))

(deftest email-one-is-not-on-test
  (is (= "Thank, this is really helpful.

One outstanding question I had:

Locally (on development), when I run..."
         (reply (email "one_is_not_on")))))
