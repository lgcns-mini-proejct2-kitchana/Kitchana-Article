pipeline {
    agent any

    tools {
        gradle 'gradle8.12.1'
        jdk 'jdk17'
    }

    environment {
        GIT_TARGET_BRANCH = 'main'
        GIT_REPOSITORY_URL = 'https://github.com/lgcns-mini-proejct2-kitchana/Kitchana-Article.git'
    }

    parameters {
        string(name: 'TAG', defaultValue: 'latest', description: 'Docker Image Tag')
    }

    stages {
        stage('Github checkout') {
            steps {
                script {
                    echo "Cloning Repository"
                    git branch: "${GIT_TARGET_BRANCH}", url: "${GIT_REPOSITORY_URL}"
                }
            }
        }

        stage('Gradle Build') {
            steps {
                script {
                    echo "Starting Gradle Build"
                    sh 'chmod +x ./gradlew'
                    sh './gradlew clean build -x test'
                }
            }
        }

        stage('Build Docker Image & Push to ECR') {
            steps {
                script {
                    def tag = (params.TAG == 'latest' || params.TAG.trim() == '') ? env.BUILD_NUMBER : params.TAG
                    
                    sshPublisher(publishers: [
                        sshPublisherDesc(
                            configName: 'kitchana-docker',  // EC2에 대한 SSH 구성 이름
                            transfers: [
                                sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    execCommand: """
                                        docker build -t ${env.AWS_ECR_URI}/kitchana/article:${tag} -f ./inner/DockerfileArticle ./inner
    
                                        docker push ${env.AWS_ECR_URI}/kitchana/article:${tag}
                                    """,
                                    execTimeout: 180000,
                                    flatten: false,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    patternSeparator: '[, ]+',
                                    remoteDirectory: './inner',
                                    remoteDirectorySDF: false,
                                    removePrefix: 'build/libs',
                                    sourceFiles: 'build/libs/article-0.0.1-SNAPSHOT.jar'  // 'article' 관련 JAR만 선택
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false,
                            verbose: false
                        )
                    ])
                }
            }
        }
        
        // *** Deploy 단계 추가
        stage('Deploy to EC2') {
            steps {
                script {
                    def tag = (params.TAG == 'latest' || params.TAG.trim() == '') ? env.BUILD_NUMBER : params.TAG

                    sshPublisher(publishers: [
                        sshPublisherDesc(
                            configName: 'kitchana-docker',
                            transfers: [
                                sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    sourceFiles: 'deploy-article.sh',
                                    removePrefix: '',
                                    remoteDirectory: '/tmp',
                                    execCommand: """
                                        cd /home/ec2-user/tmp
                                        chmod +x deploy-article.sh
                                        export TAG=${tag}
                                        export CONTAINER_NAME=kitchana-article
                                        ./deploy.sh
                                    """,
                                    execTimeout: 180000,
                                    flatten: false,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    patternSeparator: '[, ]+'
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false,
                            verbose: false
                        )
                    ])
                }
            }
        }
    }

    post {
        success {
            echo 'pipeline succeeded'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}
