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

    stage('codeCoverage') {
     agent {
        docker {
          image 'hseeberger/scala-sbt'
          args '-v docker-sbt-home:/docker-java-home'
        }
     }
    //launch coverage test
      steps {
         sbt compile'
         echo "currentBuild status: ${currentBuild.result}"
      }
    }

    stage('postCodeCoverage') {

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

          if (env.CONTINUE == 'false') {
             echo 'Exit from jenkins pipeline'
             exit 1
          } else {
            echo 'Continue jenkins pipeline execution'
          }
        }
      }
    }

    stage('test3') {
      steps {
        script {
          if (env.BRANCH_NAME == 'master') {
            echo 'You are on master branch'
          } else {
            echo 'you are on another branch'
          }
        }
      }
    }
  }
}