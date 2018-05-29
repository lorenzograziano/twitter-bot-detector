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
          environment {
               ESITO = "SUCCESS"
             }


          }
          failure {
            echo 'Failure!'
            ESITO="Failure!"

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
              ESITO="UNSTABLE!"

          }
    }
    stage('postBuild') {
          agent none
          steps {
            sh "echo ${ESITO}"
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


