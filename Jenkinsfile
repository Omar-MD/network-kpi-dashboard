pipeline {
  agent any

  tools {
    maven 'maven-3.9.7' // Use the global Maven tool named 'maven-3.9.7'
  }

  stages {
    stage('Compile and package') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }
  }
}