pipeline {

  agent none

  environment {
     CONTINUE_EXECUTION = 'true'
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
          string(name: 'CONTINUE', defaultValue: 'false')
        }
      }
      steps {
        script {
          echo "Continue execution: ${CONTINUE}"
          echo "${env.CONTINUE}"
          if (env.CONTINUE == 'false') {
             echo 'I only execute on the master branch'
             exit 1
          } else {
            echo 'else'
          }
        }
      }
    }

    stage('test3') {
      steps {
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