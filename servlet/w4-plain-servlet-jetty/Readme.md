# Recording Experience working with plain Jetty

- Got the `pebble` template which I am loving as it is almost as good as jinja2 and supports inheritance.
- Working with raw jakarta servlet api is also good but the whole request parsing is now left upto the user to do
  - What happens if the data is sent as `form-data` or `x-www-form-urlencoded` or `multipart`
  - Might have to reinvent a lot of stuff compared to even a simple framework like `javalin` or `jooby` etc...
  - Performance of javalin and other microframeworks seems to be good anyways
  - So why write so much code
  - ...
- The JVM footprint is pretty much as small as python :) 30 MB Heap and 30 MB Non Heap memory. Very good :)