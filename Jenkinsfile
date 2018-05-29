pipeline {
  agent none
      environment {
          DISABLE_AUTH = 'true'
          DB_ENGINE    = 'sqlite'
      }
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
                echo "Hello, ${DISABLE_AUTH}, nice to meet you."

            script {
                if (env.BRANCH_NAME == 'master') {
                        echo 'I only execute on the master branch'
                } else {
                        echo 'I execute elsewhere'
                }
            }
        }
     }
  }
}