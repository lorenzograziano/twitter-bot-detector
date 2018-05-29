pipeline {
  agent none
  stages {
    stage('build') {
      agent {
        docker {
          image 'hseeberger/scala-sbt'
          args '-v docker-sbt-home:/docker-java-home'
        }

      }
      steps {
        sh 'ls'//'sbt compile'
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
     stage('test3') {
        steps {
            script {
        echo "Hello, ${CONTINUE}, nice to meet you."
                if ( ${CONTINUE} == 'master') {
                        echo 'I only execute on the master branch'
                } else {
                        echo 'I execute elsewhere'
                }
            }
        }
     }
  }
}