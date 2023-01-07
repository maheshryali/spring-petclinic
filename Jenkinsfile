pipeline {
    agent { label 'ALL' }
    triggers { pollSCM('* * * * *') }
    parameters { 
        choice(name: 'BRANCH', choices: ['main', 'dev', 'qa'], description: 'this is for selection of branch')
        string(name: 'MAVEN_BUILD', defaultValue: 'package',  description: 'write the goal')
        }
    stages {
        stage('git') {
            steps {
                mail subject: 'build started',
                  body: 'build started',
                  to: 'maheshmech9999@gmail.com'
                git branch: "${params.BRANCH}",
                       url: 'https://github.com/maheshryali/spring-petclinic.git'
            }
        }
        stage('build') {
            steps {
                sh """
                /opt/apache-maven-3.8.7/bin/mvn "${params.MAVEN_BUILD}"
                """
            }  
        }
        stage('artifactory') {
            steps {
                rtMavenDeployer (
                    id: "MAVEN_DEPLOYER",
                    serverId: "jfrog_artifact",
                    releaseRepo: "new_jenkins-libs-release",
                    snapshotRepo: "new_jenkins-libs-snapshot"
                )
            }
        }
        stage('maven') {
            steps {
                rtMavenRun (
                    tool: 'mvn-3.8.7',
                    pom: 'pom.xml',
                    goals: 'clean package',
                    deployerId: 'MAVEN_DEPLOYER'
                )
            }
        }
        stage('Publish Build Info') {
            steps {
                rtPublishBuildInfo (
                    serverId: "jfrog_artifact"
                )
            }
        }
        stage('sonar_scan') {
            steps {
                withSonarQubeEnv ('scan') {
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }
    }
    post {
        always {
            echo'Job completed'
            mail subject: 'Build Sucess',
                body: 'Build Succes',
                to: 'maheshmech9999@gmail.com'
        }
        failure {
            mail subject: 'Build Failure',
                body: 'Build Failure',
                to: 'maheshmech9999@gmail.com'
        }
        success {
            junit '**/surefire-reports/*.xml'
        }

    }
}