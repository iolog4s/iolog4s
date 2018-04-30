#!/usr/bin/env bash

#   !! IMPORTANT !!
# You need to run > sbt mkSite at least once for this to even work
# ---
# handy script to get the micro-site served without having to remember where it is stored
#

cd docs/target/site/
jekyll serve & open http://127.0.0.1:4000/iolog4s/index.html