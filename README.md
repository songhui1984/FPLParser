# FPLParser

FPLParser is a Java based Parser that converts the plain text messages which is compliant to ICAO Doc.4444 standard to Java Map<String,String>.

ICAO Doc.4444 is document about 'Procedures for Navigation Services' and defines message formats of following Messages for Flight.

  - FPL : Flight Plan
  - DEP : Departure 
  - ARR : Arrival
  - CNL : Cancellation
  - DLA : Delay
  - CHG : Change

### How to Build

FPLParser only requires maven to build, you can simply type:

```sh
$ mvn clean compile
To package compiled classes to jar file
$ mvn package
```



License
----

**Free Software, Please do not change package name for me!**


