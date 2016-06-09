#!groovy

stage 'build'
node {

    echo "Checking out the code from Github"
    checkout scm

    echo "Building and running the unit tests for the project"
    sh 'sh JenkinsAndroidTest.sh'

    echo "Publishing Unit Tests results"
    step([$class: 'JUnitResultArchiver', testResults: '**app/build/test-results/release/TEST-*.xml'])
    step([$class: 'JUnitResultArchiver', testResults: '**app/build/reports/*.xml'])
}
