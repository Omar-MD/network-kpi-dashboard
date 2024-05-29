# network_kpi_dashboard
Real Time Network KPI Dashboard Service

**Build and Start the Application:**

1- Prepare Jars
In application root i.e. network-kpi-dashboard/:
`# mvn clean package -DskipTests`

2- Build and start services
`# docker compose up -d`

3- Available UI's:
adminer DB view: http://localhost:8085/
- server: mysql
- username: admin
- password: root
- database: network_node_kpi

Kafka UI: http://localhost:8090/
Grafana UI: http:localhost:3000/
- dashboard: https://snapshots.raintank.io/dashboard/snapshot/Q2XoUlynukY7kIPvyTfAWXn1lwBoc9pp

4- To see the logs of  a service
`# docker logs {service container name} Ex. docker logs mysql_db`

4- To stop and delete all resource run: 
`# docker compose down`


**CI Pipeline (Jenkins & SonarQube):**

In project root folder run:
`# docker compose -f=docker-compose-CI.yaml up -d`

Jenkins URL: http://localhost:8089/
username: admin
password: admin

SonarQube URL: http://localhost:9000/
username: admin
password: sonar

To Run build on current working directory changes you'll need to commit any changes before re-running the pipeline.
