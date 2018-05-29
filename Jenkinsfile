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
      post {
          success {
            echo 'SUCCESS!'
            input {
                          message "coverage test not passed, do you want to continue anyway?"
                          ok "Yes"
                          parameters {
                              string(name: 'CONTINUE', defaultValue: 'No')
                          }
                      }
                      steps {
                          echo "Hello, ${CONTINUE}, nice to meet you."
                      }
          }
          failure {
            echo 'Failure!'
            input {
                          message "coverage test not passed, do you want to continue anyway?"
                          ok "Yes"
                          parameters {
                              string(name: 'CONTINUE', defaultValue: 'No')
                          }
                      }
                      steps {
                          echo "Hello, ${CONTINUE}, nice to meet you."
                      }
          }
          unstable {
              input {
                  message "coverage test not passed, do you want to continue anyway?"
                  ok "Yes"
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
    /*
        stage('test') {
                if (${CONTINUE} == 'Yes') {
                        echo 'I only execute on the master branch'
                } else {
                        echo 'I execute elsewhere'
                }
        }*/


  }

}


