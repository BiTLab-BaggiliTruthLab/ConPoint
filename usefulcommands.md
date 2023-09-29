## Useful Commands
This is a collection of useful commands that I have found useful in my development and testing.
## Kubernetes
### Checkpointing
To checkpoint a pod in kubernetes, you will need to know the pod name and the namespace. You can find this by running the following command:

```kubectl get pods```


Currently, Only using the kubectl service endpoint, that's why, we need to serve the proxy.
Enable Kubectl proxy by:
```
kubectl proxy
```
Then via this endpoint template, we can checkpoint the pod:
```
sudo curl -sk -X POST  "https://localhost:10250/checkpoint/NAMESPACE/PODNAME/CONTAINERNAME"   --key /etc/kubernetes/pki/apiserver-kubelet-client.key   --cacert /etc/kubernetes/pki/ca.crt   --cert /etc/kubernetes/pki/apiserver-kubelet-client.crt
```
for example, to checkpoint the webserver pod in default namespace, we can run:
```
sudo curl -sk -X POST  "https://localhost:10250/checkpoint/default/webserver/webserver"   --key /etc/kubernetes/pki/apiserver-kubelet-client.key   --cacert /etc/kubernetes/pki/ca.crt   --cert /etc/kubernetes/pki/apiserver-kubelet-client.crt
```

## **Podman**
Initiate the podman service

```sudo podman system service --time=0 unix:///tmp/podman.sock```

Instruct podman to checkpoing a container

```sudo curl -XPOST --unix-socket /tmp/podman.sock -H "content-type:application/json" "http://d/v4.0.0/libpod/containers/{CONTAINER_ID}/checkpoint?export=true&keep=true&leaveRunning=true&printStats=true" --output ./PATH/checkpoint.tar.gz```

## Docker
To do container checkpoint. You will need to enable experimental features in docker. This can be done by editing the /etc/docker/daemon.json file and adding the following:

```
{
  "experimental": true
}
```

Then restart the docker service

```sudo systemctl restart docker```

To checkpoint a container, you will need to know the container ID. This can be found by running the following command:

```sudo docker ps```

Then you can run the following command to checkpoint the container:

```sudo docker checkpoint create <container ID> <checkpoint name> ---leave-running --checkpoint-dir=/tmp/checkpoints```

Could have multiple terminals. (ALWAYS USE NON priviledged user for the terminal, BUT use sudo for the commands, the listed below as is)
- npm start
- java -jar CCF-0.0.1-SNAPSHOT.jar
- sudo podman system service --time=0 unix:///tmp/podman.sock
- kubectl proxy
- ls /tmp/check/

In case of error in mounting: 
- sudo vmhgfs-fuse -o allow_other -o auto_unmount .host:/ /mnt/hgfs
- sudo vmhgfs-fuse -o allow_other -o auto_unmount -o nonempty .host:/ /mnt/hgfs 
- vmhgfs-fuse -o allow_other -o auto_unmount .host:/ /mnt/hgfs
- sudo vmhgfs-fuse -o allow_other -o auto_unmount -o nonempty .host:/ /mnt/hgfs

K8s command:
kubectl get pod POD_NAME -n default -o json | jq -r '.metadata.namespace, .metadata.name, .spec.containers[].name'
