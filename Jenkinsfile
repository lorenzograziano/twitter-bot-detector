pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'hseeberger/scala-sbt:latest'
          args '-v docker-sbt-home:/docker-java-home'
        }

      }
      steps {
        sh 'sbt compile'
      }
    }
    stage('postBuild') {
      input {
        message 'coverage test not passed, do you want to continue anyway?'
        id 'Yes'
        parameters {
          string(name: 'CONTINUE', defaultValue: 'No')
        }
      }
      steps {
        echo "Hello, ${CONTINUE}, nice to meet you."
      }
    }
  }
}