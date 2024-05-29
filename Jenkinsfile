pipeline {
  agent any

  environment {
      // Use the environment variable for the SonarQube URL
      SONARQUBE_SERVER = 'SonarQube'
      SCANNER_HOME = tool 'SonarQube Scanner'
      SONARQUBE_URL = "${env.SONARQUBE_URL}"
      SONARQUBE_TOKEN = "${env.SONAR_TOKEN}"
  }

  stages {
    stage('Checkout') {
      steps {
        // Checkout code from the mounted workspace
        checkout([$class: 'GitSCM', branches: [[name: '*/dev']], userRemoteConfigs: [[url: 'file:///network-kpi-dashboard']]])
      }
    }

    stage('Clean & Build') {
      steps {
        sh './mvnw clean package -DskipTests -Ddockerfile.skip'
      }
    }

    stage('Test') {
      steps {
        sh './mvnw test'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh "${SCANNER_HOME}/bin/sonar-scanner \
              -Dsonar.projectKey=network-kpi-dashboard \
              -Dsonar.sources=. \
              -Dsonar.host.url=${SONARQUBE_URL} \
              -Dsonar.token=${SONARQUBE_TOKEN} \
              -Dsonar.java.binaries=kpi-consumer/target/classes,kpi-producer/target/classes,kpi-dashboard/target/classes \
              -Dsonar.java.libraries=kpi-consumer/target/*.jar,kpi-producer/target/*.jar,kpi-dashboard/target/*.jar \
              -Dsonar.coverage.jacoco.xmlReportPaths=kpi-consumer/target/site/jacoco/jacoco.xml,kpi-producer/target/site/jacoco/jacoco.xml,kpi-dashboard/target/site/jacoco/jacoco.xml"
        }
      }
    }

    stage('Quality Gate Check') {
      steps {
        script {
          def qg = waitForQualityGate()
          if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
          }
        }
      }
    }

    stage('Build Docker Image') {
      steps {
          sh './mvnw dockerfile:build'
      }
    }
  }
}
