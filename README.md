# Simple Test System for Students

## Overview
The following repo contains simple application for tracking tests solved by students.

## More Info
1. When application starts, it creates some dummy students with some questions and tests. <br> Those initialization data can be queried by swagger web api (http://localhost:8080/api/swagger-ui/index.html)
2. On top of those dummy data, one can create his own students, questions, tests etc...
3. Again all the API documentation can be found in the link above.
4. All the urls for the application start with http://localhost:8080/api
5. H2 database and table contents can be accessed via http://localhost:8080/api/h2-ui (username = sa, there is no password)

## Disclaimers
* Since there are lots of business logic, not all those logics are covered with unit tests.
QuestionService logic are mostly covered to demonstrate unit testing skills.
* Caching solution is not added in the current endpoints. If one wants to achieve that it can simply be done by using either spring boot's in-memory cache solution together with @Cacheable annotation or Caffeine-cache (which gives you more control over eviction strategies).

## Support
Please enter an issue in the repo for any questions or problems.
<br> Alternatively, please contact me at batikanbirincioglu@gmail.com