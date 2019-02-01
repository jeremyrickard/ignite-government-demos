const {events, Job} = require("brigadier");

events.on("exec", (e, project) => {
  console.log("exec hook fired, manual test");
  var tests = javaTests(e, project);
  tests.run()
});

function javaTests(e, project) {
  var javaTests = new Job("java tests", "maven:3-jdk-11")
 
  javaTests.tasks = [
    "cd src",
    "pwd"
  ];

  return javaTests
}

