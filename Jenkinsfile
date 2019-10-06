pipeline {
    agent{label'master'}
    environment {
        USERNAME = '4514812'
        PASSWORD = 'r4514812L'
        ID= '4514812/ramalaso'
    }
    agent {dockerfile true}
    stages {
        stage ('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
            post{
                always {
                    junit 'build/test-results/test/*.xml'
                    publishHTML([allowMissing: false, 
                                alwaysLinkToLastBuild: false, 
                                keepAll: false, 
                                reportDir: 'build/reports/tests/test', 
                                reportFiles: 'index.html', 
                                reportName: 'Unit TestHTML Report', 
                                reportTitles: ''])
                }
                success {
                  archiveArtifacts 'build/libs/*.jar'
                  stash includes: 'build/libs/*.jar', name: 'package_build'
                  sh 'pwd'
                }
            }
        }
        stage('SonarQube') {
            steps {
                echo 'Sonarque'
                sh 'chmod +x gradlew'
                sh './gradlew sonarqube \
                -Dsonar.projectKey=JenkinsFile \
                -Dsonar.organization=ramalaso \
                -Dsonar.host.url=https://sonarcloud.io \
                -Dsonar.login=02bae6586821f9257fc57a4a601c3fccee980787' 
            }
        }
        stage ('Deploy to Dev'){
            agent{label'master'}
            steps {
                unstash 'package_build'
                sh 'docker-compose -f docker-compose.yaml up -d'
                sh 'docker-compose down || true'             
            }
        }
        stage ('Run Smoke Tests'){
            steps {
                echo 'Run Smoke Testing!!'
            }
        }
        stage ('Docker Build'){
            agent{label'master'}
           steps{
                unstash 'package_build'
                sh 'docker build -t ${ID}:v1.${BUILD_NUMBER} .'
                sh 'docker login -u ${USERNAME} -p ${PASSWORD}'
                sh 'docker push ${ID}:v1.${BUILD_NUMBER}'
            }
        }
        stage ('Promote to QA'){
            //agent{label'slave01'}
            steps {
                unstash 'package_build'
                echo 'Promoting to QA'
                sh 'docker-compose -f docker-compose-promote.yaml up -d'
                sh 'docker-compose -f docker-compose-promote.yaml down || true'
                
            }
        }
        stage ('Test'){
            steps {
                echo 'Run end to end test.'
            }
        }
    }
    post {
        always {
             echo 'Execute always'
        }
        failure {
            cleanWs deleteDirs: true, notFailBuild: true
            emailext attachLog: true, compressLog: true, body: 'The process to generate a new verion of ${BRANCH_NAME}. Log with the info is attached ',
                     subject: 'Build Notification: ${JOB_NAME}-Build# ${BUILD_NUMBER} ${currentBuild.result}',
                     to: 'raul.laredo@fundacion-jala.org'
        }
    }
} 