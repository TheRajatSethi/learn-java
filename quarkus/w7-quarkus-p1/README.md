# Readme

## State of this repo

Using quarkus authentication might work fine but the issue I see is that it is a very
specific way of using authentication. If the application will use it then it needs to
adopt all the way in. Else you have to roll out your own authentication else you have to 
be  very skilled in combining your authentication with quarkus's authentication system
and form based login or oidc based login etc... because we might have to override how
the framework behaves itself.

There are two options
- Roll own authentication
- Rely on Quarkus 

I'll leave the way this repo is in this state and create another project which will pick this up
from where this failed e.g. redirect/error page does not work

Will try both approaches
- Rely on Quarkus 
- Roll own authentication (Will get to learn much more especially in federated identity provider scenario like oauth etc...)

## Sqlite

Considerations for production.
- https://www.youtube.com/watch?v=yTicYJDT1zE&t=1228s
- use `strict` mode

Learn utilities
- sqlite command
- sqlite analyser
- sqlite diff


## JAX-RS Authentication

- Checkout this answer here - https://stackoverflow.com/a/26778123
- Quarkus Security Architecture - https://quarkus.io/guides/security-architecture
- Quarkus Security Tips & Tricks - https://quarkus.io/guides/security-customization