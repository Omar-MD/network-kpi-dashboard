# network_kpi_dashboard
Real Time Network KPI Dashboard Service

1- Kafka Service:
// To start the service
cd kpi-publisher/
docker compose -f=Kafka-docker-compose.yaml up -d

// To stop and delete resource run: 
docker compose -f=kafka-docker-compose.yaml down

// To Use the Kafka UI:
http://localhost:8090/