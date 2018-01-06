#!/bin/bash

cd "$( dirname "${BASH_SOURCE[0]}" )/.."

cd web
yarn
yarn build

cd ../server
mvn clean install
mvn appengine:update
