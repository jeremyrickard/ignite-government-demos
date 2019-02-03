# Developing Applications with Draft

Draft makes it easier for developers to build applications that run on Kubernetes by doing a few things:

* The `draft create` command gives developers the artifacts they need to build and run their applications in Kubernetes
* The `draft up` command builds the container image for an application and deploys it to Kubernetes
* The `draft connect` command lets us connect to the the application via our local development environment

Draft targets the "inner loop" of a developer's workflow: as they hack on code, but before code is committed to version control.

## Setup

To successfully walk through this demo, you'll need the following:

* Docker
* Visual Studio Code or your favorite editor
* Draft

We'll also use Helm for some additional configuration.

## Get Started With Draft

This sample applicaton is JAva based, so you can iterate on it locally, but when you're ready to start testing it in a Kubernetes cluster you'll need to write a Dockerfile to build a Docker container and Kubernetes manifests in order to deploy it. That's where Draft can help. To get started with Draft, you'll run the `draft create` command in the project directory.

```console
$ draft create
--> Draft detected Shell (41.268907%)
--> Could not find a pack for Shell. Trying to find the next likely language match...
--> Draft detected Batchfile (27.316366%)
--> Could not find a pack for Batchfile. Trying to find the next likely language match...
--> Draft detected Java (16.551943%)
--> Ready to sail
```

This will create a `draft.toml`, a `Dockerfile` file and a `charts` directory that contains a skeleton [Helm](https://helm.sh/) chart for the application. From the output, we can see that Draft tried to detect the programming language for us and generated an opinionated set of configuration.

## Iterate on the Deployment

Now that we've run `draft create` and have the relevant files, we're ready to try and run it.

```console
$ draft up
Draft Up Started: 'draft-boot': 01D2MZPMH32KJHCAH6Q1KDWQRD
draft-boot: Building Docker Image: SUCCESS ⚓  (90.0656s)
draft-boot: Pushing Docker Image: SUCCESS ⚓  (38.6644s)
draft-boot: Releasing Application: SUCCESS ⚓  (4.1772s)
```

This will build the Docker image, push it to the a registry if we configured one and finally deploy the application to our cluster.

We can then connect to it with the `draft connect` command:

```console
$ draft connect
Connect to draft-boot:4567 on localhost:50588
[draft-boot]:
[draft-boot]:   .   ____          _            __ _ _
[draft-boot]:  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
[draft-boot]: ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
[draft-boot]:  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
[draft-boot]:   '  |____| .__|_| |_|_| |_\__, | / / / /
[draft-boot]:  =========|_|==============|___/=/_/_/_/
[draft-boot]:  :: Spring Boot ::        (v2.1.2.RELEASE)
[draft-boot]:
[draft-boot]: 2019-02-01 16:56:53.877  INFO 8 --- [           main] c.j.draftboot.DraftBootApplication       : Starting DraftBootApplication v0.0.1-SNAPSHOT on draft-boot-draft-boot-5cb5d4586-bs9f4 with PID 8 (/opt/target/draft-boot-1.0-with-dependencies.jar started by root in /opt/target)
[draft-boot]: 2019-02-01 16:56:54.081  INFO 8 --- [           main] c.j.draftboot.DraftBootApplication       : No active profile set, falling back to default profiles: default
[draft-boot]: 2019-02-01 16:57:28.473  INFO 8 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data repositories in DEFAULT mode.
[draft-boot]: 2019-02-01 16:57:31.475  INFO 8 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 2799ms. Found 2 repository interfaces.
[draft-boot]: 2019-02-01 16:57:48.675  INFO 8 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration' of type [org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration$$EnhancerBySpringCGLIB$$28ee22a] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
[draft-boot]: 2019-02-01 16:57:49.279  INFO 8 --- [           main] trationDelegate$BeanPostProcessorChecker : Bean 'org.springframework.hateoas.config.HateoasConfiguration' of type [org.springframework.hateoas.config.HateoasConfiguration$$EnhancerBySpringCGLIB$$820f2f5c] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
[draft-boot]: 2019-02-01 16:58:03.573  INFO 8 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 4567 (http)
[draft-boot]: 2019-02-01 16:58:04.475  INFO 8 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
[draft-boot]: 2019-02-01 16:58:04.475  INFO 8 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.14]
[draft-boot]: 2019-02-01 16:58:04.976  INFO 8 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/usr/java/packages/lib:/usr/lib/x86_64-linux-gnu/jni:/lib/x86_64-linux-gnu:/usr/lib/x86_64-linux-gnu:/usr/lib/jni:/lib:/usr/lib]
[draft-boot]: 2019-02-01 16:58:06.774  INFO 8 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
[draft-boot]: 2019-02-01 16:58:06.775  INFO 8 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 69696 ms
[draft-boot]: 2019-02-01 16:58:14.477  INFO 8 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
[draft-boot]: xargs: java: terminated by signal 9
```

Wow that's a lot of output! Unfortunately, we see that it was actually terminated! Unfortunately, the default CPU and memory values that were generated for us aren't going to be sufficient. This is easy to address, however. We can simply edit the chart and change the settings. You'll find these in the `charts/draft-boot/values.yaml` file:

```yaml
 Default values for Maven projects.
# This is a YAML-formatted file.
# Declare variables to be passed into your templates.
replicaCount: 1
image:
  pullPolicy: IfNotPresent
service:
  name: java
  type: ClusterIP
  externalPort: 80
  internalPort: 4567
resources:
  limits:
    cpu: 100m
    memory: 128Mi
  requests:
    cpu: 100m
    memory: 128Mi
ingress:
  enabled: false
```

The values for `cpu` and `memory` above are pretty low, so let's increase them. Change the file to match below:

```yaml
 Default values for Maven projects.
# This is a YAML-formatted file.
# Declare variables to be passed into your templates.
replicaCount: 1
image:
  pullPolicy: IfNotPresent
service:
  name: java
  type: ClusterIP
  externalPort: 80
  internalPort: 4567
resources:
  limits:
    cpu: 1
    memory: 2048Mi
  requests:
    cpu: 500m
    memory: 1024Mi
ingress:
  enabled: false
```

Now, we can run `draft up` again and the application will be redeployed with the new configuration:

```console
$ draft up
Draft Up Started: 'draft-boot': 01D2N0FAAK8FD7JVG3J4FGJ1NA
draft-boot: Building Docker Image: SUCCESS ⚓  (95.0518s)
draft-boot: Pushing Docker Image: SUCCESS ⚓  (34.3102s)
draft-boot: Releasing Application: SUCCESS ⚓  (4.1786s)
Inspect the logs with `draft logs 01D2N0FAAK8FD7JVG3J4FGJ1NA`
```

We can see that the image was built and pushed again, along with another release of the application. This last step is where our new chart values should be applied. If we run `draft connect` again:

```console
$ draft connect
Connect to draft-boot:4567 on localhost:50915
[draft-boot]: 2019-02-01 17:13:26.394  INFO 8 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
[draft-boot]: 2019-02-01 17:13:26.575  WARN 8 --- [           main] aWebConfiguration$JpaWebMvcConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
[draft-boot]: 2019-02-01 17:13:26.988  INFO 8 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
[draft-boot]: 2019-02-01 17:13:27.877  INFO 8 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 4567 (http) with context path ''
[draft-boot]: 2019-02-01 17:13:27.880  INFO 8 --- [           main] c.j.draftboot.DraftBootApplication       : Started DraftBootApplication in 15.19 seconds (JVM running for 16.305)
```

Now we can see that the application has started up successfully! If we open a web browser, we can view the application at: http://localhost:PORT/api

In this case, the port number is 50915. Every time you run `draft connect` you'll get a different port, so check the output.

## Moving Past Development

This Java application uses Spring Data in order to persist data. This allows us to swap out database providers at runtime. When we ran the example above, we used an in memory database that is useful for testing purposes. We can change the configuration of this application by providing a database connection URL and Spring will automatically configure a connection for us! The easiest way to provide this configuration is via an environment variable. As we move this app toward production, we might want to switch to a MySQL database.

To do that, let's edit our chart one more time!

First, we'll edit the file that describes the actual Kubernetes deployment, which is located at `charts/draft-boot/templates/deployment.yaml`

```yaml
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: {{ template "fullname" . }}
  labels:
    draft: {{ default "draft-app" .Values.draft }}
    chart: "{{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}"
spec:
  replicas: {{ .Values.replicaCount }}
  template:
    metadata:
      annotations:
        buildID: {{ .Values.buildID }}
      labels:
        draft: {{ default "draft-app" .Values.draft }}
        app: {{ template "fullname" . }}
    spec:
      containers:
      - name: {{ .Chart.Name }}
        image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
        imagePullPolicy: {{ .Values.image.pullPolicy }}
        ports:
        - containerPort: {{ .Values.service.internalPort }}
        resources:
{{ toYaml .Values.resources | indent 12 }}
```

We're going to edit this to include our environment variable. This is a really common way to [provide configuration to applications](https://kubernetes.io/docs/tasks/inject-data-application/define-environment-variable-container/).

Change the file to match the following:

```yaml
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: {{ template "fullname" . }}
  labels:
    draft: {{ default "draft-app" .Values.draft }}
    chart: "{{ .Chart.Name }}-{{ .Chart.Version | replace "+" "_" }}"
spec:
  replicas: {{ .Values.replicaCount }}
  template:
    metadata:
      annotations:
        buildID: {{ .Values.buildID }}
      labels:
        draft: {{ default "draft-app" .Values.draft }}
        app: {{ template "fullname" . }}
    spec:
      containers:
      - name: {{ .Chart.Name }}
        image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
        imagePullPolicy: {{ .Values.image.pullPolicy }}
        {{ if .Values.database_host }}
        env:
        - name: SPRING_DATASOURCE_URL
          value: jdbc:mysql://{{ .Values.database_host }}:{{ .Values.database_port }}/{{ .Values.database_name }}
        - name: SPRING_DATASOURCE_USERNAME
          value: {{ .Values.database_user }}
        - name: SPRING_DATASOURCE_PASSWORD
          value: {{ .Values.database_password }}
        {{ end }}
        ports:
        - containerPort: {{ .Values.service.internalPort }}
        resources:
{{ toYaml .Values.resources | indent 12 }}
```

Now, let's install MySQL in our cluster with Helm:

```console
$ helm install --name mysql stable/mysql --set mysqlUser=bizops --set mysqlPassword=securepassword --set mysqlDatabase=bizops --set persistence.enabled=false
```

After running this command, MySQL will be available inside the cluster at `mysql.default.svc.cluster.local:3306`, with the username `bizops` and the password `securepassword`.  At this point, we can use Helm to install our app and pass the chart that value! Before we do that, let's run `draft delete` to remove the app from the cluster. Then we can re-install it with Helm.

```console
$ draft delete
app 'draft-boot' deleted
```

Now install Helm with the following command:

```console
helm install ./charts/draft-boot --name draft-boot --set database_host=mysql.default.svc.cluster.local --set database_port=3306 --set database_name=bizops --set database_user=bizops --set database_password=securepassword
```

## Next Steps
