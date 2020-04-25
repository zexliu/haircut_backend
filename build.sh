#!/usr/bin/env bash

gradle build -x test

docker build -t zexliu/haircut:latest .

docker push zexliu/haircut:latest

