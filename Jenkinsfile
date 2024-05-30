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

    // stage('Unit Tests') {
    //   steps {
    //     sh './mvnw test'
    //   }
    // }

    // stage('SonarQube Analysis') {
    //   steps {
    //     withSonarQubeEnv('SonarQube') {
    //       sh "${SCANNER_HOME}/bin/sonar-scanner \
    //           -Dsonar.projectKey=network-kpi-dashboard \
    //           -Dsonar.sources=. \
    //           -Dsonar.exclusions=**/*.js,**/*.html,**/*.css \
    //           -Dsonar.host.url=${SONARQUBE_URL} \
    //           -Dsonar.token=${SONARQUBE_TOKEN} \
    //           -Dsonar.java.binaries=kpi-consumer/target/classes,kpi-producer/target/classes,kpi-dashboard/target/classes \
    //           -Dsonar.java.libraries=kpi-consumer/target/*.jar,kpi-producer/target/*.jar,kpi-dashboard/target/*.jar \
    //           -Dsonar.coverage.jacoco.xmlReportPaths=kpi-consumer/target/site/jacoco/jacoco.xml,kpi-producer/target/site/jacoco/jacoco.xml,kpi-dashboard/target/site/jacoco/jacoco.xml"
    //     }
    //   }
    // }

    // stage('Quality Gate Check') {
    //   steps {
    //     timeout(time: 5, unit: 'MINUTES') {
    //       waitForQualityGate abortPipeline: true
    //     }
    //   }
    // }

    stage('Build Images') {
      steps {
          sh './mvnw dockerfile:build --projects=kpi-consumer,kpi-producer,kpi-dashboard'
      }
    }

    stage('Start Containers') {
      steps {
        script {
            sh 'docker compose up -d'
        }
      }
    }

    stage('End-to-End Testing') {
      steps {
          script {
            sh 'sleep 120'
            def curlOutput = sh(script: 'curl -s localhost:8081/kpi', returnStdout: true).trim()
            if (curlOutput.isEmpty()) {
              error "Curl command returned an empty response."
            } else {
              echo "Curl output: ${curlOutput}"
            }
          }
      }
    }

    stage('Push Images to Docker Hub') {
      when {
        expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
      }
      steps {
        script {
          sh 'echo "Hello World!'
        }
      }
    }
  }

  post {
    always {
      // Stop and remove the Docker containers
      script {
          sh 'docker compose down'
      }
      // Clean workspace
      cleanWs()
    }
  }
}
