    package admission

    import data.k8s.matches
    
    ###############################################################################
    #
    # Policy : Container tag should not be latest. 
    #
    ###############################################################################
    deny[{
        "id": "container-image-whitelist",          # identifies type of violation
        "resource": {
            "kind": "pods",                 # identifies kind of resource
            "namespace": namespace,         # identifies namespace of resource
            "name": name                    # identifies name of resource
        },
        "resolution": {"message": msg},     # provides human-readable message to display
    }] {
        matches[["pods", namespace, name, matched_pod]]
        container = matched_pod.spec.containers[_]
        re_match("^.+:latest$", container.image)
        msg := sprintf("invalid container registry image %q", [container.image])
    }
