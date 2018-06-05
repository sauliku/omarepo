// Build command
def doBuild() {
    sh 'printenv'
   // unstash "sources"
   // dir("$params.dir") {
        sh """#!/bin/bash
        echo $dir
   //         $params.preparationCommands
   //         make all
        """
    }
}

// Test command
def doTest() {
    dir("$params.dir") {
        echo "doTest: $dir "
    //    sh """#!/bin/bash
	//	    echo $dir
	//		robot login_tests.robot
        """
    }
}

// Notification
def notifyFailed() {

  hipchatSend (color: 'RED', notify: true,
      message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
    )
   emailext (
       subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
       body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
         <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
       recipientProviders: [[$class: 'DevelopersRecipientProvider']]
     )
 }


pipeline {
    agent none
    stages {
        stage("Checkout") {
            agent any
            steps {
			script {
				if ("$gitlabSourceRepoName" == 'adaptation') {
				echo "$gitlabSourceBranch"
				echo 'Dev branch from GitLab'
					dir("adaptation") {
					git url: "ssh://git@sccdaacp.prj.symbio.com:8022/scc/adaptation.git", branch: "$gitlabSourceBranch", credentialsId: "jenkins-slave-0"
					}
					dir("scclib") {
					git url: "ssh://git@sccdaacp.prj.symbio.com:8022/scc/scclib.git", branch: "master", credentialsId: "jenkins-slave-0"
					}
					stash "sources"
				} else {
				echo 'SCC lib'
					dir("adaptation") {
                    git url: "ssh://git@sccdaacp.prj.symbio.com:8022/scc/adaptation.git", branch: "master", credentialsId: "jenkins-slave-0"
                }
					dir("scclib") {
					git url: "ssh://git@sccdaacp.prj.symbio.com:8022/scc/scclib.git", branch: "$gitlabSourceBranch", credentialsId: "jenkins-slave-0"
                    }
					stash "sources"
				}
				}
            }
        }
        stage("Build") {
            parallel {
                stage("Release") {
                    agent { label "$params.label" }
                    steps {
                        withEnv(["export SCCTARGET=$params.target"]) {
                            doBuild(); 
					 }
                    }
                    post {
                        always {
                            deleteDir()

                        }
                        failure {
                            notifyFailed()
							updateGitlabCommitStatus name:'Build', state: 'failed'
                        }
						success {
							updateGitlabCommitStatus name:'Build', state: 'success'
						}
                    }
                }
		stage("Debug") {
                    agent { label "$params.label" }
                    steps {
                        withEnv(["DEBUG=1"]) {
                            doBuild();
					}
                    }
                    post {
                        always {
                            deleteDir()
                        }
                        failure {
                            notifyFailed()
							updateGitlabCommitStatus name:'Debug', state: 'failed'
                        }
							success {
							updateGitlabCommitStatus name:'Debug', state: 'success'
						}
                    }
                }
    //    stage('SonarQube analysis') {
    //        agent { label "master" }
    //        steps {
    //            script {
            // requires SonarQube Scanner 2.8+
    //        scannerHome = tool 'ScannerRunner'
    //            }
    //            withSonarQubeEnv('ScannerRunner') {
    //      sh "${scannerHome}/bin/sonar-scanner -X -D sonar.host.url='https://sccdaacp.prj.symbio.com/sonarqube/' -D sonar.oauth='646bf00752dd6bdcd988c8658ca4afecc8c8f4e7'"
    //    }
    //  }
//    }
		stage("Test") {
                agent { label "$params.label" }
                    steps {
                        withEnv(["export SCCTARGET=$params.target"]) {
                            //doTest();
							echo "Testi"
                        }
						//step([$class: 'RobotPublisher', disableArchiveOutput: false, logFileName: 'log.html', otherFiles: '', outputFileName: 'output.xml', outputPath: '/home/scc/RobotFW_cdemo/CDemo/', passThreshold: 100, reportFileName: 'report.html', unstableThreshold: 0])
                    }
					  post {
                        always {
                            deleteDir()
                        }
                        failure {
                            notifyFailed()
							updateGitlabCommitStatus name:'Test', state: 'failed'
                        }
							success {
							updateGitlabCommitStatus name:'Test', state: 'success'
						}
                    }
                }
            }
        }
    }
}