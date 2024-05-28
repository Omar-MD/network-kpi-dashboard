/* groovylint-disable CompileStatic, DuplicateStringLiteral */
pipeline {
  agent any

  environment {
      // Use the environment variable for the SonarQube URL
      SONARQUBE_SERVER = 'SonarQube'
      SCANNER_HOME = tool 'SonarQube Scanner'
      SONARQUBE_URL = "${env.SONARQUBE_URL}"
  }

  stages {
    stage('Checkout') {
      steps {
        // Checkout code from the mounted workspace
        checkout([$class: 'GitSCM', branches: [[name: '*/dev']], userRemoteConfigs: [[url: 'file:///network-kpi-dashboard']]])
      }
    }

    stage('Clean, Test & Build') {
      steps {
        sh './mvnw clean package -Ddockerfile.skip'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh "${SCANNER_HOME}/bin/sonar-scanner \
                        -Dsonar.projectKey=network-kpi-dashboard \
                        -Dsonar.sources=. \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=admin \
                        -Dsonar.password=sonar \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.exclusions=**/*.class"
        }
      }
    }

    stage('Quality Gate') {
      steps {
        // Wait for SonarQube analysis to be completed and check the Quality Gate status
        timeout(time: 1, unit: 'HOURS') {
          waitForQualityGate abortPipeline: true
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
