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
            sh "mkdir -p ${WORKSPACE}/allure-results ${WORKSPACE}/allure-report"

            sh """
                docker run --rm --name appium \
                  -v /var/run/docker.sock:/var/run/docker.sock \
                  -v jenkins_home:/var/jenkins_home \
                    -v ${WORKSPACE}/allure-results:/app/allure-results \
                    -v ${WORKSPACE}/allure-report:/app/allure-report \
                    localhost:5005/mobile_gev || true
            """

            // Copy results from container
          //  sh "docker cp mobile_tests:/app/target/allure-results ${WORKSPACE}/ || true"
            sh "docker cp mobile:/app/allure-results ${WORKSPACE}/ || true"
            archiveArtifacts artifacts: 'allure-results/**', fingerprint: true
            sh "docker rm -f mobile || true"
        }

    } finally {
        stage('Publish Allure & Notify') {
                allure([
                    includeProperties: false,
                    reportBuildPolicy: 'ALWAYS',
                    results: [[ path: "${WORKSPACE}/allure-results" ]]
                ])

                try {
                    def summaryFile = readFile("${WORKSPACE}/allure-report/widgets/summary.json")
                    def summary = new JsonSlurper().parseText(summaryFile)

                    def total = summary.statistic.total ?: 0
                    def passed = summary.statistic.passed ?: 0
                    def message = "📱 Mobile Test Execution Finished\n" +
                                  "✅ Passed: ${passed}/${total}\n" +
                                  "📊 Allure Report: ${env.BUILD_URL}allure"

                    sh """
                       curl -s -X POST https://api.telegram.org/bot8228531250:AAF4-CNqenOBmhO_U0qOq1pcpvMDNY0RvBU/sendMessage \
                       -d chat_id=6877916742 \
                       -d text="${message}"
                    """
                } catch (Exception e) {
                    // ignore
                }
        }
    }
}
