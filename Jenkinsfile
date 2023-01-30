pipeline {
    agent { label 'SPRING' }
    triggers { pollSCM('* * * * *') }
    stages {
        stage('git') {
            steps {
                git branch: 'main',
                       url: 'https://github.com/maheshryali123/spring-petclinic.git'
            }
        }
        stage('build') {
            steps {
                sh """
                mvn package
                """
            }
        }
        stage('sonar') {
            steps {
                withSonarQubeEnv('sonarscan') {
                    sh 'mvn clean package'
                }
            }
        }
    }
}
