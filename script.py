import requests
import json

BASE_URL = "http://localhost:8080/api/thin"

def get_process_tree():
    url = f"{BASE_URL}/processTree"
    response = requests.get(url)
    if response.status_code == 200:
        with open("process_tree_response.txt", "w") as file:
            file.write(response.text)
        print("Process Tree response saved to process_tree_response.txt")
    else:
        print(f"Failed to get process tree. Status code: {response.status_code}")

def get_zip_proc():
    url = f"{BASE_URL}/zipProc"
    response = requests.get(url)
    if response.status_code == 200:
        with open("proc_zip_response.zip", "wb") as file:
            file.write(response.content)
        print("Zip of /proc folder saved to proc_zip_response.zip")
    else:
        print(f"Failed to get zip of /proc folder. Status code: {response.status_code}")

def get_message():
    url = f"{BASE_URL}/message"
    response = requests.get(url)
    if response.status_code == 200:
        with open("message_response.txt", "w") as file:
            file.write(json.dumps(response.json()))
        print("Message response saved to message_response.txt")
    else:
        print(f"Failed to get Message. Status code: {response.status_code}")

def get_ciphers():
    url = f"{BASE_URL}/ciphers"
    response = requests.get(url)
    if response.status_code == 200:
        with open("ciphers_response.txt", "w") as file:
            file.write(response.text)
        print("Ciphers response saved to ciphers_response.txt")
    else:
        print(f"Failed to get Ciphers. Status code: {response.status_code}")

def read_file(file_path):
    url = f"{BASE_URL}/readfile"
    file_request = {"path": file_path}
    response = requests.post(url, json=file_request)
    if response.status_code == 200:
        with open("read_file_response.txt", "wb") as file:
            file.write(response.content)
        print("File response saved to read_file_response.txt")
    else:
        print(f"Failed to read file. Status code: {response.status_code}")

def write_file(file_path, content):
    url = f"{BASE_URL}/writefile"
    file_request = {"path": file_path, "content": content}
    response = requests.post(url, json=file_request)
    if response.status_code == 200:
        print(f"Successfully wrote to the file at {file_path}")
    else:
        print(f"Failed to write file. Status code: {response.status_code}")

if __name__ == "__main__":
    # Call the create-podman-checkpoint endpoint
    create_checkpoint_url = "http://localhost:8080/api/checkpoint/create-podman-checkpoint"
    checkpoint_body = {
        "url": "localhost",
        "port": "10250",
        "nameSpace": "default",
        "podName": "javathin-deployment-545866d77d-dpsth",
        "containerName": "javathin",
        "checkpointPath": "/mnt/hgfs/cpz/vmcpz/k8s/",
        "keyPath": "/etc/kubernetes/pki/apiserver-kubelet-client.key",
        "certPath":  "/etc/kubernetes/pki/apiserver-kubelet-client.crt",
        "caPath": "/etc/kubernetes/pki/ca.crt"
    }
    checkpoint_response = requests.post(create_checkpoint_url, json=checkpoint_body)

    if checkpoint_response.status_code == 200:
        print("Checkpoint created successfully.")
    else:
        print(f"Failed to create checkpoint. Status code: {checkpoint_response.status_code}")

    get_process_tree()
    get_zip_proc()
    get_message()
    get_ciphers()
    read_file("/path/to/read")
    write_file("/path/to/write", "This is the content to write.")
