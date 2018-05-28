pipeline {
  agent {
    docker {
      image 'hseeberger/scala-sbt:latest'
    }

  }
  stages {
    stage('build') {
      steps {
        sh 'sbt'
      }
    }
  }
}