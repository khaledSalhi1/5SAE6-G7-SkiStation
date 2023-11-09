pipeline {
    agent any

    stages {
        stage('GIT') {
            steps {

                checkout([
                            $class: 'GitSCM',
                                branches: [[name: 'KhaledSALHI-5SAE6-G7']],
                                userRemoteConfigs: [[
                                             url: 'https://github.com/khaledSalhi1/5SAE6-G7-SkiStation.git',
                                             credentialsId: '25'
                                                    ]]
                        ])
            }
        }

        stage('CLEAN') {
            steps {
                // Run Maven clean 
                sh 'mvn clean'
            }
        }

        stage('COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('NEXUS') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('docker image') {
            steps{
             sh 'docker build -t khaledsalhi/khaledsalhi-5sae6-g7-skistation .'
            }
        }

        stage('Docker Hub'){
            steps {
                // Deployer l'image
                sh 'docker login -u khaledsalhi -p khaledsalhi0012'
               // sh 'docker push khaledsalhi/khaledsalhi-5sae6-g7-skistation'
            }
        }

        stage('Docker Compose'){
            steps {
                // Lancer Docker-Compose
                sh 'docker login -u khaledsalhi -p khaledsalhi0012'
               // sh 'docker compose up -d'
            }
        }
        stage('JACOCO/JUNIT/MOCKITO') {
                    steps {
                        sh 'mvn jacoco:prepare-agent test jacoco:report'
                        archiveArtifacts artifacts: 'target/site/jacoco/**/*', allowEmptyArchive: true
                        jacoco(execPattern: '**/target/jacoco.exec', classPattern: '**/classes', sourcePattern: '**/src/main/java')
                    }
        }
        stage('SONARQUBE Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar -Dsonar.login=squ_3b9afa1e715d52c1dc99879e10d15c42c24bde58 -Dsonar.jacoco.reportPaths=target/jacoco.exec'
                }
            }
        }

        stage('Start Prometheus') {
                    steps {
                        script {
                            sh 'docker start prometheus'
                        }
                    }
        }
        stage('Start Grafana') {
                    steps {
                        script {
                            sh 'docker start grafana'
                        }  
                    }
        }
        

        stage('Mail') {
            steps {
                script {
                    emailext(
                        subject: "Pipeline Build Notification",
                        body: "Your Jenkins Pipeline build has completed.",
                        recipientProviders: [culprits(), developers()],
                        to: 'madrober001@gmail.com'
                    )
                    sleep(time: 10, unit: 'SECONDS')
                }
            }
        }
    }

}
