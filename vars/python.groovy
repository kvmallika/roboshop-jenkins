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
                    sh 'echo Code Quality'
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