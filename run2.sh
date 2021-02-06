#!/bin/bash
if [ $# -eq 0 ]; then
    echo "Provide input on console"
    java DriverBST
fi
if [ $# -eq 1 ]; then
    java DriverBST < "${1}"
fi
if [ $# -eq 2 ]; then
    java DriverBST < "${1}" > "${2}"
fi