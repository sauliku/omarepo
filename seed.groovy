pipelineJob('TestiJobi') {
    definition {
        cps {
            script(readFileFromWorkspace('Jenkinsfile_example'))
            sandbox()
        }
  
  logRotator {
    numToKeep(10)
    artifactNumToKeep(10)
    } 
   
     parameters {
       stringParam('target', 'Demo', 'target build')
       stringParam('label', 'kone1', 'label used in build env')
       stringParam('branch', 'master', 'used branch')
       stringParam('preparationCommands', 'source /opt/poky-st/2.2.1/environment-setup-cortexa7hf-neon-poky-linux-gnueabi', 'command for script')
       stringParam('dir', 'testihakemisto', 'build dir')
     }    
        triggers {
        scm('H/15 * * * *')
    }
    }
}
pipelineJob('TestiJobi2') {
    definition {
        cps {
            script(readFileFromWorkspace('Jenkinsfile_example'))
            sandbox()
        }
  
  logRotator {
    numToKeep(10)
    artifactNumToKeep(10)
    } 
   
     parameters {
       stringParam('target', 'Demo', 'target build')
       stringParam('label', 'kone1', 'label used in build env')
       stringParam('branch', 'master', 'used branch')
       stringParam('preparationCommands', 'source /opt/poky-st/2.2.1/environment-setup-cortexa7hf-neon-poky-linux-gnueabi', 'command for script')
       stringParam('dir', 'testihakemisto', 'build dir')
     }    
        triggers {
        scm('H/15 * * * *')
    }
    }
}

pipelineJob('TestiJobi3') {
    definition {
        cps {
            script(readFileFromWorkspace('Jenkinsfile_example'))
            sandbox()
        }
    
  logRotator {
    numToKeep(10)
    artifactNumToKeep(10)
    }   
    
       parameters {
       stringParam('target', 'Demo', 'target build')
       stringParam('label', 'kone1', 'label used in build env')
       stringParam('branch', 'master', 'used branch')
       stringParam('preparationCommands', 'source /opt/poky-st/2.2.1/environment-setup-cortexa7hf-neon-poky-linux-gnueabi', 'command for script')
       stringParam('dir', 'testihakemisto', 'build dir')
     }
  
  }     
    triggers {
     triggers {
        scm('H/15 * * * *')
    }
    }
}