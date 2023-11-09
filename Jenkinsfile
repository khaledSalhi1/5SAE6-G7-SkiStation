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
                // Run Maven clean and compile commands
                sh 'mvn clean'
            }
        }

        stage('COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('SONARQUBE Analysis') {
            steps {
                // Run SonarQube analysis
                withSonarQubeEnv('Sonarqube') {
                    sh 'mvn sonar:sonar -Dsonar.login=squ_3b9afa1e715d52c1dc99879e10d15c42c24bde58'
                }
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
                sh 'docker push khaledsalhi/khaledsalhi-5sae6-g7-skistation'
            }
        }

        stage('Docker Compose'){
            steps {
                // Lancer Docker-Compose
                sh 'docker compose up -d'
            }
        }
        stage('Jacoco') {
                    steps {
                        sh 'mvn jacoco:prepare-agent test jacoco:report'
                        archiveArtifacts artifacts: 'target/site/jacoco/**/*', allowEmptyArchive: true
                    }
        }
        stage('JUNIT/MOCKITO') {
                    steps {
                        sh 'mvn test'
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