# network_kpi_dashboard
Real Time Network KPI Dashboard Service

**Build and Start the Application:**
1- Prepare Jars
In application root i.e. network-kpi-dashboard/:
mvn clean package -DskipTest

2- Build and start services
docker compose up -d --build

3- Available UI's:
adminer DB view: http://localhost:8085/
- server: mysql
- username: admin
- password: root
- database: network_node_kpi

Kafka UI: http://localhost:8090/

4- To see the logs of  a service
docker logs {service-name}

4- To stop and delete all resource run: 
docker compose down

**Application Pipeline (Jenkins):**
url: http://13.51.233.71:8080/
username: admin
password: admin

Grafana: docker run -d --name grafana --network host grafana/grafana

defaults to port 3000

username: admin
password: admin

dashboard: https://snapshots.raintank.io/dashboard/snapshot/teN0eYATDhe9QWEfvybeFye6FkR3QIyG

