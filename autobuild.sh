#!/usr/bin/env bash

# requires: brew install entr

source ./cleanElm.sh

cd elm

ls `find . -name \*.elm -not -path \*elm-stuff*  -print` | entr sh -c 'clear; rm ../src/main/resources/flakeless.js; elm-make `find . -name \*.elm -not -path \*elm-stuff*  -print` --output ../src/main/resources/flakeless.js; cp ../src/main/resources/flakeless.js ../target/flakeless/flakeless.js'
