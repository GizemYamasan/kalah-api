## Kalah API Challenge for Backbase

@author: Gizem Yamasan Acargil

### Implementation

All game logic was implemented in domain package in KalahGame class. Rest api is 
exposing this logic as requested in problem statement. GameDTO class is 
representation of KalahGame class to encapsulate internals from clients. Unit tests were 
implemented for api, service, and domain. 

### How to build and run 

`mvn clean install && java -jar target/kalah-api-0.1.jar `

or 

`./build_and_run.sh`

### How to test

test script will create a game and make move on pit 2 and then pit 9

`./test.sh`

sample output:

```
## KALAH GAME id=1 ##
{"id":"1","url":"http://localhost:8080/games/1"}
## FIRST MOVE id=1 pit=2 ##
{"id":"1","url":"http://localhost:8080/games/1","status":{"0":"6","1":"0","10":"6","11":"6","12":"6","13":"0","2":"7","3":"7","4":"7","5":"7","6":"1","7":"7","8":"6","9":"6"}}
## SECOND MOVE id=1 pit=9 ##
{"id":"1","url":"http://localhost:8080/games/1","status":{"0":"7","1":"0","10":"7","11":"7","12":"7","13":"1","2":"7","3":"7","4":"7","5":"7","6":"1","7":"7","8":"0","9":"7"}}
```
