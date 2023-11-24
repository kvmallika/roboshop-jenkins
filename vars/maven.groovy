def call(){
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }
        options {
            ansiColor('xterm')
        }

        stages {
            stage('Code Compile') {
                steps {
                    //sh 'mvn compile'
                    sh 'echo Code Compile'
                }
            }
            stage('Code Quality') {
                steps {
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.47.250:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true  -Dsonar.java.binaries=./target'
                    sh 'echo Code Quality'
                }
            }
            stage('Unit test cases') {
                steps {
                    sh 'echo Unit Test'
                    //sh 'mvn test'
                }
            }
            stage('CheckMarx SAST Scan') {
                steps {
                    sh 'echo CheckMarx Scan'
                }
            }
            stage('CheckMarx SCA Scan') {
                steps {
                    sh 'echo CheckMarx SCA Scan'
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }

    }
}