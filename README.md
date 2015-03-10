# portal-clj

A basic blog-oriented CMS as a Clojure learning project.

## Prerequisites for Development

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

Before launching the server, make sure you have a running MySQL server,
then change the dummy user credentials supplied in db.clj to an appropriate user/password.

To create the table and appropriate tables, run

    lein run -m portal-clj.models.migration up

To populate these tables with dummy data for testing, run

    lein run -m portal-clj.models.migration dummy


Without Leiningen or Clojure installed, simply read through the
contents of portal-clj.models.migration and manually run the included SQL queries.

To start a web server for development, run:

    lein ring server

For proper deployment, download the most recent release .jar and run it, e.g.

    java -jar portal-clj-0.4.0-standalone.jar

## License

Unlicense (see `LICENSE`).

Copyright ©no one 2015
