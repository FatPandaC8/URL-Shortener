# Bug Tracking
Record of all the bugs encounter with detailed fix of them here
---
(18/11/2025): 
  - Bug     : The same url does not map to the same shortcode (Logic_Mapping)
  - Category: Logic Bug
  - Example : www.bitly_example.com will make 2 different shortcode on different attempt
  - State   : Fixed
  - Thought : In industry, it is deterministic (generate the same shortcode if given the same url)

(20/11/2025):
  - Inspecting the Logic_Mapping bug found on 18/11/2025: 
  + In the database                                     : the id suddenly jumps up to 52, 53, ... at index 6
  + Prob fix                                            : make sure to search the table first (but how to optimize this ?)
  => Make the service find in the database before making a new URLEntity.

  - Encounter HTTP 400 status code (Bad Request) while fixing:
  + It indicates the server would not process the request due to something the server considered to be a client error
  + Typically due to malformed reques syntax
  => Because of the change from longURL to originalURl is not made in the index.html.

  => Logic_Mapping is FIXED

(25/11/2025)
  - Bug : org.attoparser.ParseException: Exception evaluating SpringEL expression: "url.id" (template: "index" - line 25, col 13) -> HTML_Mapping
  - Category : Syntax Bug
  - State : Fixed 
  - Reason: thread -> thead of html
  - Solution: fix syntax in html and ./mvnw clean install (important)
  
  - Bug : if the url is checked as private -> it cannot be mark as unchecked if input again
  - Category : Feature Bug
  - Fixed 

(26/11/2025)
  - Optimization: select ue1_0.userid,ue1_0.created_at,ue1_0.email,ue1_0.password,ue1_0.role_num,ue1_0.username from users ue1_0 where ue1_0.userid=? 
  - Why : everytime we get all the public url, we do this which select everything, which can be very time consuming
  - Suggest 1: limit the select using a custom query by @Query
  - Suggest 2: Lazy loading + Eager loading 
  
(27/11/2025)
  - Bug: The resource from “http://localhost:9090/login” was blocked due to MIME type (“text/html”) mismatch (X-Content-Type-Options: nosniff). 
  - Category: Security Bug (i think so) 
  - Not a Security Bug (in this case) as i just put javascript files on the wrong directory
  
(29/11/2025)
  - Observation: every time, i go back and forth between endpoints -> it loads the database -> heavy on database 
  - Solution 1: Pageable (shows a page of a limited view of the database) -> result in the websites css is a bit messed up ?
  - Solution 2: Combine that with Lazy Loading 