const {events, Job} = require("brigadier");

events.on("exec", (e, project) => {
  console.log("exec hook fired, manual test");

  var job = createJavaTest(e, project)
  job.run()
});

events.on("push", (e, project) => {
  console.log("github push hook fired, C/I test");

  var job = createJavaTest(e, project)
  job.run()
});

function createJavaTest(e, project) {
  var javaTestJob = new Job("java-tests")

  javaTestJob.image= "maven:3-jdk-11"

  javaTestJob.tasks = [
    "cd src/draft-boot",
    "mvn test"
  ];
  
  javaTestJob.streamLogs = true

  return javaTestJob
}

