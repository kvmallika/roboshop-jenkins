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
        environment {
            NEXUS = credentials('NEXUS')
        }
        stages {
            stage('Code Quality') {
                steps {
                    // sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.47.250:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                    sh 'echo Code Quality'
                }
            }
            stage('Unit test cases') {
                steps {
                    sh 'echo Unit Test'
                    //sh 'npm test'
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
            stage('Release Application') {
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'echo ${TAG_NAME} >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip *'
                    // deleting this file as it is not needed
                    //sh 'zip -d ${component}-${TAG_NAME}.zip Jenkinsfile'
                    //sh 'curl -f -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip http://172.31.32.14:8081/repository/${component}/${component}-${TAG_NAME}.zip'
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 689505382884.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker build -t 689505382884.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'
                    sh 'docker push 689505382884.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'
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