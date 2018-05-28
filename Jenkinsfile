pipeline {
  agent {
    node {
      label 'node1'
    }

  }
  stages {
    stage('build') {
      steps {
        sh 'sbt'
      }
    }
  }
  environment {
    node1 = 'hseeberger/scala-sbt'
  }
}