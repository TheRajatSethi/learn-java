# w1-plain-servlet

**Important** - Jetty v12 has changes where the servlet api has been removed from its internals which may require more wiring thus this for now will only run on v11 of Jetty.


Build docker image
```shell
docker build . -t w1-plain-servlet
```

Run docker image
```shell
docker run --name w1-plain-servlet -p 8080:8080 w1-plain-servlet
```