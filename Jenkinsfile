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
        echo "Continue execution: ${CONTINUE}"
        script{
           if (${CONTINUE} == 'false') {
              echo 'I only execute on the master branch'
              exit 1
           } else { echo 'I execute elsewhere'}
        }
        echo "Continue execution: ${CONTINUE_EXECUTION}"

      }
    }
     stage('test3') {
        steps {
                echo "Hello, ${CONTINUE_EXECUTION}, nice to meet you."

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