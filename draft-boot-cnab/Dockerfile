FROM quay.io/deis/lightweight-docker-go:v0.2.0
FROM debian:stretch
COPY --from=0 /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/ca-certificates.crt
RUN apt-get update && \
 apt-get install -y curl && \
 curl -o helm.tgz https://storage.googleapis.com/kubernetes-helm/helm-v2.11.0-linux-amd64.tar.gz && \
 tar -xzf helm.tgz && \
 mv linux-amd64/helm /usr/local/bin && \
 rm helm.tgz
RUN helm init --client-only
# exec mixin has no buildtime dependencies

COPY cnab/ /cnab/
COPY porter.yaml /cnab/app/porter.yaml
CMD ["/cnab/app/run"]