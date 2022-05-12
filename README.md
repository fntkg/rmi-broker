# rmi-broker
Implementation of a broker middleware with RMI Java

## How to use it

This project is composed of the broker, two server test programs and a client program.

### Broker

The broker consists of `Broker.java` (interface) and `BrokerImpl.java`. In order to work, you will need to launch `rmiregistry` in the machine you deploy the broker.

Compile and launch the broker:

```bash
javac Broker.java BrokerImpl.java
java BrokerImpl
```

The broker is now waiting for requests

### Servers

There are 2 programs to test servers: `TimeRetriever.java`, `TimeRetrieverImpl.java`, `DateRetriever.java` and `DateRetrieverImpl.java`.
Wherever you deploy this programs, you are going to need to launch `rmiregistry`.

This 2 programs register themselves in the broker and register a couple services. `DateRetriever` allow you to terminate a service and to register another one in runtime, you will see instructions on the console.

These programs are launched with the address of the server they are as the first and only argument, for example:

```bash
javac DateRetriever.java DateRetrieverImpl.java
java DateRetrieverImpl 192.168.1.18
```

### Client

The client is pretty easy to use. Just follow instructions on the console.

This program requires the broker address as argument in order to work. Compile and execute like this:

```bash
javac ClientC.java
java ClientC 192.168.1.15
```