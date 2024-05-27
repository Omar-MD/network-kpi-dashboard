pipeline {
  agent any

  stages {
    stage('Compile and package') {
      steps {
        sh './mvnw clean package -DskipTests'
      }
    }
  }
}