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
        parameters {
            choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
        }

        stages {
            stage('Code Quality') {
                steps {
                    sh 'sonar-scanner -Dsonar.projectkey=${component} -Dsonar.host.url=http://172.31.47.250:9000'
                }
            }
            stage('Unit test cases') {
                steps {
                    sh 'echo Unit Test'
                }
            }
            stage('CheckMarx SAST Scan') {
                steps {
                    sh 'echo CheckMarx Scan'
                }
            }
            stage('CheckMarx SCA Scan') {
                steps {
                    sh 'CheckMarx SCA Scan'
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