# exoscale-application-http-api

## A simple HTTP API

### Objective

We define a mock JSON HTTP API for a minimalist job board application. The API exposes the following routes:

  • GET /jobs: Returns a map of open positions in the job board.
  • POST /jobs: Inserts a new open position in the job board and returns the updated map.
  • DELETE /jobs/:id: Removes an open position from the job board and returns the updated map.

Job IDs should be textual representations of UUIDs. Jobs should have a company, title, and description field.

### Assignment details

The project should be delivered as a leiningen project, starting the API server on localhost port 8080 when launched with lein run.

Storage of jobs should be limited to in-memory, and functional boundaries should be split across clearly defined components within the
daemon.

No automated tests will be ran, and attention will be given to choices made in terms of dependencies and code architecture.


## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2019 FIXME
