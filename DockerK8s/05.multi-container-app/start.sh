#!/bin/bash
docker run -d --rm --name mongodb -e MONGO_INITDB_ROOT_USERNAME=andy -e MONGO_INITDB_ROOT_PASSWORD=password -v mongodata:/data/db --network goal-net mongo
docker run --name goal-backend -v "/Users/andy/Documents/lesson/DockerK8s/05.multi-container-app/backend:/app:ro" -e MONGODB_USER_PASSWORD=password -v "/Users/andy/Documents/lesson/DockerK8s/05.multi-container-app/backend/logs:/app/logs" --network goal-net -d --rm -p 8080:80 goal-backend
docker run --name goal-frontend -p 3000:3000 -v "/Users/andy/Documents/lesson/DockerK8s/05.multi-container-app/frontend:/app:ro" -d --rm goal-frontend