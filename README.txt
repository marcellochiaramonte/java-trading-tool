Solution:

I created a Spring Boot project with the solution. I'm using a H2 in memory database for the querying.

Querying the DB on every entry is slow (400 ms instead of 10ms), so I created a Singleton-cache which is valid for 2 seconds, considering that changes to the DB might happen every 5 seconds, it's a reasonable compromise considering larger input files (this would be a problem if processing the data would take longer than the 5s updates to the DB, as some of the data would have a different modifier applied).

Consider that the modifier values at the H2 DB are different from 1, so the mean of the values will be different than using the raw csv input data.

The first run takes a bit longer, likely to starting the connection to the DB.


To run the project:
- install the dependencies (Intellij will likely automatically do this, but if not, maven install will also work)
- run "mvn spring-boot:run"
- on a browser or postman, query: http://localhost:8080/api/results
- time profiling and the results are sent as JSON


Marcello Chiaramonte