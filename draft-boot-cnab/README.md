# Managing Applications with CNAB and Porter

## Setup

* Install [Duffle](https://duffle.sh/)
* Install [Porter](https://porter.sh/)

## Bundles

We have three example bundles will install our sample application. The first will install the application and use an in-memory database using Helm. Next, we'll use Helm to install the application and create a MySQL database. Finally, we can create an instance of the application with Helm and create an Azure MySQL database. 

### Simple Draft Boot

This example uses the `helm` mixin to install the sample application with an in-memory database. The manifest contains only an install step for the application. This will result in using an in-memory database. 

First, you'll need to build the bundle with `porter build`:

```console
$ cd simple-draft-boot-cnab
$ porter build
Copying dependencies ===>
Copying mixins ===>
Copying mixin helm ===>
Copying mixin exec ===>
Copying mixin porter ===>

Generating Dockerfile =======>
[FROM quay.io/deis/lightweight-docker-go:v0.2.0 FROM debian:stretch COPY cnab/ /cnab/ COPY porter.yaml /cnab/app/porter.yaml CMD ["/cnab/app/run"] COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt RUN apt-get update && \  apt-get install -y curl && \  curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \  tar -xzf helm.tgz && \  mv linux-amd64/helm /usr/local/bin && \  rm helm.tgz RUN helm init --client-only # exec mixin has no buildtime dependencies ]

Writing Dockerfile =======>
FROM quay.io/deis/lightweight-docker-go:v0.2.0
FROM debian:stretch
COPY cnab/ /cnab/
COPY porter.yaml /cnab/app/porter.yaml
CMD ["/cnab/app/run"]
COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
RUN apt-get update && \
 apt-get install -y curl && \
 curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \
 tar -xzf helm.tgz && \
 mv linux-amd64/helm /usr/local/bin && \
 rm helm.tgz
RUN helm init --client-only
# exec mixin has no buildtime dependencies


Starting Invocation Image Build =======>
Step 1/8 : FROM quay.io/deis/lightweight-docker-go:v0.2.0
 ---> acf6712d2918
Step 2/8 : FROM debian:stretch
 ---> 4879790bd60d
Step 3/8 : COPY cnab/ /cnab/
 ---> ae7af873a2d2
Step 4/8 : COPY porter.yaml /cnab/app/porter.yaml
 ---> 5d942adbf41e
Step 5/8 : CMD ["/cnab/app/run"]
 ---> Running in a0a976ec6fe6
 ---> 293a9d930950
Step 6/8 : COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
 ---> b5701d18f7bb
Step 7/8 : RUN apt-get update &&  apt-get install -y curl &&  curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz &&  tar -xzf helm.tgz &&  mv linux-amd64/helm /usr/local/bin &&  rm helm.tgz
 ---> Running in 316704b44118
Ign:1 http://cdn-fastly.deb.debian.org/debian stretch InRelease
Get:2 http://security-cdn.debian.org/debian-security stretch/updates InRelease [94.3 kB]
Get:3 http://cdn-fastly.deb.debian.org/debian stretch-updates InRelease [91.0 kB]
Get:5 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 Packages [475 kB]
Get:4 http://cdn-fastly.deb.debian.org/debian stretch Release [118 kB]
Get:6 http://cdn-fastly.deb.debian.org/debian stretch Release.gpg [2434 B]
Get:7 http://cdn-fastly.deb.debian.org/debian stretch-updates/main amd64 Packages [7748 B]
Get:8 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 Packages [7090 kB]
Fetched 7878 kB in 3s (1978 kB/s)
Reading package lists...
Reading package lists...
Building dependency tree...
Reading state information...
The following additional packages will be installed:
  ca-certificates krb5-locales libcurl3 libffi6 libgmp10 libgnutls30
  libgssapi-krb5-2 libhogweed4 libidn2-0 libk5crypto3 libkeyutils1 libkrb5-3
  libkrb5support0 libldap-2.4-2 libldap-common libnghttp2-14 libp11-kit0
  libpsl5 librtmp1 libsasl2-2 libsasl2-modules libsasl2-modules-db libssh2-1
  libssl1.0.2 libssl1.1 libtasn1-6 libunistring0 openssl publicsuffix
Suggested packages:
  gnutls-bin krb5-doc krb5-user libsasl2-modules-gssapi-mit
  | libsasl2-modules-gssapi-heimdal libsasl2-modules-ldap libsasl2-modules-otp
  libsasl2-modules-sql
The following NEW packages will be installed:
  ca-certificates curl krb5-locales libcurl3 libffi6 libgmp10 libgnutls30
  libgssapi-krb5-2 libhogweed4 libidn2-0 libk5crypto3 libkeyutils1 libkrb5-3
  libkrb5support0 libldap-2.4-2 libldap-common libnghttp2-14 libp11-kit0
  libpsl5 librtmp1 libsasl2-2 libsasl2-modules libsasl2-modules-db libssh2-1
  libssl1.0.2 libssl1.1 libtasn1-6 libunistring0 openssl publicsuffix
0 upgraded, 30 newly installed, 0 to remove and 7 not upgraded.
Need to get 7663 kB of archives.
After this operation, 19.8 MB of additional disk space will be used.
Get:2 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 libssl1.0.2 amd64 1.0.2q-1~deb9u1 [1300 kB]
Get:1 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 krb5-locales all 1.15-1+deb9u1 [93.8 kB]
Get:3 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libgmp10 amd64 2:6.1.2+dfsg-1 [253 kB]
Get:4 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libhogweed4 amd64 3.3-1+b2 [136 kB]
Get:5 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libffi6 amd64 3.2.1-6 [20.4 kB]
Get:6 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libp11-kit0 amd64 0.23.3-2 [111 kB]
Get:7 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libtasn1-6 amd64 4.10-1.1+deb9u1 [50.6 kB]
Get:8 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libgnutls30 amd64 3.5.8-5+deb9u4 [896 kB]
Get:9 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libkeyutils1 amd64 1.5.9-9 [12.4 kB]
Get:10 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libkrb5support0 amd64 1.15-1+deb9u1 [61.9 kB]
Get:11 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libk5crypto3 amd64 1.15-1+deb9u1 [119 kB]
Get:14 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libkrb5-3 amd64 1.15-1+deb9u1 [311 kB]
Get:12 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 libssl1.1 amd64 1.1.0j-1~deb9u1 [1354 kB]
Get:15 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libgssapi-krb5-2 amd64 1.15-1+deb9u1 [155 kB]
Get:16 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libsasl2-modules-db amd64 2.1.27~101-g0780600+dfsg-3 [68.2 kB]
Get:17 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libsasl2-2 amd64 2.1.27~101-g0780600+dfsg-3 [105 kB]
Get:18 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libldap-common all 2.4.44+dfsg-5+deb9u2 [85.5 kB]
Get:19 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libldap-2.4-2 amd64 2.4.44+dfsg-5+deb9u2 [219 kB]
Get:20 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 ca-certificates all 20161130+nmu1+deb9u1 [182 kB]
Get:21 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libunistring0 amd64 0.9.6+really0.9.3-0.1 [279 kB]
Get:13 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 openssl amd64 1.1.0j-1~deb9u1 [746 kB]
Get:22 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libidn2-0 amd64 0.16-1+deb9u1 [60.7 kB]
Get:23 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libnghttp2-14 amd64 1.18.1-1 [79.1 kB]
Get:24 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libpsl5 amd64 0.17.0-3 [41.8 kB]
Get:25 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 librtmp1 amd64 2.4+20151223.gitfa8646d.1-1+b1 [60.4 kB]
Get:26 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libssh2-1 amd64 1.7.0-1 [138 kB]
Get:27 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libcurl3 amd64 7.52.1-5+deb9u8 [292 kB]
Get:28 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 curl amd64 7.52.1-5+deb9u8 [228 kB]
Get:29 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libsasl2-modules amd64 2.1.27~101-g0780600+dfsg-3 [102 kB]
Get:30 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 publicsuffix all 20181003.1334-0+deb9u1 [104 kB]
debconf: delaying package configuration, since apt-utils is not installed
Fetched 7663 kB in 1s (4933 kB/s)
Selecting previously unselected package libssl1.0.2:amd64.
(Reading database ... 6498 files and directories currently installed.)
Preparing to unpack .../00-libssl1.0.2_1.0.2q-1~deb9u1_amd64.deb ...
Unpacking libssl1.0.2:amd64 (1.0.2q-1~deb9u1) ...
Selecting previously unselected package libssl1.1:amd64.
Preparing to unpack .../01-libssl1.1_1.1.0j-1~deb9u1_amd64.deb ...
Unpacking libssl1.1:amd64 (1.1.0j-1~deb9u1) ...
Selecting previously unselected package krb5-locales.
Preparing to unpack .../02-krb5-locales_1.15-1+deb9u1_all.deb ...
Unpacking krb5-locales (1.15-1+deb9u1) ...
Selecting previously unselected package libgmp10:amd64.
Preparing to unpack .../03-libgmp10_2%3a6.1.2+dfsg-1_amd64.deb ...
Unpacking libgmp10:amd64 (2:6.1.2+dfsg-1) ...
Selecting previously unselected package libhogweed4:amd64.
Preparing to unpack .../04-libhogweed4_3.3-1+b2_amd64.deb ...
Unpacking libhogweed4:amd64 (3.3-1+b2) ...
Selecting previously unselected package libffi6:amd64.
Preparing to unpack .../05-libffi6_3.2.1-6_amd64.deb ...
Unpacking libffi6:amd64 (3.2.1-6) ...
Selecting previously unselected package libp11-kit0:amd64.
Preparing to unpack .../06-libp11-kit0_0.23.3-2_amd64.deb ...
Unpacking libp11-kit0:amd64 (0.23.3-2) ...
Selecting previously unselected package libtasn1-6:amd64.
Preparing to unpack .../07-libtasn1-6_4.10-1.1+deb9u1_amd64.deb ...
Unpacking libtasn1-6:amd64 (4.10-1.1+deb9u1) ...
Selecting previously unselected package libgnutls30:amd64.
Preparing to unpack .../08-libgnutls30_3.5.8-5+deb9u4_amd64.deb ...
Unpacking libgnutls30:amd64 (3.5.8-5+deb9u4) ...
Selecting previously unselected package libkeyutils1:amd64.
Preparing to unpack .../09-libkeyutils1_1.5.9-9_amd64.deb ...
Unpacking libkeyutils1:amd64 (1.5.9-9) ...
Selecting previously unselected package libkrb5support0:amd64.
Preparing to unpack .../10-libkrb5support0_1.15-1+deb9u1_amd64.deb ...
Unpacking libkrb5support0:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libk5crypto3:amd64.
Preparing to unpack .../11-libk5crypto3_1.15-1+deb9u1_amd64.deb ...
Unpacking libk5crypto3:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libkrb5-3:amd64.
Preparing to unpack .../12-libkrb5-3_1.15-1+deb9u1_amd64.deb ...
Unpacking libkrb5-3:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libgssapi-krb5-2:amd64.
Preparing to unpack .../13-libgssapi-krb5-2_1.15-1+deb9u1_amd64.deb ...
Unpacking libgssapi-krb5-2:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libsasl2-modules-db:amd64.
Preparing to unpack .../14-libsasl2-modules-db_2.1.27~101-g0780600+dfsg-3_amd64.deb ...
Unpacking libsasl2-modules-db:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Selecting previously unselected package libsasl2-2:amd64.
Preparing to unpack .../15-libsasl2-2_2.1.27~101-g0780600+dfsg-3_amd64.deb ...
Unpacking libsasl2-2:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Selecting previously unselected package libldap-common.
Preparing to unpack .../16-libldap-common_2.4.44+dfsg-5+deb9u2_all.deb ...
Unpacking libldap-common (2.4.44+dfsg-5+deb9u2) ...
Selecting previously unselected package libldap-2.4-2:amd64.
Preparing to unpack .../17-libldap-2.4-2_2.4.44+dfsg-5+deb9u2_amd64.deb ...
Unpacking libldap-2.4-2:amd64 (2.4.44+dfsg-5+deb9u2) ...
Selecting previously unselected package openssl.
Preparing to unpack .../18-openssl_1.1.0j-1~deb9u1_amd64.deb ...
Unpacking openssl (1.1.0j-1~deb9u1) ...
Selecting previously unselected package ca-certificates.
Preparing to unpack .../19-ca-certificates_20161130+nmu1+deb9u1_all.deb ...
Unpacking ca-certificates (20161130+nmu1+deb9u1) ...
Selecting previously unselected package libunistring0:amd64.
Preparing to unpack .../20-libunistring0_0.9.6+really0.9.3-0.1_amd64.deb ...
Unpacking libunistring0:amd64 (0.9.6+really0.9.3-0.1) ...
Selecting previously unselected package libidn2-0:amd64.
Preparing to unpack .../21-libidn2-0_0.16-1+deb9u1_amd64.deb ...
Unpacking libidn2-0:amd64 (0.16-1+deb9u1) ...
Selecting previously unselected package libnghttp2-14:amd64.
Preparing to unpack .../22-libnghttp2-14_1.18.1-1_amd64.deb ...
Unpacking libnghttp2-14:amd64 (1.18.1-1) ...
Selecting previously unselected package libpsl5:amd64.
Preparing to unpack .../23-libpsl5_0.17.0-3_amd64.deb ...
Unpacking libpsl5:amd64 (0.17.0-3) ...
Selecting previously unselected package librtmp1:amd64.
Preparing to unpack .../24-librtmp1_2.4+20151223.gitfa8646d.1-1+b1_amd64.deb ...
Unpacking librtmp1:amd64 (2.4+20151223.gitfa8646d.1-1+b1) ...
Selecting previously unselected package libssh2-1:amd64.
Preparing to unpack .../25-libssh2-1_1.7.0-1_amd64.deb ...
Unpacking libssh2-1:amd64 (1.7.0-1) ...
Selecting previously unselected package libcurl3:amd64.
Preparing to unpack .../26-libcurl3_7.52.1-5+deb9u8_amd64.deb ...
Unpacking libcurl3:amd64 (7.52.1-5+deb9u8) ...
Selecting previously unselected package curl.
Preparing to unpack .../27-curl_7.52.1-5+deb9u8_amd64.deb ...
Unpacking curl (7.52.1-5+deb9u8) ...
Selecting previously unselected package libsasl2-modules:amd64.
Preparing to unpack .../28-libsasl2-modules_2.1.27~101-g0780600+dfsg-3_amd64.deb ...
Unpacking libsasl2-modules:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Selecting previously unselected package publicsuffix.
Preparing to unpack .../29-publicsuffix_20181003.1334-0+deb9u1_all.deb ...
Unpacking publicsuffix (20181003.1334-0+deb9u1) ...
Setting up libnghttp2-14:amd64 (1.18.1-1) ...
Setting up libldap-common (2.4.44+dfsg-5+deb9u2) ...
Setting up libsasl2-modules-db:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Setting up libsasl2-2:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Setting up libtasn1-6:amd64 (4.10-1.1+deb9u1) ...
Setting up libssl1.0.2:amd64 (1.0.2q-1~deb9u1) ...
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
debconf: (Can't locate Term/ReadLine.pm in @INC (you may need to install the Term::ReadLine module) (@INC contains: /etc/perl /usr/local/lib/x86_64-linux-gnu/perl/5.24.1 /usr/local/share/perl/5.24.1 /usr/lib/x86_64-linux-gnu/perl5/5.24 /usr/share/perl5 /usr/lib/x86_64-linux-gnu/perl/5.24 /usr/share/perl/5.24 /usr/local/lib/site_perl /usr/lib/x86_64-linux-gnu/perl-base .) at /usr/share/perl5/Debconf/FrontEnd/Readline.pm line 7.)
debconf: falling back to frontend: Teletype
Setting up libgmp10:amd64 (2:6.1.2+dfsg-1) ...
Setting up libssh2-1:amd64 (1.7.0-1) ...
Setting up krb5-locales (1.15-1+deb9u1) ...
Processing triggers for libc-bin (2.24-11+deb9u3) ...
Setting up publicsuffix (20181003.1334-0+deb9u1) ...
Setting up libunistring0:amd64 (0.9.6+really0.9.3-0.1) ...
Setting up libssl1.1:amd64 (1.1.0j-1~deb9u1) ...
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
debconf: (Can't locate Term/ReadLine.pm in @INC (you may need to install the Term::ReadLine module) (@INC contains: /etc/perl /usr/local/lib/x86_64-linux-gnu/perl/5.24.1 /usr/local/share/perl/5.24.1 /usr/lib/x86_64-linux-gnu/perl5/5.24 /usr/share/perl5 /usr/lib/x86_64-linux-gnu/perl/5.24 /usr/share/perl/5.24 /usr/local/lib/site_perl /usr/lib/x86_64-linux-gnu/perl-base .) at /usr/share/perl5/Debconf/FrontEnd/Readline.pm line 7.)
debconf: falling back to frontend: Teletype
Setting up openssl (1.1.0j-1~deb9u1) ...
Setting up libffi6:amd64 (3.2.1-6) ...
Setting up libkeyutils1:amd64 (1.5.9-9) ...
Setting up libsasl2-modules:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Setting up ca-certificates (20161130+nmu1+deb9u1) ...
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
debconf: (Can't locate Term/ReadLine.pm in @INC (you may need to install the Term::ReadLine module) (@INC contains: /etc/perl /usr/local/lib/x86_64-linux-gnu/perl/5.24.1 /usr/local/share/perl/5.24.1 /usr/lib/x86_64-linux-gnu/perl5/5.24 /usr/share/perl5 /usr/lib/x86_64-linux-gnu/perl/5.24 /usr/share/perl/5.24 /usr/local/lib/site_perl /usr/lib/x86_64-linux-gnu/perl-base .) at /usr/share/perl5/Debconf/FrontEnd/Readline.pm line 7.)
debconf: falling back to frontend: Teletype
Updating certificates in /etc/ssl/certs...
151 added, 0 removed; done.
Setting up libidn2-0:amd64 (0.16-1+deb9u1) ...
Setting up libpsl5:amd64 (0.17.0-3) ...
Setting up libkrb5support0:amd64 (1.15-1+deb9u1) ...
Setting up libhogweed4:amd64 (3.3-1+b2) ...
Setting up libp11-kit0:amd64 (0.23.3-2) ...
Setting up libk5crypto3:amd64 (1.15-1+deb9u1) ...
Setting up libgnutls30:amd64 (3.5.8-5+deb9u4) ...
Setting up librtmp1:amd64 (2.4+20151223.gitfa8646d.1-1+b1) ...
Setting up libldap-2.4-2:amd64 (2.4.44+dfsg-5+deb9u2) ...
Setting up libkrb5-3:amd64 (1.15-1+deb9u1) ...
Setting up libgssapi-krb5-2:amd64 (1.15-1+deb9u1) ...
Setting up libcurl3:amd64 (7.52.1-5+deb9u8) ...
Setting up curl (7.52.1-5+deb9u8) ...
Processing triggers for libc-bin (2.24-11+deb9u3) ...
Processing triggers for ca-certificates (20161130+nmu1+deb9u1) ...
Updating certificates in /etc/ssl/certs...
0 added, 0 removed; done.
Running hooks in /etc/ca-certificates/update.d...
done.
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 18.2M  100 18.2M    0     0  7729k      0  0:00:02  0:00:02 --:--:-- 7727k
 ---> 2b28af3d0d88
Step 8/8 : RUN helm init --client-only
 ---> Running in 19f621cb3dac
Creating /root/.helm
Creating /root/.helm/repository
Creating /root/.helm/repository/cache
Creating /root/.helm/repository/local
Creating /root/.helm/plugins
Creating /root/.helm/starters
Creating /root/.helm/cache/archive
Creating /root/.helm/repository/repositories.yaml
Adding stable repo with URL: https://kubernetes-charts.storage.googleapis.com
Adding local repo with URL: http://127.0.0.1:8879/charts
$HELM_HOME has been configured at /root/.helm.
Not installing Tiller due to 'client-only' flag having been set
Happy Helming!
 ---> e181b0b6466d
Successfully built e181b0b6466d
Successfully tagged jeremyrickard/simple-draft-boot-mysql:latest
The push refers to repository [docker.io/jeremyrickard/simple-draft-boot-mysql]
44304daab70d: Preparing
221bf59bbec8: Preparing
faa17eca1633: Preparing
a15543de6a21: Preparing
9a515a0037ef: Preparing
90d1009ce6fe: Preparing
90d1009ce6fe: Waiting
a15543de6a21: Pushed
faa17eca1633: Pushed
44304daab70d: Pushed
90d1009ce6fe: Mounted from jeremyrickard/porter-hello
221bf59bbec8: Pushed
9a515a0037ef: Pushed
latest: digest: sha256:37a2048203d2cc2faf3043cb03e1fec806d8158466af1c72dd82e784e2ded278 size: 1580

Generating Bundle File with Invocation Image jeremyrickard/simple-draft-boot-mysql@sha256:37a2048203d2cc2faf3043cb03e1fec806d8158466af1c72dd82e784e2ded278 =======>
Generating credential kubeconfig ====>
```

After you run `porter build`, you'll end up with a `Dockerfile` and a `bundle.json` for the bundle. The invocation image will also be built for you. To install the bundle, you'll next need to create a credential to use when running the install:

```console
$ duffle credential generate kube-cred -f bundle.json --insecure
? Choose a source for "kubeconfig"  [Use arrows to move, type to filter]
  specific value
  environment variable
> file path
  shell command
? Enter a value for "kubeconfig" $HOME/.kube/config
```
Next, you can install the bundle with `duffle`:

```console
 duffle install -c kube-cred simple-draft-boot -f bundle.json --insecure
Executing install action...
executing porter install configuration from /cnab/app/porter.yaml
Install Hello World
/usr/local/bin/helm helm install --name draft-boot-mysql /cnab/app/chart/draft-boot --set image.repository=jeremyrickard/draft-boot --set image.tag=latest
NAME:   draft-boot-mysql
LAST DEPLOYED: Sun Feb  3 00:24:04 2019
NAMESPACE: default
STATUS: DEPLOYED

RESOURCES:
==> v1/Service
NAME                         TYPE       CLUSTER-IP    EXTERNAL-IP  PORT(S)  AGE
draft-boot-mysql-draft-boot  ClusterIP  10.0.230.207  <none>       80/TCP   1s

==> v1beta1/Deployment
NAME                         DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
draft-boot-mysql-draft-boot  1        1        1           0          1s

==> v1/Pod(related)
NAME                                          READY  STATUS             RESTARTS  AGE
draft-boot-mysql-draft-boot-5bd5bfbf69-t9zvf  0/1    ContainerCreating  0         1s


NOTES:

  http://draft-boot-mysql. to access your application

execution completed successfully!
```

### Draft Boot with MySQL and Helm

The next example builds on the previous example and uses Helm to also install MySQL on the cluster. The `porter.yaml` looks like this:

```yaml
# This is the configuration for Porter
# You must define steps for each action, but the rest is optional
# Uncomment out the sections below to take full advantage of what Porter can do!

mixins:
  - helm
  - exec 

credentials:
- name: kubeconfig
  path: /root/.kube/config

name: helm-draft-boot 
version: 0.1.0
description: "Install Draft Boot and MySQL on Azure Kuberentes Using Helm"
invocationImage: jeremyrickard/helm-draft-boot-mysql:latest

parameters:

- name: mysql-user
  type: string
  default: bizops-user
  destination:
    env: MYSQL_USER

- name: database-name
  type: string
  default: mydb
  destination:
    env: DATABASE_NAME

install:
  - description: "Install MySQL"
    helm:
      name: helm-draft-boot-mysql
      chart: stable/mysql
      version: 0.10.2
      replace: true
      set:
        persistence.enabled: false
        mysqlDatabase:
          source: bundle.parameters.database-name
        mysqlUser:
          source: bundle.parameters.mysql-user
    outputs:
    - name: mysql-root-password
      secret: helm-draft-boot-mysql
      key: mysql-root-password
    - name: mysql-password
      secret: helm-draft-boot-mysql
      key: mysql-password
  
  - description: "Install Hello World"
    helm:
      name: helm-draft-boot
      chart: /cnab/app/chart/draft-boot
      set:
        image.repository: "jeremyrickard/draft-boot"
        image.tag: "latest"
        database_host: helm-draft-boot-mysql.default.svc.cluster.local
        database_port: 3306
        database_name: 
          source: bundle.parameters.database-name
        database_user:
          source: bundle.parameters.mysql-user
        database_password:
          source: bundle.outputs.mysql-password
uninstall:
  - description: ""
    exec:
      command: echo
      args: 
       - "-c"
       - "echo Goodbye World"
```

When the `porter build` is run, it looks similar to the previous example, but we've also added some parameters:

```console
 porter build
Copying dependencies ===>
Copying mixins ===>
Copying mixin helm ===>
Copying mixin exec ===>
Copying mixin porter ===>

Generating Dockerfile =======>
[FROM quay.io/deis/lightweight-docker-go:v0.2.0 FROM debian:stretch COPY cnab/ /cnab/ COPY porter.yaml /cnab/app/porter.yaml CMD ["/cnab/app/run"] COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt RUN apt-get update && \  apt-get install -y curl && \  curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \  tar -xzf helm.tgz && \  mv linux-amd64/helm /usr/local/bin && \  rm helm.tgz RUN helm init --client-only # exec mixin has no buildtime dependencies ]

Writing Dockerfile =======>
FROM quay.io/deis/lightweight-docker-go:v0.2.0
FROM debian:stretch
COPY cnab/ /cnab/
COPY porter.yaml /cnab/app/porter.yaml
CMD ["/cnab/app/run"]
COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
RUN apt-get update && \
 apt-get install -y curl && \
 curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \
 tar -xzf helm.tgz && \
 mv linux-amd64/helm /usr/local/bin && \
 rm helm.tgz
RUN helm init --client-only
# exec mixin has no buildtime dependencies


Starting Invocation Image Build =======>
Step 1/8 : FROM quay.io/deis/lightweight-docker-go:v0.2.0
 ---> acf6712d2918
Step 2/8 : FROM debian:stretch
 ---> 4879790bd60d
Step 3/8 : COPY cnab/ /cnab/
 ---> Using cache
 ---> ae7af873a2d2
Step 4/8 : COPY porter.yaml /cnab/app/porter.yaml
 ---> fe069abbcc3a
Step 5/8 : CMD ["/cnab/app/run"]
 ---> Running in c997ac71caf4
 ---> 2738d386c593
Step 6/8 : COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
 ---> 63f4e298a7d8
Step 7/8 : RUN apt-get update &&  apt-get install -y curl &&  curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz &&  tar -xzf helm.tgz &&  mv linux-amd64/helm /usr/local/bin &&  rm helm.tgz
 ---> Running in ce0397471749
Get:1 http://security-cdn.debian.org/debian-security stretch/updates InRelease [94.3 kB]
Ign:2 http://cdn-fastly.deb.debian.org/debian stretch InRelease
Get:3 http://cdn-fastly.deb.debian.org/debian stretch-updates InRelease [91.0 kB]
Get:4 http://cdn-fastly.deb.debian.org/debian stretch Release [118 kB]
Get:5 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 Packages [475 kB]
Get:6 http://cdn-fastly.deb.debian.org/debian stretch Release.gpg [2434 B]
Get:7 http://cdn-fastly.deb.debian.org/debian stretch-updates/main amd64 Packages [7748 B]
Get:8 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 Packages [7090 kB]
Fetched 7878 kB in 3s (2205 kB/s)
Reading package lists...
Reading package lists...
Building dependency tree...
Reading state information...
The following additional packages will be installed:
  ca-certificates krb5-locales libcurl3 libffi6 libgmp10 libgnutls30
  libgssapi-krb5-2 libhogweed4 libidn2-0 libk5crypto3 libkeyutils1 libkrb5-3
  libkrb5support0 libldap-2.4-2 libldap-common libnghttp2-14 libp11-kit0
  libpsl5 librtmp1 libsasl2-2 libsasl2-modules libsasl2-modules-db libssh2-1
  libssl1.0.2 libssl1.1 libtasn1-6 libunistring0 openssl publicsuffix
Suggested packages:
  gnutls-bin krb5-doc krb5-user libsasl2-modules-gssapi-mit
  | libsasl2-modules-gssapi-heimdal libsasl2-modules-ldap libsasl2-modules-otp
  libsasl2-modules-sql
The following NEW packages will be installed:
  ca-certificates curl krb5-locales libcurl3 libffi6 libgmp10 libgnutls30
  libgssapi-krb5-2 libhogweed4 libidn2-0 libk5crypto3 libkeyutils1 libkrb5-3
  libkrb5support0 libldap-2.4-2 libldap-common libnghttp2-14 libp11-kit0
  libpsl5 librtmp1 libsasl2-2 libsasl2-modules libsasl2-modules-db libssh2-1
  libssl1.0.2 libssl1.1 libtasn1-6 libunistring0 openssl publicsuffix
0 upgraded, 30 newly installed, 0 to remove and 7 not upgraded.
Need to get 7663 kB of archives.
After this operation, 19.8 MB of additional disk space will be used.
Get:1 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 libssl1.0.2 amd64 1.0.2q-1~deb9u1 [1300 kB]
Get:4 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 krb5-locales all 1.15-1+deb9u1 [93.8 kB]
Get:5 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libgmp10 amd64 2:6.1.2+dfsg-1 [253 kB]
Get:6 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libhogweed4 amd64 3.3-1+b2 [136 kB]
Get:2 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 libssl1.1 amd64 1.1.0j-1~deb9u1 [1354 kB]
Get:7 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libffi6 amd64 3.2.1-6 [20.4 kB]
Get:8 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libp11-kit0 amd64 0.23.3-2 [111 kB]
Get:9 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libtasn1-6 amd64 4.10-1.1+deb9u1 [50.6 kB]
Get:10 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libgnutls30 amd64 3.5.8-5+deb9u4 [896 kB]
Get:3 http://security-cdn.debian.org/debian-security stretch/updates/main amd64 openssl amd64 1.1.0j-1~deb9u1 [746 kB]
Get:11 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libkeyutils1 amd64 1.5.9-9 [12.4 kB]
Get:12 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libkrb5support0 amd64 1.15-1+deb9u1 [61.9 kB]
Get:13 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libk5crypto3 amd64 1.15-1+deb9u1 [119 kB]
Get:14 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libkrb5-3 amd64 1.15-1+deb9u1 [311 kB]
Get:15 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libgssapi-krb5-2 amd64 1.15-1+deb9u1 [155 kB]
Get:16 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libsasl2-modules-db amd64 2.1.27~101-g0780600+dfsg-3 [68.2 kB]
Get:17 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libsasl2-2 amd64 2.1.27~101-g0780600+dfsg-3 [105 kB]
Get:18 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libldap-common all 2.4.44+dfsg-5+deb9u2 [85.5 kB]
Get:19 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libldap-2.4-2 amd64 2.4.44+dfsg-5+deb9u2 [219 kB]
Get:20 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 ca-certificates all 20161130+nmu1+deb9u1 [182 kB]
Get:21 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libunistring0 amd64 0.9.6+really0.9.3-0.1 [279 kB]
Get:22 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libidn2-0 amd64 0.16-1+deb9u1 [60.7 kB]
Get:23 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libnghttp2-14 amd64 1.18.1-1 [79.1 kB]
Get:24 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libpsl5 amd64 0.17.0-3 [41.8 kB]
Get:25 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 librtmp1 amd64 2.4+20151223.gitfa8646d.1-1+b1 [60.4 kB]
Get:26 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libssh2-1 amd64 1.7.0-1 [138 kB]
Get:27 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libcurl3 amd64 7.52.1-5+deb9u8 [292 kB]
Get:28 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 curl amd64 7.52.1-5+deb9u8 [228 kB]
Get:29 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 libsasl2-modules amd64 2.1.27~101-g0780600+dfsg-3 [102 kB]
Get:30 http://cdn-fastly.deb.debian.org/debian stretch/main amd64 publicsuffix all 20181003.1334-0+deb9u1 [104 kB]
debconf: delaying package configuration, since apt-utils is not installed
Fetched 7663 kB in 1s (4565 kB/s)
Selecting previously unselected package libssl1.0.2:amd64.
(Reading database ... 6498 files and directories currently installed.)
Preparing to unpack .../00-libssl1.0.2_1.0.2q-1~deb9u1_amd64.deb ...
Unpacking libssl1.0.2:amd64 (1.0.2q-1~deb9u1) ...
Selecting previously unselected package libssl1.1:amd64.
Preparing to unpack .../01-libssl1.1_1.1.0j-1~deb9u1_amd64.deb ...
Unpacking libssl1.1:amd64 (1.1.0j-1~deb9u1) ...
Selecting previously unselected package krb5-locales.
Preparing to unpack .../02-krb5-locales_1.15-1+deb9u1_all.deb ...
Unpacking krb5-locales (1.15-1+deb9u1) ...
Selecting previously unselected package libgmp10:amd64.
Preparing to unpack .../03-libgmp10_2%3a6.1.2+dfsg-1_amd64.deb ...
Unpacking libgmp10:amd64 (2:6.1.2+dfsg-1) ...
Selecting previously unselected package libhogweed4:amd64.
Preparing to unpack .../04-libhogweed4_3.3-1+b2_amd64.deb ...
Unpacking libhogweed4:amd64 (3.3-1+b2) ...
Selecting previously unselected package libffi6:amd64.
Preparing to unpack .../05-libffi6_3.2.1-6_amd64.deb ...
Unpacking libffi6:amd64 (3.2.1-6) ...
Selecting previously unselected package libp11-kit0:amd64.
Preparing to unpack .../06-libp11-kit0_0.23.3-2_amd64.deb ...
Unpacking libp11-kit0:amd64 (0.23.3-2) ...
Selecting previously unselected package libtasn1-6:amd64.
Preparing to unpack .../07-libtasn1-6_4.10-1.1+deb9u1_amd64.deb ...
Unpacking libtasn1-6:amd64 (4.10-1.1+deb9u1) ...
Selecting previously unselected package libgnutls30:amd64.
Preparing to unpack .../08-libgnutls30_3.5.8-5+deb9u4_amd64.deb ...
Unpacking libgnutls30:amd64 (3.5.8-5+deb9u4) ...
Selecting previously unselected package libkeyutils1:amd64.
Preparing to unpack .../09-libkeyutils1_1.5.9-9_amd64.deb ...
Unpacking libkeyutils1:amd64 (1.5.9-9) ...
Selecting previously unselected package libkrb5support0:amd64.
Preparing to unpack .../10-libkrb5support0_1.15-1+deb9u1_amd64.deb ...
Unpacking libkrb5support0:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libk5crypto3:amd64.
Preparing to unpack .../11-libk5crypto3_1.15-1+deb9u1_amd64.deb ...
Unpacking libk5crypto3:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libkrb5-3:amd64.
Preparing to unpack .../12-libkrb5-3_1.15-1+deb9u1_amd64.deb ...
Unpacking libkrb5-3:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libgssapi-krb5-2:amd64.
Preparing to unpack .../13-libgssapi-krb5-2_1.15-1+deb9u1_amd64.deb ...
Unpacking libgssapi-krb5-2:amd64 (1.15-1+deb9u1) ...
Selecting previously unselected package libsasl2-modules-db:amd64.
Preparing to unpack .../14-libsasl2-modules-db_2.1.27~101-g0780600+dfsg-3_amd64.deb ...
Unpacking libsasl2-modules-db:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Selecting previously unselected package libsasl2-2:amd64.
Preparing to unpack .../15-libsasl2-2_2.1.27~101-g0780600+dfsg-3_amd64.deb ...
Unpacking libsasl2-2:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Selecting previously unselected package libldap-common.
Preparing to unpack .../16-libldap-common_2.4.44+dfsg-5+deb9u2_all.deb ...
Unpacking libldap-common (2.4.44+dfsg-5+deb9u2) ...
Selecting previously unselected package libldap-2.4-2:amd64.
Preparing to unpack .../17-libldap-2.4-2_2.4.44+dfsg-5+deb9u2_amd64.deb ...
Unpacking libldap-2.4-2:amd64 (2.4.44+dfsg-5+deb9u2) ...
Selecting previously unselected package openssl.
Preparing to unpack .../18-openssl_1.1.0j-1~deb9u1_amd64.deb ...
Unpacking openssl (1.1.0j-1~deb9u1) ...
Selecting previously unselected package ca-certificates.
Preparing to unpack .../19-ca-certificates_20161130+nmu1+deb9u1_all.deb ...
Unpacking ca-certificates (20161130+nmu1+deb9u1) ...
Selecting previously unselected package libunistring0:amd64.
Preparing to unpack .../20-libunistring0_0.9.6+really0.9.3-0.1_amd64.deb ...
Unpacking libunistring0:amd64 (0.9.6+really0.9.3-0.1) ...
Selecting previously unselected package libidn2-0:amd64.
Preparing to unpack .../21-libidn2-0_0.16-1+deb9u1_amd64.deb ...
Unpacking libidn2-0:amd64 (0.16-1+deb9u1) ...
Selecting previously unselected package libnghttp2-14:amd64.
Preparing to unpack .../22-libnghttp2-14_1.18.1-1_amd64.deb ...
Unpacking libnghttp2-14:amd64 (1.18.1-1) ...
Selecting previously unselected package libpsl5:amd64.
Preparing to unpack .../23-libpsl5_0.17.0-3_amd64.deb ...
Unpacking libpsl5:amd64 (0.17.0-3) ...
Selecting previously unselected package librtmp1:amd64.
Preparing to unpack .../24-librtmp1_2.4+20151223.gitfa8646d.1-1+b1_amd64.deb ...
Unpacking librtmp1:amd64 (2.4+20151223.gitfa8646d.1-1+b1) ...
Selecting previously unselected package libssh2-1:amd64.
Preparing to unpack .../25-libssh2-1_1.7.0-1_amd64.deb ...
Unpacking libssh2-1:amd64 (1.7.0-1) ...
Selecting previously unselected package libcurl3:amd64.
Preparing to unpack .../26-libcurl3_7.52.1-5+deb9u8_amd64.deb ...
Unpacking libcurl3:amd64 (7.52.1-5+deb9u8) ...
Selecting previously unselected package curl.
Preparing to unpack .../27-curl_7.52.1-5+deb9u8_amd64.deb ...
Unpacking curl (7.52.1-5+deb9u8) ...
Selecting previously unselected package libsasl2-modules:amd64.
Preparing to unpack .../28-libsasl2-modules_2.1.27~101-g0780600+dfsg-3_amd64.deb ...
Unpacking libsasl2-modules:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Selecting previously unselected package publicsuffix.
Preparing to unpack .../29-publicsuffix_20181003.1334-0+deb9u1_all.deb ...
Unpacking publicsuffix (20181003.1334-0+deb9u1) ...
Setting up libnghttp2-14:amd64 (1.18.1-1) ...
Setting up libldap-common (2.4.44+dfsg-5+deb9u2) ...
Setting up libsasl2-modules-db:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Setting up libsasl2-2:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Setting up libtasn1-6:amd64 (4.10-1.1+deb9u1) ...
Setting up libssl1.0.2:amd64 (1.0.2q-1~deb9u1) ...
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
debconf: (Can't locate Term/ReadLine.pm in @INC (you may need to install the Term::ReadLine module) (@INC contains: /etc/perl /usr/local/lib/x86_64-linux-gnu/perl/5.24.1 /usr/local/share/perl/5.24.1 /usr/lib/x86_64-linux-gnu/perl5/5.24 /usr/share/perl5 /usr/lib/x86_64-linux-gnu/perl/5.24 /usr/share/perl/5.24 /usr/local/lib/site_perl /usr/lib/x86_64-linux-gnu/perl-base .) at /usr/share/perl5/Debconf/FrontEnd/Readline.pm line 7.)
debconf: falling back to frontend: Teletype
Setting up libgmp10:amd64 (2:6.1.2+dfsg-1) ...
Setting up libssh2-1:amd64 (1.7.0-1) ...
Setting up krb5-locales (1.15-1+deb9u1) ...
Processing triggers for libc-bin (2.24-11+deb9u3) ...
Setting up publicsuffix (20181003.1334-0+deb9u1) ...
Setting up libunistring0:amd64 (0.9.6+really0.9.3-0.1) ...
Setting up libssl1.1:amd64 (1.1.0j-1~deb9u1) ...
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
debconf: (Can't locate Term/ReadLine.pm in @INC (you may need to install the Term::ReadLine module) (@INC contains: /etc/perl /usr/local/lib/x86_64-linux-gnu/perl/5.24.1 /usr/local/share/perl/5.24.1 /usr/lib/x86_64-linux-gnu/perl5/5.24 /usr/share/perl5 /usr/lib/x86_64-linux-gnu/perl/5.24 /usr/share/perl/5.24 /usr/local/lib/site_perl /usr/lib/x86_64-linux-gnu/perl-base .) at /usr/share/perl5/Debconf/FrontEnd/Readline.pm line 7.)
debconf: falling back to frontend: Teletype
Setting up openssl (1.1.0j-1~deb9u1) ...
Setting up libffi6:amd64 (3.2.1-6) ...
Setting up libkeyutils1:amd64 (1.5.9-9) ...
Setting up libsasl2-modules:amd64 (2.1.27~101-g0780600+dfsg-3) ...
Setting up ca-certificates (20161130+nmu1+deb9u1) ...
debconf: unable to initialize frontend: Dialog
debconf: (TERM is not set, so the dialog frontend is not usable.)
debconf: falling back to frontend: Readline
debconf: unable to initialize frontend: Readline
debconf: (Can't locate Term/ReadLine.pm in @INC (you may need to install the Term::ReadLine module) (@INC contains: /etc/perl /usr/local/lib/x86_64-linux-gnu/perl/5.24.1 /usr/local/share/perl/5.24.1 /usr/lib/x86_64-linux-gnu/perl5/5.24 /usr/share/perl5 /usr/lib/x86_64-linux-gnu/perl/5.24 /usr/share/perl/5.24 /usr/local/lib/site_perl /usr/lib/x86_64-linux-gnu/perl-base .) at /usr/share/perl5/Debconf/FrontEnd/Readline.pm line 7.)
debconf: falling back to frontend: Teletype
Updating certificates in /etc/ssl/certs...
151 added, 0 removed; done.
Setting up libidn2-0:amd64 (0.16-1+deb9u1) ...
Setting up libpsl5:amd64 (0.17.0-3) ...
Setting up libkrb5support0:amd64 (1.15-1+deb9u1) ...
Setting up libhogweed4:amd64 (3.3-1+b2) ...
Setting up libp11-kit0:amd64 (0.23.3-2) ...
Setting up libk5crypto3:amd64 (1.15-1+deb9u1) ...
Setting up libgnutls30:amd64 (3.5.8-5+deb9u4) ...
Setting up librtmp1:amd64 (2.4+20151223.gitfa8646d.1-1+b1) ...
Setting up libldap-2.4-2:amd64 (2.4.44+dfsg-5+deb9u2) ...
Setting up libkrb5-3:amd64 (1.15-1+deb9u1) ...
Setting up libgssapi-krb5-2:amd64 (1.15-1+deb9u1) ...
Setting up libcurl3:amd64 (7.52.1-5+deb9u8) ...
Setting up curl (7.52.1-5+deb9u8) ...
Processing triggers for libc-bin (2.24-11+deb9u3) ...
Processing triggers for ca-certificates (20161130+nmu1+deb9u1) ...
Updating certificates in /etc/ssl/certs...
0 added, 0 removed; done.
Running hooks in /etc/ca-certificates/update.d...
done.
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 18.2M  100 18.2M    0     0  4671k      0  0:00:04  0:00:04 --:--:-- 4672k
 ---> c288ac0fa172
Step 8/8 : RUN helm init --client-only
 ---> Running in ccac78c2be6a
Creating /root/.helm
Creating /root/.helm/repository
Creating /root/.helm/repository/cache
Creating /root/.helm/repository/local
Creating /root/.helm/plugins
Creating /root/.helm/starters
Creating /root/.helm/cache/archive
Creating /root/.helm/repository/repositories.yaml
Adding stable repo with URL: https://kubernetes-charts.storage.googleapis.com
Adding local repo with URL: http://127.0.0.1:8879/charts
$HELM_HOME has been configured at /root/.helm.
Not installing Tiller due to 'client-only' flag having been set
Happy Helming!
 ---> c3faed85e62e
Successfully built c3faed85e62e
Successfully tagged jeremyrickard/helm-draft-boot-mysql:latest
The push refers to repository [docker.io/jeremyrickard/helm-draft-boot-mysql]
162c77b6e48b: Preparing
00dadeedba5d: Preparing
dbb88893ca88: Preparing
572ce892f590: Preparing
9a515a0037ef: Preparing
90d1009ce6fe: Preparing
90d1009ce6fe: Waiting
9a515a0037ef: Mounted from jeremyrickard/simple-draft-boot-mysql
90d1009ce6fe: Mounted from jeremyrickard/simple-draft-boot-mysql
dbb88893ca88: Pushed
572ce892f590: Pushed
162c77b6e48b: Pushed
00dadeedba5d: Pushed
latest: digest: sha256:06941fe1fba5dd4c045dc8f8599354e2e7ba433624d89962c05142f077f1c06f size: 1580

Generating Bundle File with Invocation Image jeremyrickard/helm-draft-boot-mysql@sha256:06941fe1fba5dd4c045dc8f8599354e2e7ba433624d89962c05142f077f1c06f =======>
Generating parameter definition mysql-user ====>
Generating parameter definition database-name ====>
Generating credential kubeconfig ====>
```

This can be installed with the same credential since it only needs the Kubernetes config. When we examine the output, we see two `Helm` installs and the parameters and output are passed between steps:

```console
duffle install -c kube-cred helm-draft-boot -f bundle.json --insecure
Executing install action...
executing porter install configuration from /cnab/app/porter.yaml
Install MySQL
/usr/local/bin/helm helm install --name helm-draft-boot-mysql stable/mysql --version 0.10.2 --replace --set mysqlDatabase=mydb --set mysqlUser=bizops-user --set persistence.enabled=false
NAME:   helm-draft-boot-mysql
LAST DEPLOYED: Sun Feb  3 01:41:04 2019
NAMESPACE: default
STATUS: DEPLOYED

RESOURCES:
==> v1/Pod(related)
NAME                                    READY  STATUS    RESTARTS  AGE
helm-draft-boot-mysql-7d45b6dbc7-zrmr2  0/1    Init:0/1  0         1s

==> v1/Secret
NAME                   TYPE    DATA  AGE
helm-draft-boot-mysql  Opaque  2     1s

==> v1/ConfigMap
NAME                        DATA  AGE
helm-draft-boot-mysql-test  1     1s

==> v1/Service
NAME                   TYPE       CLUSTER-IP   EXTERNAL-IP  PORT(S)   AGE
helm-draft-boot-mysql  ClusterIP  10.0.116.37  <none>       3306/TCP  1s

==> v1beta1/Deployment
NAME                   DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
helm-draft-boot-mysql  1        1        1           0          1s


NOTES:
MySQL can be accessed via port 3306 on the following DNS name from within your cluster:
helm-draft-boot-mysql.default.svc.cluster.local

To get your root password run:

    MYSQL_ROOT_PASSWORD=$(kubectl get secret --namespace default helm-draft-boot-mysql -o jsonpath="{.data.mysql-root-password}" | base64 --decode; echo)

To connect to your database:

1. Run an Ubuntu pod that you can use as a client:

    kubectl run -i --tty ubuntu --image=ubuntu:16.04 --restart=Never -- bash -il

2. Install the mysql client:

    $ apt-get update && apt-get install mysql-client -y

3. Connect using the mysql cli, then provide your password:
    $ mysql -h helm-draft-boot-mysql -p

To connect to your database directly from outside the K8s cluster:
    MYSQL_HOST=127.0.0.1
    MYSQL_PORT=3306

    # Execute the following command to route the connection:
    kubectl port-forward svc/helm-draft-boot-mysql 3306

    mysql -h ${MYSQL_HOST} -P${MYSQL_PORT} -u root -p${MYSQL_ROOT_PASSWORD}


Install Hello World
/usr/local/bin/helm helm install --name helm-draft-boot /cnab/app/chart/draft-boot --set database_host=helm-draft-boot-mysql.default.svc.cluster.local --set database_name=mydb --set database_password=QqcJb8q3lg --set database_port=3306 --set database_user=bizops-user --set image.repository=jeremyrickard/draft-boot --set image.tag=latest
NAME:   helm-draft-boot
LAST DEPLOYED: Sun Feb  3 01:41:09 2019
NAMESPACE: default
STATUS: DEPLOYED

RESOURCES:
==> v1beta1/Deployment
NAME                        DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
helm-draft-boot-draft-boot  1        1        1           0          1s

==> v1/Pod(related)
NAME                                         READY  STATUS             RESTARTS  AGE
helm-draft-boot-draft-boot-765586c44b-cff2q  0/1    ContainerCreating  0         1s

==> v1/Service
NAME                        TYPE       CLUSTER-IP   EXTERNAL-IP  PORT(S)  AGE
helm-draft-boot-draft-boot  ClusterIP  10.0.64.181  <none>       80/TCP   1s


NOTES:

  http://helm-draft-boot. to access your application

execution completed successfully!
```

### Draft Boot with Azure MySQL

For the next bundle, we'll also be creating an Azure MySQL instance. To do this, we'll need a service principal. If you need to do that, you can run the following command:

```console
az ad sp create-for-rbac --name osba-quickstart -o table
```

That will output a few fields, set those to the following environment variables:

```console
export AZURE_TENANT_ID=<Tenant>
export AZURE_CLIENT_ID=<AppId>
export AZURE_CLIENT_SECRET=<Password>
export AZURE_SUBSCRIPTION_ID=<Subscription Id>
```

This bundle is similar to the previous bundles, in that it also uses the `helm` mixin, but an important difference here is we will also use the `azure` mixin. This will use ARM to create a database for us. We'll also pipe the output from that mixin to the helm install. The `porter.yaml` looks like this:

```yaml
# This is the configuration for Porter
# You must define steps for each action, but the rest is optional
# Uncomment out the sections below to take full advantage of what Porter can do!

mixins:
  - azure
  - exec
  - helm 

credentials:
- name: SUBSCRIPTION_ID
  env: AZURE_SUBSCRIPTION_ID
- name: CLIENT_ID
  env: AZURE_CLIENT_ID
- name: TENANT_ID
  env: AZURE_TENANT_ID
- name: CLIENT_SECRET
  env: AZURE_CLIENT_SECRET
- name: kubeconfig
  path: /root/.kube/config

parameters:
- name: mysql_user
  type: string
  default: azureuser

- name: mysql_password
  type: string
  default: "!Th1s1s4p4ss!"

- name: database_name
  type: string
  default: "wordpress"

name: simple-draft-boot 
version: 0.1.0
description: "Install Draft Boot and MySQL on Azure"
invocationImage: jeremyrickard/simple-draft-boot-mysql:latest

install:

  - description: "Create Azure MySQL"
    azure:
      type: mysql
      name: draft-boot-mysql
      resourceGroup: "draft-boot-test"
      parameters:
        administratorLogin:
          source: bundle.parameters.mysql_user
        administratorLoginPassword:
          source: bundle.parameters.mysql_password
        location: "eastus"
        serverName: "draft-boot-mysql-feb-2018"
        version: "5.7"
        sslEnforcement: "Disabled"
        databaseName:
          source: bundle.parameters.database_name
    outputs:
      - name: "MYSQL_HOST"
        key: "MYSQL_HOST"

  - description: "Install Hello World"
    helm:
      name: draft-boot-mysql-azure
      chart: /cnab/app/chart/draft-boot
      set:
        image.repository: "jeremyrickard/draft-boot"
        image.tag: "latest"
        database_port: 3306
        database_host: 
          source: bundle.outputs.MYSQL_HOST
        database_name:
          source: bundle.parameters.database_name
        database.user:
          source: bundle.parameters.mysql_user
        database.password:
          source: bundle.parameters.mysql_password
uninstall:
  - description: ""
    exec:
      command: echo
      args: 
       - "-c"
       - "echo Goodbye World"
```

Next, we again build the bundle with `porter build`:

```console
$ cd azure-draft-boot-cnab/
$ porter build
porter build
Copying dependencies ===>
Copying mixins ===>
Copying mixin azure ===>
Copying mixin exec ===>
Copying mixin helm ===>
Copying mixin porter ===>

Generating Dockerfile =======>
[FROM quay.io/deis/lightweight-docker-go:v0.2.0 FROM debian:stretch COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt  # exec mixin has no buildtime dependencies  RUN apt-get update && \  apt-get install -y curl && \  curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \  tar -xzf helm.tgz && \  mv linux-amd64/helm /usr/local/bin && \  rm helm.tgz RUN helm init --client-only COPY cnab/ /cnab/ COPY porter.yaml /cnab/app/porter.yaml CMD ["/cnab/app/run"]]

Writing Dockerfile =======>
FROM quay.io/deis/lightweight-docker-go:v0.2.0
FROM debian:stretch
COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt

# exec mixin has no buildtime dependencies

RUN apt-get update && \
 apt-get install -y curl && \
 curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \
 tar -xzf helm.tgz && \
 mv linux-amd64/helm /usr/local/bin && \
 rm helm.tgz
RUN helm init --client-only
COPY cnab/ /cnab/
COPY porter.yaml /cnab/app/porter.yaml
CMD ["/cnab/app/run"]

Starting Invocation Image Build =======>
Step 1/8 : FROM quay.io/deis/lightweight-docker-go:v0.2.0
 ---> acf6712d2918
Step 2/8 : FROM debian:stretch
 ---> de8b49d4b0b3
Step 3/8 : COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
 ---> Using cache
 ---> 6bf0ab0d751e
Step 4/8 : RUN apt-get update &&  apt-get install -y curl &&  curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz &&  tar -xzf helm.tgz &&  mv linux-amd64/helm /usr/local/bin &&  rm helm.tgz
 ---> Using cache
 ---> 66fc7ec07da7
Step 5/8 : RUN helm init --client-only
 ---> Using cache
 ---> f5831b023f53
Step 6/8 : COPY cnab/ /cnab/
 ---> 55f430aafaa5
Step 7/8 : COPY porter.yaml /cnab/app/porter.yaml
 ---> 3a906364fa0c
Step 8/8 : CMD ["/cnab/app/run"]
 ---> Running in 9a9a9d21cb2f
 ---> 0cd592a6f22e
Successfully built 0cd592a6f22e
Successfully tagged jeremyrickard/simple-draft-boot-mysql:latest
The push refers to repository [docker.io/jeremyrickard/simple-draft-boot-mysql]
5ec60a513bda: Preparing
417e3872d281: Preparing
3609f9b8168b: Preparing
3aeb7c6a347e: Preparing
c7956a703d1e: Preparing
c581f4ede92d: Preparing
c581f4ede92d: Waiting
3609f9b8168b: Mounted from jeremyrickard/draft-boot-mysql
c7956a703d1e: Mounted from jeremyrickard/draft-boot-mysql
3aeb7c6a347e: Mounted from jeremyrickard/draft-boot-mysql
5ec60a513bda: Pushed
c581f4ede92d: Mounted from jeremyrickard/draft-boot-mysql
417e3872d281: Pushed
latest: digest: sha256:1c5154f6a71db55786b4f02d97cf68d75abbf84db65387169342e305badd66c9 size: 1580

Generating Bundle File with Invocation Image jeremyrickard/simple-draft-boot-mysql@sha256:1c5154f6a71db55786b4f02d97cf68d75abbf84db65387169342e305badd66c9 =======>
Generating parameter definition mysql_user ====>
Generating parameter definition mysql_password ====>
Generating parameter definition database_name ====>
Generating credential SUBSCRIPTION_ID ====>
Generating credential CLIENT_ID ====>
Generating credential TENANT_ID ====>
Generating credential CLIENT_SECRET ====>
Generating credential kubeconfig ====>
```

This output shows that there are a few different parameters and more credentials required. So let's build a new credential:

```console
$ duffle credential generate azure-kube-cred -f bundle.json --insecure
? Choose a source for "CLIENT_SECRET"  [Use arrows to move, type to filter]
  specific value
> environment variable
  file path
  shell command
? Choose a source for "CLIENT_SECRET" environment variable
? Enter a value for "CLIENT_SECRET" AZURE_CLIENT_SECRET
? Choose a source for "SUBSCRIPTION_ID"  [Use arrows to move, type to filter]
  specific value
> environment variable
  file path
  shell command
? Choose a source for "SUBSCRIPTION_ID" environment variable
? Enter a value for "SUBSCRIPTION_ID" AZURE_SUBSCRIPTION_ID
? Choose a source for "TENANT_ID"  [Use arrows to move, type to filter]
  specific value
> environment variable
  file path
  shell command
? Choose a source for "TENANT_ID" environment variable
? Enter a value for "TENANT_ID" AZURE_TENANT_ID
? Choose a source for "kubeconfig"  [Use arrows to move, type to filter]
  specific value
  environment variable
> file path
  shell command
? Choose a source for "kubeconfig" file path
? Enter a value for "kubeconfig" $HOME/.kube/config
? Choose a source for "CLIENT_ID"  [Use arrows to move, type to filter]
  specific value
> environment variable
  file path
  shell command
? Choose a source for "CLIENT_ID" environment variable
? Enter a value for "CLIENT_ID" AZURE_CLIENT_ID
```

The credential that is generated will now have both the kubernetes config and the Azure config. Now we can run the bundle with duffle. Note, because this is using Azure to create the MySQL, it will take some time to finish:

```console
$ duffle install -c azure-kube-cred azure-mysql-draft-boot -f bundle.json --insecure
Executing install action...
executing porter install configuration from /cnab/app/porter.yaml
Create Azure MySQL
Starting deployment operations...
Finished deployment operations...
Install Hello World
/usr/local/bin/helm helm install --name draft-boot-mysql-azure /cnab/app/chart/draft-boot --set database.password=!Th1s1s4p4ss! --set database.user=azureuser --set database_host=draft-boot-mysql-feb-2018.mysql.database.azure.com --set database_name=wordpress --set database_port=3306 --set image.repository=jeremyrickard/draft-boot --set image.tag=latest
NAME:   draft-boot-mysql-azure
LAST DEPLOYED: Sun Feb  3 03:53:56 2019
NAMESPACE: default
STATUS: DEPLOYED

RESOURCES:
==> v1/Service
NAME                               TYPE       CLUSTER-IP   EXTERNAL-IP  PORT(S)  AGE
draft-boot-mysql-azure-draft-boot  ClusterIP  10.0.240.28  <none>       80/TCP   0s

==> v1beta1/Deployment
NAME                               DESIRED  CURRENT  UP-TO-DATE  AVAILABLE  AGE
draft-boot-mysql-azure-draft-boot  1        1        1           0          0s

==> v1/Pod(related)
NAME                                                READY  STATUS             RESTARTS  AGE
draft-boot-mysql-azure-draft-boot-5d595d6674-chfvd  0/1    ContainerCreating  0         0s


NOTES:

  http://draft-boot-mysql-azure. to access your application

execution completed successfully!
```

After the Azure MySQL instance was created, Helm was used to install the sample app, just like in the previous examples. This time, however, the database_host was the value that was generated by the `azure` mixin.

## Cleanup

Porter hasn't currently implemented the uninstall actions, so you'll need to do this manually. You can remove the Helm components by doing a `helm list` and then running `helm delete --purge <releasename>`.

The last bundle creates some Azure resources. To clean that up, you'll want to do `az group delete draft-boot-test`.