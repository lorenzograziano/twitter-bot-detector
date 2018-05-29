pipeline {
  agent none
  stages {
    stage('Example') {
        input {
            message "Should we continue?"
            ok "Yes, we should."
            submitter "alice,bob"
            parameters {
                string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
            }
        }
        steps {
            echo "Hello, ${PERSON}, nice to meet you."
        }
    }

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
  post {
      success {
        echo 'SUCCESS!'
      }
      failure {
        echo 'Failure!'
      }
      unstable {
        echo 'Unstable!'
      }
  }
}

