#!/bin/bash

echo "Insert object..."
curl -X POST -d '{"model": "tesla"}' -H "Content-Type: application/json"  http://localhost:80/cars/resources/cars
echo ""

sleep 1
echo "Select object via appsvr0 (populate cache)..."
curl http://localhost:80/cars/resources/cars/1
echo ""

sleep 1
echo "Select object via appsvr1 (populate cache)..."
curl http://localhost:81/cars/resources/cars/1
echo ""

sleep 1
echo "Update object via appsvr0 (update cache)..."
curl -X PUT -d '{"id":"1", "model": "tesla-x"}' -H "Content-Type: application/json"  http://localhost:80/cars/resources/cars
echo ""

sleep 1
echo "Select object via appsvr0 from cache..."
curl http://localhost:80/cars/resources/cars/1
echo ""

sleep 1
echo "Select object via appsvr1 from cache..."
curl http://localhost:81/cars/resources/cars/1
echo ""