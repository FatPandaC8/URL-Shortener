# Bug Tracking
Record of all the bugs encounter with detailed fix of them here
---
(18/11/2025): 
  - Bug     : The same url does not map to the same shortcode (Logic_Mapping)
  - Category: Logic Bug
  - Example : www.bitly_example.com will make 2 different shortcode on different attempt
  - State   : Not fixed
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
