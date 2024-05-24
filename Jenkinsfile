pipeline {
  agent any

  environment {
    REPO_URL = 'https://github.com/Omar-MD/network-kpi-dashboard.git'
    BRANCH = 'dev'
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: "${BRANCH}", url: "${REPO_URL}"
      }
    }

    stage('Test Setup') {
      steps {
        echo "Hello"
      }
    }
  }
}
