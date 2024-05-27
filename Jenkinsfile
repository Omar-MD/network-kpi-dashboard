pipeline {
  agent any

  tools {
    maven 'maven-3.9.7' // Use the global Maven tool named 'maven-3.9.7'
    jdk 'jdk11' // Use the global JDK tool named 'jdk11'
  }

  stages {
    stage('Compile and package') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }
  }
}