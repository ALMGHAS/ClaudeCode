pipeline {
    agent { label 'Linux-slave-1' }
    tools {
        jdk 'Java21'
        maven 'Maven-3.8.7'  
    }
    environment {
        SONAR_PROJECT_KEY = 'bookmyseat-movie-service-Backend'
        SONAR_CACHE = "${env.WORKSPACE}/.sonar-cache"
        DOCKER_IMAGE = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/movieapp-backend:${BUILD_NUMBER}"
        AWS_REGION = "${AWS_REGION}"
        AWS_ACCOUNT_ID = "${AWS_ACCOUNT_ID}"
        
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'git@github.com:ALMGHAS/bookmyseat-movie-service.git',
                        credentialsId: 'BookmySeat-service-ssh-cred'
                    ]]
                ])
            }
        }
        /*
        stage('Restore Cache & Install Dependencies') {
            steps {
                script {
                    // Restore Maven cache if stashed from previous run
                    if (unstash('maven-cache')) {
                        echo "Restored Maven cache from previous run"
                    } else {
                        sh 'mvn dependency:resolve'  // Install/resolve dependencies
                    }
                }
            }
        }
        */
        stage('Build') {
            steps {
                sh 'mvn clean compile -DskipTests'  // Compile sources
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'  // Run unit tests and generate JaCoCo coverage report
            }
            post {
                always {
                    // Publish JaCoCo coverage report
                    jacoco(
                        execPattern: 'target/site/jacoco.exec',
                        classPattern: 'target/classes',
                        sourcePattern: 'src/main/java',
                        inclusionPattern: '**/*.class'
                    )
                    // Publish JUnit test results
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('SAST Scan') {
            steps {
                script {
                    sh "mkdir -p ${SONAR_CACHE}"
                    withSonarQubeEnv('SonarQube1') {  // Assumes SonarQube server configured in Jenkins
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.projectName=movie-service \
                            -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.sources=src/main/java \
                            -Dsonar.tests=src/test/java \
                            -Dsonar.java.binaries=target/classes \
                            -Dsonar.junit.reportPaths=target/surefire-reports \
                            -Dsonar.jacoco.reportPaths=target/site/jacoco/jacoco.xml \
                        """
                    }
                    timeout(time: 5, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true  // Fails build if quality gate fails
                    }
                }
            }
        }
        
    }
}
