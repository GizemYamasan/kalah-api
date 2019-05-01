#!/usr/bin/env bash

echo "## KALAH GAME id=1 ##"

curl -s --header 'Content-Type: application/json' --request POST http://localhost:8080/games

echo
echo "## FIRST MOVE id=1 pit=2 ##"

curl --header "Content-Type: application/json" --request PUT "http://localhost:8080/games/1/pits/2"

echo
echo "## SECOND MOVE id=1 pit=9 ##"

curl --header "Content-Type: application/json" --request PUT "http://localhost:8080/games/1/pits/9"
echo
