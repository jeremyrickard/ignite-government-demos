# ignite-government-demos

This repository represents three main demo vignettes:

* Inner Loop Development using Draft
* Representational CI/CD with Brigade
* Application Packaging and Installation using Cloud Native Application Bundles (CNAB)

The inner loop development with Draft can be found in [draft-boot](./draft-boot). It contains a Spring Boot application that has been started and will run locally with Java and an in-memory database. The demo flow will walk you through getting that app up and running on a Kubernetes cluster.

Next, we can look at what the beginnings of a CI/CD pipeline might look like with Brigade. The example is very minimal, but you can easily run it locally and see what happens with the single Job that is defined. To read the steps for this, check out [brigade-overview](./brigade-overview.md).

Finally, we can look at what it would look like to [package that application](./draft-boot-cnab) into a CNAB using [Porter](http://porter.sh). There are three different bundles to walk through, the simple case of running just the sample app, an example that uses Helm to also install MySQL and finally an example that will show you how to use Porter to create an Azure MySQL instance and install the demo application.

Each directory will provide info on how to walk through the flow.

There is also a video located here: [TBD](#tbd).