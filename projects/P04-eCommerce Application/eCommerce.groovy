pipeline {
    agent any
    stages {
        stage('clone') {
            steps {
                sh """
                git clone https://github.com/severina19/JDND.git
                """
            }
        }
        stage('build') {
            steps {
                sh """
                cd JDND/projects/P04-eCommerce\\ Application/starter_code
                /usr/local/bin/mvn package
                """
            }
        }
        stage('deploy') {
            steps {
                sh """
                cd JDND/projects/P04-eCommerce\\ Application/starter_code
                /usr/local/bin/mvn install:install-file -Dfile=target/auth-course-0.0.1-SNAPSHOT.jar -DgroupId=com.example.demo -DartifactId=myartifact -Dversion=0.1 -Dpackaging=jar
                """
            }
        }
    }
    post {
        always {
            deleteDir() /* clean up our workspace */
        }
    }
}
