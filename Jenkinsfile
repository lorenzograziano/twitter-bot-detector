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
                echo "SUCCESS"

            }
      }


      stage('postBuild') {
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
}


