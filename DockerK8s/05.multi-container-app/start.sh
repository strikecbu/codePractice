#!/bin/bash
docker run -d --rm --name mongodb -v mongodata:/data/db --network goal-net mongo
docker run --name goal-backend -v "/Users/andy/Documents/lesson/DockerK8s/05.multi-container-app/backend:/app:ro" -v "/Users/andy/Documents/lesson/DockerK8s/05.multi-container-app/backend/logs:/app/logs" --network goal-net -d --rm -p 8080:80 goal-backend
docker run --name goal-frontend -p 3000:3000 -v "/Users/andy/Documents/lesson/DockerK8s/05.multi-container-app/frontend:/app:ro" -d --rm goal-frontend