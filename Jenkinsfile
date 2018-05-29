pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'hseeberger/scala-sbt:latest'
        }

      }
      steps {
        sh 'sbt compile'
      }
    }
  }
}