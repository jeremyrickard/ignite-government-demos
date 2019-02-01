const {events, Job} = require("brigadier");

events.on("exec", (e, project) => {
  console.log("exec hook fired, manual test");
  runTests(e, project);
});

function javaTests(e, project) {
  var javaTests = new Job("java tests", "maven:3-jdk-11")
 
  javaTests.tasks = [
    "cd src/draft-boot",
    "mvn test"
  ];

  return javaTests
}

function runTests(e, project) {
  javaTest(e, project).catch(e  => {console.error(e.toString())});
}
