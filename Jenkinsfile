import groovy.json.JsonSlurper
import hudson.triggers.SCMTrigger

node('maven_gev') {

    properties([
        parameters([
            gitParameter(
                name: 'BRANCH',
                branch: '',
                branchFilter: 'origin/(.*)',
                defaultValue: 'master',
                description: 'Select a Git branch to build',
                quickFilterEnabled: true,
                selectedValue: 'DEFAULT',
                sortMode: 'DESCENDING',
                type: 'PT_BRANCH'
            )
        ]),
        pipelineTriggers([
            [$class: 'SCMTrigger', scmpoll_spec: '* * * * *'],
            [$class: 'TimerTrigger', spec: '0 21 * * *']
        ])
    ])

    try {
        stage('Check Allure CLI') {
            sh 'allure --version'
        }

        stage('Checkout') {
            checkout([
                $class: 'GitSCM',
                branches: [[ name: "${params.BRANCH}" ]],
                userRemoteConfigs: [[ url: 'https://github.com/gbkocharyan/appium.git' ]]
            ])
        }

        stage('Run Mobile Tests') {
            // Prepare results folders
            sh "mkdir -p ${WORKSPACE}/allure-results ${WORKSPACE}/allure-report"
            sh "chown -R 1000:1000 ${WORKSPACE}/allure-results ${WORKSPACE}/allure-report"
            sh "chmod -R 777 ${WORKSPACE}/allure-results ${WORKSPACE}/allure-report"

            // Remove existing container if exists
            sh "docker rm -f appium || true"

            // Run container with full logging
            sh """
                echo === Starting Docker container ===
                docker run --name appium \
                  -v /var/run/docker.sock:/var/run/docker.sock \
                  -v jenkins_home:/var/jenkins_home \
                  -v ${WORKSPACE}/allure-results:/app/allure-results \
                  -v ${WORKSPACE}/allure-report:/app/allure-report \
                  localhost:5005/mobile_gev bash -c "
                      echo === Inside container ===;
                      whoami;
                      pwd;
                      echo '=== List /app before tests ===';
                      ls -la /app;
                      echo '=== Running Maven tests ===';
                      mvn clean test -DrunType=remote -Dsurefire.ignoreFailures=true -Dallure.results.directory=/app/allure-results || true;
                      echo '=== After tests ===';
                      ls -la /app/allure-results;
                  "
            """

            // Copy results from container
            sh "docker cp appium:/app/allure-results ${WORKSPACE}/ || true"

            // List results locally for debug
            sh """
                echo === Local Allure Results ===
                ls -la ${WORKSPACE}/allure-results
            """

            archiveArtifacts artifacts: 'allure-results/**', fingerprint: true

            // Clean up container
            sh "docker rm -f appium || true"
        }

    } finally {
        stage('Publish Allure & Notify') {
            // Generate Allure report
            allure([
                includeProperties: false,
                reportBuildPolicy: 'ALWAYS',
                results: [[ path: "${WORKSPACE}/allure-results" ]]
            ])

            // Debug Allure report contents
            sh """
                echo === Listing Allure Report folder ===
                ls -la ${WORKSPACE}/allure-report
            """

            try {
                def summaryFile = readFile("${WORKSPACE}/allure-report/widgets/summary.json")
                def summary = new JsonSlurper().parseText(summaryFile)

                def total = summary.statistic.total ?: 0
                def passed = summary.statistic.passed ?: 0
                def message = "📱 Mobile Test Execution Finished\\n" +
                              "✅ Passed: ${passed}/${total}\\n" +
                              "📊 Allure Report: ${env.BUILD_URL}allure"

                sh """
                   curl -s -X POST https://api.telegram.org/bot8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU/sendMessage \
                   -d chat_id=6877916742 \
                   -d text="${message}"
                """
            } catch (Exception e) {
                echo "Failed to read Allure summary: ${e}"
            }
        }
    }
}
