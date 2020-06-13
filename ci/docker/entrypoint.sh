#!/bin/sh
echo "------------------------------"
echo "Main Class: ${MAIN_CLASS}"
echo "------------------------------"
java -cp app:app/lib/* "${MAIN_CLASS}"
