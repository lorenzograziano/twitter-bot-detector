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
            def ESITO='SUCCESS!'

          }
          failure {
            echo 'Failure!'
            def ESITO='Failure!'

          }
          unstable {
            /*input {
                message "coverage test not passed, do you want to continue anyway?"
                ok "Yes"
                parameters {
                    string(name: 'CONTINUE', defaultValue: 'No')
                }
            }
            steps {
                echo "Hello, ${CONTINUE}, nice to meet you."
              }*/
              def ESITO='UNSTABLE!'

          }
    }
    stage('postBuild') {
          agent none
          steps {
            echo ${ESITO}
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
}


