# portal-clj

A basic blog-oriented CMS as a Clojure learning project.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Before launching the server, make sure you have a running MySQL server, 
then change the dummy user credentials supplied in db.clj to an appropriate user/password.

To create the table and appropriate tables, run

    lein run -m portal-clj.models.migration up

To populate these tables with dummy data for testing, run

    lein run -m portal-clj.models.migration dummy

To start a web server for the application, run:

    lein ring server

## License

Copyright ©no one 2015
