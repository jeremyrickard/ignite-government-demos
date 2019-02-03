# Brigade Demo Walkthrough

## Setup

If you don't have Helm installed, do that first. 

Next, install Brigade in your cluster:

```console
helm repo add brigade https://azure.github.io/brigade
helm install brigade/brigade --name brigade-server
```

Then install the `brig` CLI. On linux that looks like:

```console
wget -O brig https://github.com/Azure/brigade/releases/download/v0.20.0/brig-linux-amd64
chmod +x brig
mv brig /usr/local/bin/
```

## Create a Brigade Project

Brigade runs jobs in your cluster and is normally driven by a source repository. This sample repo has a `brigade.js` file, which defines the jobs that can be run by Brigade. Let's make a Brigade project that will use that:

```console
brig project create
? Project Name [? for help] (deis/empty-testbed) ignite-demo-project
? Full repository name [? for help] github.com/jeremyrickard/ignite-government-demos
? Clone URL (https://github.com/your/repo.git) [? for help] (https://github.com/ignite-demo-project.git) https://github.com/jeremyrickard/ignite-government-demos.git
? Add secrets? [? for help] (y/N) N
Auto-generated a Shared Secret: "yePDWQwem1RsrNflVYLh5lyY"
? Configure GitHub Access? [? for help] (y/N) N
? Configure advanced options [? for help] (y/N) N
Project ID: brigade-7b1abf24c0e4effa7b3939c5c3f1280ab4bf1e5e087ee2a89fb5d2
```

Now you can view the projects:

```console
$ brig project list
NAME               	ID                                                            	REPO
ignite-demo-project	brigade-7b1abf24c0e4effa7b3939c5c3f1280ab4bf1e5e087ee2a89fb5d2	github.com/jeremyrickard/ignite-government-demos
```

You can also launch the dashboard and see it:

```console
$ brig dashboard
2019/02/02 21:44:13 Connecting to kashti at http://localhost:8081...
2019/02/02 21:44:13 Connected! When you are finished with this session, enter CTRL+C.
```

The `brigade.js` included in this sample repo is pretty simple:

```javascript
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
```

This will simulate running the unit tests for the project on a push event from github. We'll manually run it with `brig run` though:

```console
$ /usr/local/bin/brig run ignite-demo-project
Event created. Waiting for worker pod named "brigade-worker-01d2rtt06cvwtcv2y5qnkrwfdr".
Build: 01d2rtt06cvwtcv2y5qnkrwfdr, Worker: brigade-worker-01d2rtt06cvwtcv2y5qnkrwfdr
prestart: no dependencies file found
prestart: loading script from /vcs/brigade.js
[brigade] brigade-worker version: 0.19.0
exec hook fired, manual test
[brigade:k8s] Creating secret java-tests-01d2rtt06cvwtcv2y5qnkrwfdr
[brigade:k8s] Creating pod java-tests-01d2rtt06cvwtcv2y5qnkrwfdr
[brigade:k8s] Timeout set at 900000
[brigade:k8s] Pod not yet scheduled
[brigade:k8s] default/java-tests-01d2rtt06cvwtcv2y5qnkrwfdr phase Pending
.=.. Pending
```

This will run for a while. You can see what's happening behind the scenes with `kubectl`:

```console
$ kubectl get pods
NAME                                                READY     STATUS            RESTARTS   AGE
brigade-server-brigade-api-7dc5c858b-dvhnb          1/1       Running           0          12m
brigade-server-brigade-ctrl-fcf4dfdd-frm9t          1/1       Running           0          12m
brigade-server-brigade-github-gw-5647d6464d-kq5rc   1/1       Running           0          12m
brigade-server-kashti-55d69c649-pzbdx               1/1       Running           0          12m
brigade-worker-01d2rtt06cvwtcv2y5qnkrwfdr           1/1       Running           0          1m
java-tests-01d2rtt06cvwtcv2y5qnkrwfdr               0/1       PodInitializing   0          48s
```

Here we can see that a brigade worker pod started up. That matches what we see in the output above. We can also see a pod has been created to run our `java-tests`. The tasks in the `createJavaTests` function above will be run in that container:

* First, it will cd into `src/draft-boot`. This works because the github repo was cloned and mounted at `src` in the `java-tests-01d2rtt06cvwtcv2y5qnkrwfdr` container.
* Second, it will execute `mvn test`. This image is actually running the `maven:3-jdk-11` Docker image, so it has that locally. 

Brigade jobs can do anything that can run inside a Docker container!

Run the `brig dashboard` command again and click into the `ignite-demo-project` project. Now there is a run and we can see the logs from the container. These are the exact same outputs we'd see if we ran this locally.

## Cleanup

To remove the project, run `brig project delete ignite-demo-project`. This will clean it up and let you re-run this flow.
