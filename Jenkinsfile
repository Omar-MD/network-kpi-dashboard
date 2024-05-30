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
        checkout([$class: 'GitSCM', branches: [[name: '*/dev']], userRemoteConfigs: [[url: 'file:///network-kpi-dashboard']]])
      }
    }

    stage('Clean & Build') {
      steps {
        sh './mvnw clean package -DskipTests -Ddockerfile.skip'
      }
    }

    stage('Unit Tests') {
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
              -Dsonar.exclusions=**/*.js,**/*.html,**/*.css \
              -Dsonar.host.url=${SONARQUBE_URL} \
              -Dsonar.token=${SONARQUBE_TOKEN} \
              -Dsonar.java.binaries=kpi-consumer/target/classes,kpi-producer/target/classes,kpi-dashboard/target/classes \
              -Dsonar.java.libraries=kpi-consumer/target/*.jar,kpi-producer/target/*.jar,kpi-dashboard/target/*.jar \
              -Dsonar.coverage.jacoco.xmlReportPaths=kpi-consumer/target/site/jacoco/jacoco.xml,kpi-producer/target/site/jacoco/jacoco.xml,kpi-dashboard/target/site/jacoco/jacoco.xml"
        }
      }
    }

    stage('Build Images') {
      steps {
          sh './mvnw dockerfile:build --projects=kpi-consumer,kpi-producer,kpi-dashboard'
      }
    }

    stage('Push Images to Docker Hub') {
      steps {
        script {
            sh './mvnw dockerfile:push --projects=kpi-consumer,kpi-producer,kpi-dashboard -Ddockerfile.useMavenSettingsForAuth=true'
        }
      }
    }
  }

  post {
    always {
      cleanWs()
    }
  }
}
